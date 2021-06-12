/*
 * MIT License
 *
 * Copyright (c) 2021 OroArmor (Eli Orona)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.oroarmor.discordbot.versions;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.oroarmor.discordbot.util.MessageEmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;

public class VersionHandler {
    private final List<VersionChecker> checkers;
    private final TextChannel channel;

    public VersionHandler(TextChannel channel) {
        this.channel = channel;
        checkers = new ArrayList<>();
    }

    public void addChecker(VersionChecker checker) {
        checkers.add(checker);
    }

    public void update() {
        checkers.stream().map(VersionChecker::update).filter(versions -> !versions.isEmpty()).map(versions -> String.join("\n", versions)).forEach(update -> {
            MessageEmbed embed = new MessageEmbedBuilder().setTitle("New updates").setDescription(update).setColor(0xFF0000).build();
            channel.sendMessage(embed).queue();
        });
    }

    public static abstract class VersionChecker {
        protected final Set<String> versions;
        protected final URL checkURL;

        public VersionChecker(URL checkURL) {
            this.versions = new HashSet<>();
            this.checkURL = checkURL;
        }

        public VersionChecker(String checkURL) {
            this(((Supplier<URL>) () -> {
                try {
                    return new URL(checkURL);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                return null;
            }).get());
        }

        public Set<String> update() {
            try {
                InputStream stream = checkURL.openStream();
                String str = new String(stream.readAllBytes());
                ObjectMapper mapper = new ObjectMapper();
                JsonNode element = mapper.readTree(str);

                Set<String> newVersions = this.getVersions(element);
                newVersions.removeAll(versions);

                if (!newVersions.isEmpty() && !versions.isEmpty()) {
                    versions.addAll(newVersions);
                    return decorate(newVersions);
                }
                versions.addAll(newVersions);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return Set.of();
        }

        protected abstract Set<String> decorate(Set<String> versions);

        protected abstract Set<String> getVersions(JsonNode element);
    }

    public static class GithubReleasesChecker extends VersionChecker {
        private final String repository;

        public GithubReleasesChecker(String repository) {
            super("https://api.github.com/repos/" + repository + "/releases");
            this.repository = repository;
        }

        @Override
        protected Set<String> decorate(Set<String> versions) {
            return versions.stream().map(version -> "New " + repository + " version: " + version).collect(Collectors.toSet());
        }

        @Override
        protected Set<String> getVersions(JsonNode node) {
            ArrayNode versions = (ArrayNode) node;
            return StreamSupport.stream(Spliterators.spliteratorUnknownSize(versions.elements(), Spliterator.IMMUTABLE), true).map(_node -> _node.get("name").asText().isEmpty() ? _node.get("tag_name").asText() : _node.get("name").asText()).collect(Collectors.toSet());
        }
    }

    public static class MavenMetadataChecker extends VersionChecker {
        private final String name;

        public MavenMetadataChecker(String checkURL, String name) {
            super(checkURL);
            this.name = name;
        }

        @Override
        public Set<String> update() {
            try {
                InputStream stream = checkURL.openStream();
                String str = new String(stream.readAllBytes());
                ObjectMapper mapper = new XmlMapper();
                JsonNode element = mapper.readTree(str);

                Set<String> newVersions = this.getVersions(element);
                newVersions.removeAll(versions);

                if (!newVersions.isEmpty() && !versions.isEmpty()) {
                    versions.addAll(newVersions);
                    return decorate(newVersions);
                }
                versions.addAll(newVersions);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return Set.of();
        }

        @Override
        protected Set<String> decorate(Set<String> versions) {
            return versions.stream().map(version -> "New " + name + " version: " + version).collect(Collectors.toSet());
        }

        @Override
        protected Set<String> getVersions(JsonNode node) {
            ArrayNode versions = (ArrayNode) node.get("versioning").get("versions").get("version");
            return StreamSupport.stream(Spliterators.spliteratorUnknownSize(versions.elements(), Spliterator.IMMUTABLE), true).map(JsonNode::asText).collect(Collectors.toSet());
        }
    }

    public static class FabricMetaChecker extends VersionChecker {
        private final String name;

        public FabricMetaChecker(String url, String name) {
            super(url);
            this.name = name;
        }

        @Override
        protected Set<String> decorate(Set<String> versions) {
            return versions.stream().map(version -> "New " + name + " version: " + version).collect(Collectors.toSet());
        }

        @Override
        protected Set<String> getVersions(JsonNode node) {
            ArrayNode versions = (ArrayNode) node;
            return StreamSupport.stream(Spliterators.spliteratorUnknownSize(versions.elements(), Spliterator.IMMUTABLE), true).map(_node -> _node.get("version").asText()).collect(Collectors.toSet());
        }
    }

    public static class QuiltMinecraftMetaChecker extends VersionChecker {
        public QuiltMinecraftMetaChecker() {
            super("https://meta.quiltmc.org/v3/versions");
        }

        @Override
        protected Set<String> decorate(Set<String> versions) {
            return versions.stream().map(version -> "New Minecraft version: " + version).collect(Collectors.toSet());
        }

        @Override
        protected Set<String> getVersions(JsonNode node) {
            ArrayNode versions = (ArrayNode) node.get("game");
            return StreamSupport.stream(Spliterators.spliteratorUnknownSize(versions.elements(), Spliterator.IMMUTABLE), true).map(_node -> _node.get("version").asText()).collect(Collectors.toSet());
        }
    }
}

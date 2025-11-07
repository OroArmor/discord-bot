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

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.oroarmor.discordbot.DiscordBot;
import com.oroarmor.discordbot.util.MessageEmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.middleman.GuildMessageChannel;
import org.jetbrains.annotations.NotNull;

public class VersionHandler {
    private final List<VersionChecker> checkers;
    private final List<GuildMessageChannel> channels;

    public VersionHandler(List<GuildMessageChannel> channels, List<VersionChecker> checkers) {
        this.channels = channels;
        this.checkers = checkers;
    }

    public void update() {
        checkers.stream()
                .map(VersionChecker::update)
                .filter(versions -> !versions.isEmpty())
                .map(TreeSet::new)
                .map(versions -> String.join("\n", versions))
                .forEach(update -> {
                    MessageEmbed embed = new MessageEmbedBuilder()
                            .setTitle("New updates")
                            .setDescription(update)
                            .setColor(0xFF0000)
                            .build();

                    channels.forEach(channel -> channel.sendMessageEmbeds(embed).queue());
                });
    }

    public static List<VersionChecker> loadFromJson(ArrayNode checkers) {
        List<VersionChecker> checkerList = new ArrayList<>();
        for (JsonNode checker : checkers) {
            String type = checker.get("type").asText();
            VersionChecker versionChecker = switch (type) {
                case "meta" -> new MetaChecker(
                        checker.get("name").asText(),
                        checker.get("id").asText(),
                        checker.get("url").asText()
                    );
                case "maven" -> new MavenMetadataChecker(
                        checker.get("name").asText(),
                        checker.get("id").asText(),
                        checker.get("url").asText()
                    );
                case "github" -> new GithubReleasesChecker(
                        checker.get("repository").asText()
                    );
                default -> throw new IllegalArgumentException("Unknown checker type: " + type);
            };
            checkerList.add(versionChecker);
        }

        System.out.println("Versions to check: " + checkerList.stream().map(VersionChecker::getName).collect(Collectors.joining(", ")));

        return checkerList;
    }

    public static abstract class VersionChecker {
        protected final Set<String> versions;

        protected final URL checkURL;
        private final String name;
        private final String id;

        public VersionChecker(String name, String id, URL checkURL) {
            this.name = name;
            this.id = id;
            this.versions = loadVersions();
            this.checkURL = checkURL;
        }

        private Set<String> loadVersions() {
            Path cache = DiscordBot.CACHE_DIRECTORY.resolve(this.id + ".json");
            try {
                ArrayNode node = ((ArrayNode) new JsonMapper().readTree(Files.newBufferedReader(cache)));

                Set<String> versions = new HashSet<>();

                for (JsonNode version : node) {
                    versions.add(version.asText());
                }

                return versions;
            } catch (NoSuchFileException e) {
                return new HashSet<>();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private void saveVersions(Set<String> versions) {
            Path cache = DiscordBot.CACHE_DIRECTORY.resolve(this.id + ".json");
            try {
                Files.createDirectories(cache.getParent());
                if (!Files.exists(cache)) {
                    Files.createFile(cache);
                }

                JsonGenerator generator = new JsonMapper().createGenerator(Files.newBufferedWriter(cache));
                generator.writeArray(
                        versions.toArray(String[]::new),
                        0,
                        versions.size()
                );
                generator.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public VersionChecker(String name, String id, String checkURL) {
            this(name, id, ((Supplier<URL>) () -> {
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
                ObjectMapper mapper = getMapper();
                JsonNode element = mapper.readTree(str);

                Set<String> newVersions = this.getVersions(element);
                newVersions.removeAll(versions);

                if (!newVersions.isEmpty()) {
                    System.out.println("Updates for " + getName() + " " + newVersions);
                }

                if (!newVersions.isEmpty() && !versions.isEmpty()) {
                    versions.addAll(newVersions);
                    return decorate(newVersions);
                }
                versions.addAll(newVersions);
                saveVersions(versions);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return Set.of();
        }

        @NotNull
        protected ObjectMapper getMapper() {
            return new ObjectMapper();
        }

        protected String getName() {
            return this.name;
        }

        public String getId() {
            return id;
        }

        protected Set<String> decorate(Set<String> versions) {
            return versions.stream().map(version -> "New " + getName() + " version: " + version).collect(Collectors.toSet());
        }

        protected abstract Set<String> getVersions(JsonNode element);
    }

    public static class GithubReleasesChecker extends VersionChecker {
        public GithubReleasesChecker(String repository) {
            super(repository, repository, "https://api.github.com/repos/" + repository + "/releases");
        }

        @Override
        protected Set<String> getVersions(JsonNode node) {
            ArrayNode versions = (ArrayNode) node;
            return StreamSupport.stream(Spliterators.spliteratorUnknownSize(versions.elements(), Spliterator.IMMUTABLE), true).map(_node -> _node.get("name").asText().isEmpty() ? _node.get("tag_name").asText() : _node.get("name").asText()).collect(Collectors.toSet());
        }
    }

    public static class MavenMetadataChecker extends VersionChecker {
        public MavenMetadataChecker(String name, String id, String url) {
            super(name, id, url);
        }

        @Override
        protected Set<String> getVersions(JsonNode node) {
            ArrayNode versions = (ArrayNode) node.get("versioning").get("versions").get("version");
            return StreamSupport.stream(Spliterators.spliteratorUnknownSize(versions.elements(), Spliterator.IMMUTABLE), true).map(JsonNode::asText).collect(Collectors.toSet());
        }

        @Override
        @NotNull
        protected ObjectMapper getMapper() {
            return new XmlMapper();
        }
    }

    public static class MetaChecker extends VersionChecker {

        public MetaChecker(String name, String id, String url) {
            super(name, id, url);
        }

        @Override
        protected Set<String> getVersions(JsonNode node) {
            ArrayNode versions = (ArrayNode) node;
            return StreamSupport.stream(Spliterators.spliteratorUnknownSize(versions.elements(), Spliterator.IMMUTABLE), true).map(_node -> _node.get("version").asText()).collect(Collectors.toSet());
        }
    }
}

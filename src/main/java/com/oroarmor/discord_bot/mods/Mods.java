package com.oroarmor.discord_bot.mods;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.oroarmor.discord_bot.util.MessageEmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class Mods {
    private static final List<Mod> MODS = new ArrayList<>();
    private static long lastPull = 0;

    private static void pullModData() {
        if (MODS.size() == 0 || System.currentTimeMillis() - lastPull > 86400000) {
            lastPull = System.currentTimeMillis();
            try {
                InputStream stream = new URL("https://oroarmor.com/api/mods.json").openStream();
                String str = new String(stream.readAllBytes());

                ObjectMapper mapper = new ObjectMapper();


                JsonNode element = mapper.readTree(str);
                ArrayNode modArray = (ArrayNode) element.findValue("mods");

                modArray.forEach(jsonMod -> {
                    Mod.Builder builder = new Mod.Builder().setName(jsonMod.get("name").asText())
                            .setId(jsonMod.get("id").asText())
                            .setDescription(jsonMod.get("description").asText())
                            .setExtendedDescription(jsonMod.get("extendedDescription").asText());

                    String id = jsonMod.get("id").asText();
                    String[] idTokens = id.contains("_") ? id.split("_") : id.split("-");
                    StringBuilder alias = new StringBuilder();
                    for (String token : idTokens) {
                        alias.append(token, 0, 1);
                    }
                    builder.setAlias(alias.toString());

                    ArrayNode jsonLinks = (ArrayNode) jsonMod.get("links");
                    Map<String, String> links = new HashMap<>();
                    jsonLinks.forEach(linkJson -> links.put(linkJson.get("name").asText().toLowerCase(), linkJson.get("link").asText()));
                    builder.setLinks(links);

                    MODS.add(builder.build());
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getIds() {
        pullModData();
        return MODS.stream().map(mod -> mod.getId() + ": " + mod.getAlias()).collect(Collectors.joining("\n"));
    }

    public static Mod getMod(String s) {
        pullModData();
        return MODS.stream().filter(mod -> mod.getAlias().equals(s) || mod.getId().equals(s)).findFirst().orElse(null);
    }

    public static List<Mod> getMods() {
        pullModData();
        return MODS;
    }

    public static MessageEmbed getModEmbed(Mod mod) {
        return new MessageEmbedBuilder().setTitle(mod.getName()).setDescription(mod.getExtendedDescription() + "\n\n" + mod.getLinks().entrySet().stream().map(entry -> "Find on " + entry.getKey() + ": " + entry.getValue()).collect(Collectors.joining("\n"))).build();
    }
}

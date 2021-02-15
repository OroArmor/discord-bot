package com.oroarmor.discord_bot.mods;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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

                JsonElement element = JsonParser.parseString(str);
                JsonArray modArray = element.getAsJsonObject().get("mods").getAsJsonArray();

                modArray.forEach(jsonMod -> {
                    JsonObject modObject = jsonMod.getAsJsonObject();
                    Mod.Builder builder = new Mod.Builder().setName(modObject.get("name").getAsString())
                            .setId(modObject.get("id").getAsString())
                            .setDescription(modObject.get("description").getAsString())
                            .setExtendedDescription(modObject.get("extendedDescription").getAsString());

                    String id = modObject.get("id").getAsString();
                    String[] idTokens = id.contains("_") ? id.split("_") : id.split("-");
                    StringBuilder alias = new StringBuilder();
                    for (String token : idTokens) {
                        alias.append(token, 0, 1);
                    }
                    builder.setAlias(alias.toString());

                    JsonArray jsonLinks = modObject.get("links").getAsJsonArray();
                    Map<String, String> links = new HashMap<>();
                    jsonLinks.forEach(linkJson -> {
                        JsonObject linkObject = linkJson.getAsJsonObject();
                        links.put(linkObject.get("name").getAsString().toLowerCase(), linkObject.get("link").getAsString());
                    });
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

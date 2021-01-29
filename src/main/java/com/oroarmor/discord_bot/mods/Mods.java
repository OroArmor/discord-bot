package com.oroarmor.discord_bot.mods;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.oroarmor.discord_bot.util.MessageEmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class Mods {
    private static final List<Mod> MODS = new ArrayList<>();

    private static void pullModData() {
        if (MODS.size() == 0) {
            MODS.add(new Mod.Builder().setName("Netherite Plus")
                    .setId("netherite_plus")
                    .setAlias("np")
                    .setDescription("Adds more netherite features to Minecraft.")
                    .setLinks(Map.of("github", "https://github.com/OroArmor/Netherite-Plus-Mod",
                            "modrinth", "https://modrinth.com/mod/netherite-plus-mod",
                            "curseforge", "https://www.curseforge.com/minecraft/mc-mods/netherite-plus-mod"))
                    .setExtendedDescription("Netherite is an amazing feature, but I felt that it was lacking some depth in Vanilla Minecraft, so I created this mod to add netherite features to all current tools. As of now, there are netherite equivalents for the elytra, bow, crossbow, anvil, fishing rod, trident, shulker boxes, horse armor, and shields. Some added features are fake netherite blocks for building, lava fishing with the netherite fishing rod, and xp reduction in the netherite anvil, which never breaks. All of these features are configurable and can be turned on and off.")
                    .build());
            MODS.add(new Mod.Builder().setName("Slime Block in Redstone Tab")
                    .setId("slime_block")
                    .setAlias("sb")
                    .setDescription("Reorginizes the creative inventory with configurable groups and items.")
                    .setLinks(Map.of("github", "https://github.com/OroArmor/SlimeBlockInRedstoneTab",
                            "curseforge", "https://www.curseforge.com/minecraft/mc-mods/slime-block-in-the-redstone-tab"))
                    .setExtendedDescription("The creative inventory is extremely cluttered and needs updating. This client-side mod allows you to move any item or tag to any tab, and creating new tabs. There are two config files that can be dynamically changed and updated with the /reload command in Minecraft. Check Github for a more in depth explaination of the config files.")
                    .build());
            MODS.add(new Mod.Builder().setName("Oro Config")
                    .setId("oro_config")
                    .setAlias("oc")
                    .setDescription("Simple JSON based config library with command and mod menu support")
                    .setLinks(Map.of("github", "https://github.com/OroArmor/Oro-Config"))
                    .setExtendedDescription("Simple JSON based config library with command and mod menu support")
                    .build());
            MODS.add(new Mod.Builder().setName("Multi Item Lib")
                    .setId("multi_item_lib")
                    .setAlias("mil")
                    .setDescription("Lightweight mod that expands hardcoded Minecraft items")
                    .setLinks(Map.of("github", "https://github.com/OroArmor/Multi-Item-Lib"))
                    .setExtendedDescription("A small library that easily allows for multiple types of items for Minecraft's more hardcoded items like elytra, fishing rods, bows, crossbows, and tridents.\nThis library will be depricated once Fabric API includes better versions of this library. This library does not encompass all uses cases for item, but it does with most of them to make the items work with the players.")
                    .build());
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
        return new MessageEmbedBuilder().setTitle(mod.getName()).setDescription(mod.getExtendedDescription() + "\n\n" + mod.getLinks().entrySet().stream().map(entry -> "Find on " + entry.getKey() + " " + entry.getValue()).collect(Collectors.joining("\n"))).build();
    }
}

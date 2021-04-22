package com.oroarmor.discordbot.commands;

import java.util.List;

import com.oroarmor.discordbot.mods.Mod;
import com.oroarmor.discordbot.mods.Mods;
import com.oroarmor.discordbot.util.MessageEmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;

public class IssueCommand extends Command {
    public IssueCommand() {
        super("issue", "How to create an issue.", Permissions.ANY);
    }

    @Override
    public void run(Member member, MessageChannel channel, List<String> tokens) {
        if (tokens.size() == 1) {
            channel.sendMessage(new MessageEmbedBuilder().setTitle("Create an Issue").setColor(0xff5500).setDescription("I won't support issues in here, so go to github, https://github.com/OroArmor/Netherite-Plus-Mod/issues, and place an issue. As well, if you dont provide a log or screenshots, I will not help you.").build()).queue();
        } else {
            Mod mod = Mods.getMod(tokens.get(1));
            if (mod == null) {
                channel.sendMessage("Mod: " + tokens.get(1) + ", is not a valid mod.").queue();
            } else {
                channel.sendMessage(new MessageEmbedBuilder().setTitle("Create an Issue").setColor(0xff5500).setDescription("I won't support issues in here, so go to github, " + mod.getLinks().get("github") + "/issues, and place an issue. As well, if you dont provide a log or screenshots, I will not help you.").build()).queue();
            }
        }
    }
}

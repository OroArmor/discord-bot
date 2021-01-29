package com.oroarmor.discord_bot.commands;

import java.util.List;
import java.util.stream.Collectors;

import com.oroarmor.discord_bot.mods.Mod;
import com.oroarmor.discord_bot.mods.Mods;
import com.oroarmor.discord_bot.util.MessageEmbededBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;

public class ModsCommand extends Command {
    public ModsCommand() {
        super("mods", "Gets the info about all mods.", Permissions.ANY);
    }

    @Override
    public void run(Member member, MessageChannel channel, List<String> tokens) {
        if (tokens.size() == 1) {
            channel.sendMessage(new MessageEmbededBuilder().setTitle("My Mods").setDescription("My mods are: \n" + Mods.getMods().stream().map(mod -> "**" + mod.getName() + "(" + mod.getAlias() + ")**: " + mod.getDescription()).collect(Collectors.joining("\n"))).build()).queue();
        } else {
            Mod mod = Mods.getMod(tokens.get(1));
            if (mod == null) {
                channel.sendMessage("Mod: `" + tokens.get(1) + "`, is not a valid mod.").queue();
            } else {
                channel.sendMessage(Mods.getModEmbed(mod)).queue();
            }
        }
    }

}

package com.oroarmor.discordbot.commands;

import java.util.List;

import com.oroarmor.discordbot.mods.Mod;
import com.oroarmor.discordbot.mods.Mods;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;

import static com.oroarmor.discordbot.mods.Mods.getModEmbed;

public class ModCommand extends Command {
    public ModCommand() {
        super("mod", "Gets the info about a mod.", Permissions.ANY);
    }

    @Override
    public void run(Member member, MessageChannel channel, List<String> tokens) {
        if (tokens.size() == 1) {
            channel.sendMessage("No mod provided! Available mods are:```" + Mods.getIds() + "```").queue();
        } else {
            Mod mod = Mods.getMod(tokens.get(1));
            if (mod == null) {
                channel.sendMessage("Mod: `" + tokens.get(1) + "`, is not a valid mod.").queue();
            } else {
                channel.sendMessage(getModEmbed(mod)).queue();
            }
        }
    }
}

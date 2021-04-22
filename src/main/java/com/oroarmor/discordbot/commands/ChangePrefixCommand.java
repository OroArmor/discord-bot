package com.oroarmor.discordbot.commands;

import java.util.List;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;

public class ChangePrefixCommand extends Command {
    public ChangePrefixCommand() {
        super("change-prefix", "Changes the command prefix (Only for moderators).", Permissions.MODERATOR);
    }

    @Override
    public void run(Member member, MessageChannel channel, List<String> tokens) {
        CommandManager.PREFIX = tokens.get(1);
        channel.sendMessage("Changing command prefix to " + tokens.get(1)).queue();
    }
}

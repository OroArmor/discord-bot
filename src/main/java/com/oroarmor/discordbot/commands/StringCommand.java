package com.oroarmor.discordbot.commands;

import java.util.List;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;

public class StringCommand extends Command {
    protected final String message;

    public StringCommand(String name, String description, Permissions permissions, String message) {
        super(name, description, permissions);
        this.message = message;
    }

    @Override
    public void run(Member member, MessageChannel channel, List<String> tokens) {
        channel.sendMessage(message).queue();
    }
}

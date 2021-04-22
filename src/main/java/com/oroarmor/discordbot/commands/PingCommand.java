package com.oroarmor.discordbot.commands;

import java.util.List;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;

public class PingCommand extends Command {
    public PingCommand() {
        super("ping", "Pings the discord bot", Permissions.ANY);
    }

    @Override
    public void run(Member member, MessageChannel channel, List<String> tokens) {
        long time = System.currentTimeMillis();
        channel.sendMessage("Pong").queue(response -> response.editMessageFormat("Pong %d ms", System.currentTimeMillis() - time).queue());
    }
}

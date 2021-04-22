package com.oroarmor.discordbot.commands;

import java.util.List;
import java.util.stream.Collectors;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;

public class AliasesCommand extends Command {
    public AliasesCommand() {
        super("aliases", "Get a list of all aliases for commands.", Permissions.ANY);
    }

    @Override
    public void run(Member member, MessageChannel channel, List<String> tokens) {
        channel.sendMessage("Available aliases are: ```" + CommandManager.getAliases().entrySet().stream().map(e -> "!" + e.getKey() + ": " + e.getValue().getName() + " (" + e.getValue().getDescription() + ")").collect(Collectors.joining("\n")) + "```").queue();
    }
}

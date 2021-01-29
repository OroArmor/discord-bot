package com.oroarmor.discord_bot.commands;

import java.util.List;
import java.util.stream.Collectors;

import com.oroarmor.discord_bot.CommandManager;
import net.dv8tion.jda.api.entities.*;

public class CommandsCommand extends Command {
    public CommandsCommand() {
        super("commands", "Get a list of all commands runnable by the user.", Permissions.ANY);
    }

    @Override
    public void run(Member member, MessageChannel channel, List<String> tokens) {
        channel.sendMessage("Available commands are: ```" + CommandManager.getCommands().values().stream().filter(command -> command.getPermissions().validUser(member, channel)).map(command -> CommandManager.PREFIX + command.getName() + ": " + command.getDescription()).collect(Collectors.joining("\n")) + "```").queue();
    }
}
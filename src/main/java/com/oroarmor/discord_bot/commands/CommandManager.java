package com.oroarmor.discord_bot.commands;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

public class CommandManager {
    private static final Map<String, Command> COMMANDS = new TreeMap<>();
    private static final Map<String, Command> ALIASES = new TreeMap<>();
    public static String PREFIX = "!";

    public static void runCommand(Member member, MessageChannel channel, Message message) {
        if (message.getContentRaw().startsWith(PREFIX)) {
            List<String> tokens = Arrays.asList(message.getContentRaw().substring(1).split("\\s+"));
            String commandName = tokens.get(0);

            Command command = null;
            if (ALIASES.containsKey(commandName)) {
                command = ALIASES.get(commandName);
            }

            if (COMMANDS.containsKey(commandName)) {
                command = COMMANDS.get(commandName);
            }

            if (command == null) {
                return;
            }

            if (command.getPermissions().validUser(member, channel)) {
                command.run(member, channel, tokens);
            }
        }
    }

    public static void addCommand(Command command) {
        if (COMMANDS.containsKey(command.getName())) {
            throw new IllegalArgumentException("Cannot register two commands to the same name");
        }

        COMMANDS.put(command.getName(), command);
    }

    public static void addAlias(String alias, String command) {
        if (!COMMANDS.containsKey(command)) {
            throw new IllegalArgumentException("Command: " + command + " not found");
        }

        ALIASES.put(alias, COMMANDS.get(command));
    }

    public static Map<String, Command> getCommands() {
        return COMMANDS;
    }

    public static Map<String, Command> getAliases() {
        return ALIASES;
    }
}

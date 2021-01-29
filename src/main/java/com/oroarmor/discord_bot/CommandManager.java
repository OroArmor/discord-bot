package com.oroarmor.discord_bot;

import java.util.*;

import com.oroarmor.discord_bot.commands.Command;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;

public class CommandManager {
    private static final Map<String, Command> COMMANDS = new TreeMap<>();
    public static String PREFIX = "!";

    public static void runCommand(Member member, MessageChannel channel, Message message) {
        if (message.getContentRaw().startsWith(PREFIX)) {
            List<String> tokens = Arrays.asList(message.getContentRaw().substring(1).split("\\s+"));
            String commandName = tokens.get(0);

            if(COMMANDS.containsKey(commandName)) {
                Command command = COMMANDS.get(commandName);
                if(command.getPermissions().validUser(member, channel)) {
                    command.run(member, channel, tokens);
                }
            }
        }
    }

    public static void addCommand(Command command){
        if(COMMANDS.containsKey(command.getName())) {
            throw new IllegalArgumentException("Cannot register two commands to the same name");
        }

        COMMANDS.put(command.getName(), command);
    }

    public static Map<String, Command> getCommands() {
        return COMMANDS;
    }
}

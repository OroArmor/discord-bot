/*
 * MIT License
 *
 * Copyright (c) 2021 OroArmor (Eli Orona)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.oroarmor.discordbot.commands;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;

public class CommandManager {
    private static final Map<String, Command> COMMANDS = new TreeMap<>();
    private static final Map<String, Command> ALIASES = new TreeMap<>();
    public static String PREFIX = "!";

    public static void runCommand(Member member, MessageChannelUnion channel, Message message) {
        if (message.getContentRaw().startsWith(PREFIX)) {
            List<String> tokens = Arrays.asList(message.getContentRaw().substring(1).split("\\s+"));
            boolean delete = tokens.get(tokens.size() - 1).equals("-d");
            if(delete) {
                tokens = tokens.subList(0, tokens.size() - 1);
                message.delete().queue();
            }

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
        } else if(COMMANDS.containsKey(alias)) {
            throw new IllegalArgumentException("Alias: " + alias +" is already a command");
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

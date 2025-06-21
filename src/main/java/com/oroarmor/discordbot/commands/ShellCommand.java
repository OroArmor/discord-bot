package com.oroarmor.discordbot.commands;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;

import java.io.*;
import java.util.List;

public class ShellCommand extends Command {
    private final String[] command;

    public ShellCommand(String name, String description, String[] command) {
        super(name, description, Permissions.OROARMOR);
        this.command = command;
    }

    @Override
    public void run(Member member, MessageChannel channel, List<String> tokens) {
        try {
            ProcessBuilder pb = new ProcessBuilder(command);
            Process process = pb.start();
            InputStream output = process.getInputStream();
            int code = process.waitFor();

            BufferedInputStream reader = new BufferedInputStream(output);
            String outputString = new String(reader.readAllBytes());

            channel.sendMessage("Process exited with code `" + code + "`\n```"+(outputString)+"```").queue();
        } catch (Exception e) {
            channel.sendMessage("Error executing process. Check log for more info.").queue();
            e.printStackTrace();
        }
    }
}


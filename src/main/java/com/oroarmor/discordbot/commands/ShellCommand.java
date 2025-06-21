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


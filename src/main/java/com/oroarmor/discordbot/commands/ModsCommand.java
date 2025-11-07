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

import java.util.List;
import java.util.stream.Collectors;

import com.oroarmor.discordbot.mods.Mod;
import com.oroarmor.discordbot.mods.Mods;
import com.oroarmor.discordbot.util.MessageEmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;

public class ModsCommand extends Command {
    public ModsCommand() {
        super("mods", "Gets the info about all mods.", Permissions.ANY);
    }

    @Override
    public void run(Member member, MessageChannelUnion channel, List<String> tokens) {
        if (tokens.size() == 1) {
            channel.sendMessageEmbeds(
                    new MessageEmbedBuilder()
                            .setTitle("My Mods")
                            .setDescription(
                                    Mods.getMods()
                                            .stream()
                                            .map(mod ->
                                                    "### **" + mod.getName() + "**:\n > Alias: " + mod.getAlias() + "\n" + mod.getDescription()
                                            ).collect(Collectors.joining("\n\n"))
                            ).build()
            ).queue();
        } else {
            Mod mod = Mods.getMod(tokens.get(1));
            if (mod == null) {
                channel.sendMessage("Mod: `" + tokens.get(1) + "`, is not a valid mod.").queue();
            } else {
                channel.sendMessageEmbeds(Mods.getModEmbed(mod)).queue();
            }
        }
    }

}

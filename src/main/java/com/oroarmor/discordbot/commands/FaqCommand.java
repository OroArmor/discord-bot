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
import java.util.Objects;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

public class FaqCommand extends Command{
    private long channelID;

    public FaqCommand() {
        super("faq", "Links to the #faq channel and lists them", Permissions.ANY);
    }

    @Override
    public void run(Member member, MessageChannel channel, List<String> tokens) {
        if(channelID == 0) {
            member.getGuild().getChannels().forEach(guildChannel -> {
                if(guildChannel.getName().equals("faq")) {
                    channelID = guildChannel.getIdLong();
                }
            });
        }

        if(tokens.size() == 1 || tokens.size() > 2 || !tokens.get(1).matches("\\d")) {
            channel.sendMessage(String.format("<#%d>", channelID)).queue();
            return;
        }

        try {
            List<Message> messages = ((MessageChannel) Objects.requireNonNull(member.getGuild().getGuildChannelById(channelID))).getHistoryFromBeginning(100).submit().get().getRetrievedHistory();
            for(Message message : messages) {
                if(message.getContentStripped().startsWith(tokens.get(1))) {
                    channel.sendMessageEmbeds(new EmbedBuilder().setTitle(String.format("FAQ %s", tokens.get(1))).setDescription(message.getContentRaw()).build()).queue();
                    return;
                }
            }
            channel.sendMessage(String.format("That FAQ does not exist. Check <#%d> for all FAQ posts", channelID)).queue();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

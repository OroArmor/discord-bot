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
                    channel.sendMessage(new EmbedBuilder().setTitle(String.format("FAQ %s", tokens.get(1))).setDescription(message.getContentRaw()).build()).queue();
                    return;
                }
            }
            channel.sendMessage(String.format("That FAQ does not exist. Check <#%d> for all FAQ posts", channelID)).queue();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

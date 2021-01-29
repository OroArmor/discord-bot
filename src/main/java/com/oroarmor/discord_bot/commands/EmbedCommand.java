package com.oroarmor.discord_bot.commands;

import java.util.List;

import com.oroarmor.discord_bot.CommandManager;
import com.oroarmor.discord_bot.util.MessageEmbededBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;

public class EmbedCommand extends Command {
    private final String title;
    private final int color;
    private final String embedDescription;

    public EmbedCommand(String name, String description, Permissions permissions, String title, int color, String embedDescription) {
        super(name, description, permissions);
        this.title = title;
        this.color = color;
        this.embedDescription = embedDescription;
    }

    @Override
    public void run(Member member, MessageChannel channel, List<String> tokens) {
        channel.sendMessage(new MessageEmbededBuilder().setTitle(this.title).setColor(color).setDescription(embedDescription).build()).queue();
    }
}

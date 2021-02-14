package com.oroarmor.discord_bot.commands;

import java.util.List;

import com.oroarmor.discord_bot.util.MessageEmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;

public class LinkCommand extends Command {
    protected final String url;
    protected final int color;

    public LinkCommand(String name, String url, int color) {
        super(name, "Links to OroArmor's " + name + " page.", Permissions.ANY);
        this.url = url;
        this.color = color;
    }

    @Override
    public void run(Member member, MessageChannel channel, List<String> tokens) {
        channel.sendMessage(new MessageEmbedBuilder().setTitle(name).setDescription("OroArmor's " + name + " page: " + url).setColor(color).build()).queue();
    }

    @Override
    public String getName() {
        return name.toLowerCase();
    }
}

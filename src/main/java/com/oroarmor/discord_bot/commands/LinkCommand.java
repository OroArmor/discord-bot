package com.oroarmor.discord_bot.commands;

import java.util.List;

import net.dv8tion.jda.api.entities.*;

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
        channel.sendMessage(new MessageEmbed(null, name, "OroArmor's " + name + " page: " + url, EmbedType.LINK, null, color, null, null, null, null, null, null, null)).queue();
    }

    @Override
    public String getName() {
        return name.toLowerCase();
    }
}

package com.oroarmor.discordbot.commands;

import java.util.List;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;

public abstract class Command {
    protected final String name;
    protected final String description;
    protected final Permissions permissions;

    public Command(String name, String description, Permissions permissions) {
        this.name = name;
        this.description = description;
        this.permissions = permissions;
    }

    public abstract void run(Member member, MessageChannel channel, List<String> tokens);

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Permissions getPermissions() {
        return permissions;
    }
}

package com.oroarmor.discordbot.commands;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;

public enum Permissions {
    ANY {
        @Override
        public boolean validUser(Member member, MessageChannel channel) {
            return true;
        }
    }, MODERATOR {
        @Override
        public boolean validUser(Member member, MessageChannel channel) {
            return member.getRoles().stream().anyMatch(role -> role.getName().equals("Mod Gang"));
        }
    };

    public abstract boolean validUser(Member member, MessageChannel channel);
}

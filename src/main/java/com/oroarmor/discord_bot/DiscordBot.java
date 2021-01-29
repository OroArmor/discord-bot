package com.oroarmor.discord_bot;

import com.oroarmor.discord_bot.commands.*;
import javax.annotation.Nonnull;
import javax.security.auth.login.LoginException;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/*
 * This Java source file was generated by the Gradle 'init' task.
 */
public class DiscordBot {
    public static void main(String[] args)
            throws LoginException, InterruptedException {
        // Note: It is important to register your ReadyListener before building
        JDA jda = JDABuilder.createDefault(System.getenv("BOT_TOKEN"))
                .addEventListeners(new EventListener())
                .build();

//        CommandManager.addCommand(new PingCommand());
        CommandManager.addCommand(new LinkCommand("Youtube", "https://www.youtube.com/channel/UCsbVQJhwgXIJ035XUCN9IRw", 0xff0000));
        CommandManager.addCommand(new LinkCommand("CurseForge", "https://www.curseforge.com/members/oroarmor/projects", 0x6441A4));
        CommandManager.addCommand(new LinkCommand("Modrinth", "https://modrinth.com/user/h74rYEcI", 0x079515));
        CommandManager.addCommand(new LinkCommand("Github", "https://github.com/OroArmor", 0x555555));
        CommandManager.addCommand(new LinkCommand("Patreon", "https://patreon.com/oroarmor", 0xFF6871));
        CommandManager.addCommand(new CommandsCommand());
        CommandManager.addCommand(new StringCommand("apply-for-mod", "Provides the link to the moderator application page.", Permissions.ANY, "Apply for moderator at: https://forms.gle/F4LPDH7xD96VknY39"));
        CommandManager.addCommand(new ModCommand());
        CommandManager.addCommand(new ModsCommand());
        CommandManager.addCommand(new ChangePrefixCommand());
        CommandManager.addCommand(new EmbedCommand("bintray", "Links to OroArmor's bintray repository", Permissions.ANY, "Bintray", 0x079515, "OroArmor's Bintray page: https://bintray.com/oroarmor/oroarmor\nOroArmor's Bintray Repo: https://dl.bintray.com/oroarmor/oroarmor/"));
        CommandManager.addCommand(new EmbedCommand("architectury", "Links to Architectury", Permissions.ANY, "Architectury",0xc76003, "Architectury is required to run Netherite Plus. Download the fabric version: https://www.curseforge.com/minecraft/mc-mods/architectury-fabric or the forge version: https://www.curseforge.com/minecraft/mc-mods/architectury-forge."));
        CommandManager.addCommand(new IssueCommand());
        // optionally block until JDA is ready
        jda.awaitReady();
    }

    public static class EventListener extends ListenerAdapter {
        public void onMessageReceived(MessageReceivedEvent event) {
            CommandManager.runCommand(event.getMember(), event.getChannel(), event.getMessage());
        }

        @Override
        public void onMessageReactionAdd(@Nonnull MessageReactionAddEvent event) {
            if(event.getChannel().getName().equals("rules")) {
                Role role = event.getGuild().getRoles().stream().filter(role1 -> role1.getName().equals("Mod Lover")).findFirst().get();
                event.getGuild().addRoleToMember(event.getMember(), role).queue();
            }
        }

        @Override
        public void onReady(@Nonnull ReadyEvent event) {
            System.out.println("Logged in!");
        }
    }
}

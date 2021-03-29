package com.oroarmor.discord_bot.commands;

import java.util.List;

import com.oroarmor.discord_bot.mods.Mod;
import com.oroarmor.discord_bot.mods.Mods;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;

import static com.oroarmor.discord_bot.mods.Mods.getModEmbed;

public class RefreshModsCommand extends Command{
    public RefreshModsCommand() {
        super("refresh-mods", "Refreshes the mod list", Permissions.MODERATOR);
    }

    @Override
    public void run(Member member, MessageChannel channel, List<String> tokens) {
        List<Mod> mods = Mods.getMods();
        mods.clear();
        Mods.getMods();
        channel.sendMessage("Mod List refreshed").queue();
    }
}

package com.github.tcn.plexi.discordBot.commands;

import com.github.tcn.plexi.discordBot.EmbedManager;
import com.github.tcn.plexi.discordBot.PlexiBot;
import com.github.tcn.plexi.ombi.OmbiCallers;
import com.github.tcn.plexi.settingsManager.Settings;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class GetMissingEpisodes extends Command {

    public GetMissingEpisodes() {
        this.name = "getMissing";
        this.arguments = "TVDb ID";
        this.ownerCommand = false;
        this.guildOnly = false;
    }

    @Override
    protected void execute(CommandEvent event) {
        OmbiCallers ombiCallers = new OmbiCallers();
        EmbedManager embedManager = new EmbedManager();
        event.reply(embedManager.createMissingEpisodeEmbed(ombiCallers.getMissingEpisodeArray(event.getArgs())).build());

        //print command usage to the log
        Settings.getInstance().getLogger().info(event.getAuthor().getName() + " has used the getMissing command for ID: " + event.getArgs());
    }
}

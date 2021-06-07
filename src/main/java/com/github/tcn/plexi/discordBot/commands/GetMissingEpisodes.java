package com.github.tcn.plexi.discordBot.commands;

import com.github.tcn.plexi.discordBot.EmbedManager;
import com.github.tcn.plexi.discordBot.PlexiBot;
import com.github.tcn.plexi.ombi.OmbiCallers;
import com.github.tcn.plexi.ombi.templateClasses.tv.moreInfo.TvInfo;
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

        //first we want to get a tv object for the thingy
        TvInfo tvInfo = ombiCallers.ombiTvInfo(event.getArgs());

        //next we can extract the title of the show from the tvInfo object
        String name = tvInfo.getTitle();

        event.reply(embedManager.createMissingEpisodeEmbed(ombiCallers.getMissingEpisodeArray(tvInfo), name).build());
    }
}

package com.github.tcn.plexi.discordBot.commands;

import com.github.tcn.plexi.ombi.OmbiCallers;
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


        event.reply(ombiCallers.getMissingEpisodeEmbed(event.getArgs()).build());
    }
}

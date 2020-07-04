package com.github.tcn.plexi.discordBot.commands;

import com.github.tcn.plexi.discordBot.EmbedManager;
import com.github.tcn.plexi.discordBot.PlexiBot;
import com.github.tcn.plexi.ombi.OmbiCallers;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class getPingCommand extends Command {

    public getPingCommand() {
        this.name = "ping";
        this.help = "Returns the time in ms that all of the apis took to respond to plexi";
        this.guildOnly = false;
    }

    @Override
    protected void execute(CommandEvent event) {
        //create OmbiCaller obj
        OmbiCallers caller = new OmbiCallers();
        EmbedManager embedManager = new EmbedManager();
        event.reply(embedManager.createPingEmbed(PlexiBot.getInstance().getGatewayPing(), PlexiBot.getInstance().getRestPing(), caller.getPingTime()).build());
    }
}

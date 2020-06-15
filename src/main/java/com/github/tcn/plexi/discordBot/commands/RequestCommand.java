package com.github.tcn.plexi.discordBot.commands;

import com.github.tcn.plexi.ombi.OmbiCallers;
import com.github.tcn.plexi.ombi.templateClasses.tv.moreInfo.TvInfo;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;


public class RequestCommand extends Command {

    public RequestCommand() {
        this.name = "request";
        this.help = "requests media to be added to the plex server";
        this.arguments = "<tv|movie> <TVDb ID|TMDb ID>";
        this.aliases = new String[]{"r"};
        this.ownerCommand = false;
        this.guildOnly = true;
    }


    @Override
    protected void execute(CommandEvent event) {
        OmbiCallers ombiCallers = new OmbiCallers();
        String[] args = event.getArgs().split(" ", 2);

        if (args[0].toLowerCase().matches("tv|television|telly|tele|t")) {
            try {
                //TODO: this command must change to conform to the new request method.
                OmbiCallers caller = new OmbiCallers();
                TvInfo tvInfo = caller.ombiTvInfo(args[1]);

                event.reply(ombiCallers.requestTv(args[1], true, tvInfo.getPlexAvailabilityInt(), tvInfo));
            } catch (IllegalArgumentException e) {
                event.reply("Error requesting media");
            }
        } else if (args[0].toLowerCase().matches("movie|film|feature|flick|cinematic|cine|movies|films|features|flicks|m")) {
            try {
                event.reply(ombiCallers.requestMovie(args[1]));

            } catch (IllegalArgumentException e) {
                event.reply("Error requesting media");
            }
        } else {
            event.getChannel().sendMessage("Malformed Command!").queue();
        }

    }
}

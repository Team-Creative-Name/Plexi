package com.github.tcn.plexi.discordBot.commands;

import com.github.tcn.plexi.discordBot.EmbedManager;
import com.github.tcn.plexi.ombi.OmbiCallers;
import com.github.tcn.plexi.ombi.templateClasses.movies.moreInfo.MovieInfo;
import com.github.tcn.plexi.ombi.templateClasses.tv.moreInfo.TvInfo;
import com.github.tcn.plexi.settingsManager.Settings;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class MediaInfoCommand extends Command {

    public MediaInfoCommand() {
        this.name = "info";
        this.help = "Prints media information given the movie/tv db ID number";
        this.arguments = "tv|movie> <TVDb ID|TMDb ID>";
        this.aliases = new String[]{"i", "I", "Info"};
        this.ownerCommand = false;
        this.guildOnly = false;
    }

    @Override
    protected void execute(CommandEvent event) {

        //Create objects
        OmbiCallers ombiCaller = new OmbiCallers();
        EmbedManager eManager = new EmbedManager();

        String[] args = event.getArgs().split(" ", 2);

        //check to make sure the args are correctly formed
        if (args.length != 2) {
            event.reply("Malformed Command!");
            return;
        }

        if (args[0].toLowerCase().matches("tv|television|telly|tele|t")) {

            //retrieve the TvInfo Object
            TvInfo newInfo = ombiCaller.ombiTvInfo(args[1]);

            event.reply(eManager.createTvMoreInfoEmbed(newInfo).build());

            //log the command's usage
            Settings.getInstance().getLogger().info(event.getAuthor().getName() + " has used the info command for TVDb_ID: " + args[1]);


        } else if (args[0].toLowerCase().matches("movie|film|feature|flick|cinematic|cine|movies|films|features|flicks|m")) {

            MovieInfo newInfo = ombiCaller.ombiMovieInfo(args[1]);
            event.reply(eManager.createMovieMoreInfoEmbed(newInfo).build());

            //log the command's usage
            Settings.getInstance().getLogger().info(event.getAuthor().getName() + " has used the info command for TMDb_ID: " + args[1]);
        } else {
            event.reply("Malformed Command! - Please specify `movie` or `tv`");
            //log the command's usage
            Settings.getInstance().getLogger().info(event.getAuthor().getName() + " has attempted to use the info command but provided invalid information");
        }

    }
}

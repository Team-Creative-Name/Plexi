package discordBot.commands;

import apis.ombi.OmbiCallers;
import apis.ombi.templateClasses.movies.moreInfo.MovieInfo;
import apis.ombi.templateClasses.tv.moreInfo.TvInfo;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import discordBot.EmbedManager;

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
            //log the request in the console
            System.out.println("Asked for tv info: " + args[1]);

            //retrieve the TvInfo Object
            TvInfo newInfo = ombiCaller.ombiTvInfo(args[1]);

            //retrieve the EmbedBuilder Object made from the newInfo Object

            event.reply(eManager.createTvMoreInfoEmbed(newInfo).build());


        } else if (args[0].toLowerCase().matches("movie|film|feature|flick|cinematic|cine|movies|films|features|flicks|m")) {

            MovieInfo newInfo = ombiCaller.ombiMovieInfo(args[1]);
            event.reply(eManager.createMovieMoreInfoEmbed(newInfo).build());
        } else {
            event.reply("Malformed Command! - Please specify `movie` or `tv`");
        }


    }
}

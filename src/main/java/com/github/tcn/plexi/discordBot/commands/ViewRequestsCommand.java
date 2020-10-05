package com.github.tcn.plexi.discordBot.commands;

import com.github.tcn.plexi.discordBot.EmbedManager;
import com.github.tcn.plexi.ombi.OmbiCallers;
import com.github.tcn.plexi.ombi.templateClasses.movies.requestList.MovieRequestList;
import com.github.tcn.plexi.ombi.templateClasses.tv.tvRequests.TvRequestList;
import com.github.tcn.plexi.paginators.simplePaginators.ArrowPaginator;
import com.github.tcn.plexi.settingsManager.Settings;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import org.slf4j.Logger;

public class ViewRequestsCommand extends Command {

    private final ArrowPaginator.Builder paginatorBuilder;
    Logger logger = Settings.getInstance().getLogger();

    public ViewRequestsCommand(EventWaiter waiter){
        this.name = "viewRequests";
        this.help = "Views the requests that are currently in the request list and have not been filled.";
        this.arguments = "<tv|movie>";
        this.aliases = new String[]{"viewrequests", "VR", "vr", "v"};
        this.ownerCommand = Settings.getInstance().getUsersViewRequests();
        this.guildOnly = false;

        //set up the paginator
        paginatorBuilder = new ArrowPaginator.Builder()
                .setDefaultFinalAction()
                .setEventWaiter(waiter);
    }

    @Override
    protected void execute(CommandEvent event) {
        OmbiCallers caller = new OmbiCallers();

        String[] args = event.getArgs().split( " ", 2); //split the args

        EmbedManager embedManager = new EmbedManager();

        if(args[0].toLowerCase().matches("tv|television|telly|tele|t")){ //if the user specified tv
            logger.info(event.getAuthor().getName() + " has used the viewRequests command for TV shows");

            //we can use the TV lite endpoint to get an array of tv requests

            TvRequestList[] tvRequests = caller.getTvRequestListArray();

            //check to make sure that this array is not empty
            if(tvRequests.length == 0){
                event.reply("There are no tv requests! \n Search for some by using the search command.");
            }else{
                paginatorBuilder.setPages(embedManager.getUnfilledTvRequestList(tvRequests));

                ArrowPaginator paginator = paginatorBuilder.setUsers(event.getAuthor()).build();
                paginator.paginate(event.getChannel(), 0);
            }


        }else if(args[0].toLowerCase().matches("movie|film|feature|flick|cinematic|cine|movies|films|features|flicks|m")){ //or a movie
            logger.info(event.getAuthor().getName() + " has used the viewRequests command for movies");

            MovieRequestList[] requestList = caller.getMovieRequests();

            if(requestList.length == 0){
                event.reply("There are no movie requests! \n Search for some by using the search command.");
            }else{
                paginatorBuilder.setPages(embedManager.getUnfilledMovieRequestListArray(requestList));

                ArrowPaginator paginator = paginatorBuilder.setUsers(event.getAuthor()).build();
                paginator.paginate(event.getChannel(), 0);
            }
        }else{
            event.getChannel().sendMessage("Malformed Command!").queue();
            Settings.getInstance().getLogger().info(event.getAuthor().getName() + " has attempted to use the viewRequests command but failed to specify the media type!");
        }
    }
}

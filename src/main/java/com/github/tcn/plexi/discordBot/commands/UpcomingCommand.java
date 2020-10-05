package com.github.tcn.plexi.discordBot.commands;

import com.github.tcn.plexi.discordBot.EmbedManager;
import com.github.tcn.plexi.ombi.OmbiCallers;
import com.github.tcn.plexi.ombi.templateClasses.movies.search.MovieSearch;
import com.github.tcn.plexi.ombi.templateClasses.tv.search.TvSearch;
import com.github.tcn.plexi.paginators.searchPaginators.SearchPaginator;
import com.github.tcn.plexi.settingsManager.Settings;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import org.slf4j.Logger;

import java.util.Set;

/**
 * This class creates a command that allows users to view soon to be released movies and television shows.
 * If A user sees ones that they might want to watch in the future, they can request that show from this command via
 * the reactions located below the paginated embed.
 */


public class UpcomingCommand extends Command {

    private final SearchPaginator.Builder paginatorBuilder;
    private Logger logger;

    public UpcomingCommand(EventWaiter waiter){
        this.name = "upcoming";
        this.help = "This command lists upcoming movie or tv releases";
        this.arguments = "<tv|movie>";
        this.aliases = new String[]{"u", "Upcoming", "up", "U", "Up"};
        this.ownerCommand = false;
        this.guildOnly = true;

        //This class uses the SearchPaginator to show movies and tv releases
        paginatorBuilder = new SearchPaginator.Builder()
                .setDefaultFinalAction()
                .setEventWaiter(waiter);

        logger = Settings.getInstance().getLogger();
    }

    @Override
    protected void execute(CommandEvent event) {

        OmbiCallers caller = new OmbiCallers();
        EmbedManager embedManager = new EmbedManager();

        //we need to call different methods depending on the media type
        if(event.getArgs().toLowerCase().matches("tv|television|telly|tele|t|s|show")){
            logger.info(event.getAuthor() + " has used the upcoming tv command.");



            TvSearch[] result = caller.getUpcomingTvShows();

            //ensure a non-empty result
            if(result.length == 0){
                event.reply("There are no TV shows scheduled to be released soon");
            }else{
                //lets tell the user what we are presenting
                event.reply("Here are some upcoming TV shows:");

                paginatorBuilder.setMediaType(1).setPages(embedManager.getTvSearchEmbedArray(result));

                SearchPaginator p = paginatorBuilder
                        .setUsers(event.getAuthor())
                        .build();
                p.paginate(event.getChannel(), 1);
            }


        }else if(event.getArgs().toLowerCase().matches("((m(ovie)?|film|feature|flick)s?)|(cine(matic)?)")){
            logger.info(event.getAuthor() + " has used the upcoming movie command.");

            MovieSearch[] result = caller.getUpcomingMovies();

            //make sure that the array is not empty (it really shouldn't be unless there was a worldwide pandemic or something...)
            if(result.length == 0){
                event.reply("There are currently no movies scheduled to be released soon.");
            }else{
                //lets tell the user what we are presenting
                event.reply("Here are some upcoming movies:");

                paginatorBuilder.setMediaType(2).setPages(embedManager.getMovieSearchEmbedArray(result));

                SearchPaginator p = paginatorBuilder
                        .setUsers(event.getAuthor())
                        .build();

                p.paginate(event.getChannel(), 1);
            }

        }else{
            logger.info(event.getAuthor() + " has incorrectly used the upcoming command!");
            event.reply("Malformed Command!");
        }

    }
}

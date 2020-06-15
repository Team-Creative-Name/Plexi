package com.github.tcn.plexi.discordBot.commands;

import com.github.tcn.plexi.discordBot.EmbedManager;
import com.github.tcn.plexi.ombi.OmbiCallers;
import com.github.tcn.plexi.ombi.templateClasses.movies.search.MovieSearch;
import com.github.tcn.plexi.ombi.templateClasses.tv.search.TvSearch;
import com.github.tcn.plexi.paginators.searchPaginators.SearchPaginator;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.api.exceptions.PermissionException;

import java.util.ArrayList;


public class SearchCommand extends Command {

    private final SearchPaginator.Builder ePBuilder;

    public SearchCommand(EventWaiter waiter) {
        this.name = "search";
        this.help = "This command allows the user to search the Plexi database for matching titles";
        this.arguments = "<tv|movie> <name of show|name of movie>";
        this.aliases = new String[]{"s", "Search", "S"};
        this.ownerCommand = false;
        this.guildOnly = true;


        ePBuilder = new SearchPaginator.Builder()

                .setFinalAction(message -> {
                    try {
                        message.clearReactions().queue();
                    } catch (PermissionException ex) {
                        message.delete().queue();
                    }
                })
                .setEventWaiter(waiter);
    }

    @Override
    protected void execute(CommandEvent event) {
        String[] args = event.getArgs().split(" ", 2);

        //inform the user that they broke it
        if (args.length != 2) {
            event.reply("Missing title of media");
            return;
        }

        if (args[0].toLowerCase().matches("tv|television|telly|tele|t|s|show")) {

            //Create new objects
            OmbiCallers tvSearcher = new OmbiCallers();
            EmbedManager embedManager = new EmbedManager();

            System.out.println(event.getAuthor().getName() + " has searched for " + args[1]);
            //Retrieve array of TvSearch Objects - each object is a search result
            TvSearch[] result = tvSearcher.ombiTvSearch(args[1]);

            //check to see if there were any results{
            if (result.length == 0) {
                event.reply("No Results Found!");
            } else {

                //Return the name of the first show on the calendar
                ePBuilder.setMediaType(1);
                ePBuilder.setPages(embedManager.getPostTvSearchEmbed(result));


                SearchPaginator p = ePBuilder
                        .setUsers(event.getAuthor())
                        .build();

                p.paginate(event.getChannel(), 1);
            }


            //event.getChannel().sendMessage("Here is the first result: " + result[0].getTitle()).queue();
        } else if (args[0].toLowerCase().matches("movie|film|feature|flick|cinematic|cine|movies|films|features|flicks|m")) {

            OmbiCallers moviesearcher = new OmbiCallers();
            EmbedManager embedManager = new EmbedManager();
            System.out.println(event.getAuthor().getName() + " has searched for " + args[1]);

            MovieSearch[] result = moviesearcher.ombiMovieSearch(args[1]);


            //make sure the array is not empty
            if (result.length == 0) {
                event.reply("No Results Found!");
            } else {
                ePBuilder.setMediaType(2).setPages(embedManager.getPostMovieSearchEmbed(result));


                SearchPaginator p = ePBuilder
                        .setUsers(event.getAuthor())
                        .build();

                p.paginate(event.getChannel(), 1);
            }

        } else {
            event.getChannel().sendMessage("Malformed Command!").queue();
        }
    }

    private ArrayList<Integer> generateEpisodeIdArray(TvSearch[] tvArray) {

        ArrayList<Integer> toReturn = new ArrayList<>();

        for (TvSearch tvSearch : tvArray) {
            toReturn.add(tvSearch.getId());
        }

        return toReturn;
    }

    private ArrayList<Integer> generateMovieIdArray(MovieSearch[] movieArray) {
        ArrayList<Integer> toReturn = new ArrayList<>();

        for (MovieSearch movieSearch : movieArray) {
            toReturn.add(movieSearch.getId());
        }
        return toReturn;
    }

}
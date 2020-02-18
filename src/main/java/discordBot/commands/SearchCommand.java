package discordBot.commands;

import apis.ombi.OmbiCallers;
import apis.ombi.templateClasses.movies.search.MovieSearch;
import apis.ombi.templateClasses.tv.search.TvSearch;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import discordBot.EmbedManager;
import net.dv8tion.jda.api.exceptions.PermissionException;
import paginators.pWithSubmenu.SearchEmbedMenu;

import java.util.ArrayList;


public class SearchCommand extends Command {

    private final SearchEmbedMenu.Builder ePBuilder;

    public SearchCommand(EventWaiter waiter) {
        this.name = "search";
        this.help = "This command allows the user to search the Plexi database for matching titles";
        this.arguments = "<tv|movie> <name of show|name of movie>";
        this.aliases = new String[]{"s", "Search", "S"};
        this.ownerCommand = false;
        this.guildOnly = true;


        ePBuilder = new SearchEmbedMenu.Builder()

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

        if (args[0].toLowerCase().matches("tv|television|telly|tele|t")) {

            //Create new objects
            OmbiCallers tvSearcher = new OmbiCallers();
            EmbedManager embeder = new EmbedManager();

            System.out.println(args[1]);
            //Retrieve array of TvSearch Objects - each object is a search result
            TvSearch[] tester = tvSearcher.ombiTvSearch(args[1]);

            //check to see if there were any results{
            if (tester.length == 0) {
                event.reply("No Results Found!");
            } else {

                //Return the name of the first show on the calendar
                ePBuilder.setTvType();
                ePBuilder.setEmbedArray(embeder.getPostTvSearchEmbed(tester));
                ePBuilder.setSubmenuEmbedArray(generateEpisodeIdArray(tester));
                ePBuilder.setCommandEvent(event);
                ePBuilder.setWrapPageEnds(true);
                SearchEmbedMenu p = ePBuilder
                        .setUsers(event.getAuthor())
                        .build();

                p.paginate(event.getChannel(), 1);
            }


            //event.getChannel().sendMessage("Here is the first result: " + tester[0].getTitle()).queue();
        } else if (args[0].toLowerCase().matches("movie|film|feature|flick|cinematic|cine|movies|films|features|flicks|m")) {

            OmbiCallers moviesearcher = new OmbiCallers();
            EmbedManager embeder = new EmbedManager();
            System.out.println(args[1]);

            MovieSearch[] result = moviesearcher.ombiMovieSearch(args[1]);


            //make sure the array is not empty
            if (result.length == 0) {
                event.reply("No Results Found!");
            } else {
                ePBuilder.setMovieType();
                ePBuilder.setEmbedArray(embeder.getPostMovieSearchEmbed(result));
                ePBuilder.setSubmenuEmbedArray(generateMovieIdArray(result));
                ePBuilder.setWrapPageEnds(true);

                SearchEmbedMenu p = ePBuilder
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

        for (int i = 0; i < tvArray.length; i++) {
            toReturn.add(tvArray[i].getId());
        }

        return toReturn;
    }

    private ArrayList<Integer> generateMovieIdArray(MovieSearch[] movieArray) {
        ArrayList<Integer> toReturn = new ArrayList<>();

        for (int i = 0; i < movieArray.length; i++) {
            toReturn.add(movieArray[i].getId());
        }
        return toReturn;
    }

}

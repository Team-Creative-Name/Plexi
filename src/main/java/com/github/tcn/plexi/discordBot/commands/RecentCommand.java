package com.github.tcn.plexi.discordBot.commands;

import com.github.tcn.plexi.discordBot.EmbedManager;
import com.github.tcn.plexi.ombi.OmbiCallers;
import com.github.tcn.plexi.ombi.templateClasses.movies.recentlyAdded.RecentMovie;
import com.github.tcn.plexi.ombi.templateClasses.movies.search.MovieSearch;
import com.github.tcn.plexi.ombi.templateClasses.tv.recentlyAdded.RecentTv;
import com.github.tcn.plexi.ombi.templateClasses.tv.search.TvSearch;
import com.github.tcn.plexi.paginators.recentPaginator.RecentPaginator;
import com.github.tcn.plexi.paginators.searchPaginators.SearchPaginator;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.api.exceptions.PermissionException;

import java.util.ArrayList;


public class RecentCommand extends Command {

    private final RecentPaginator.Builder ePBuilder;

    public RecentCommand(EventWaiter waiter) {
        this.name = "recent";
        this.help = "This command allows the user to search the Plexi database for recently added titles";
        this.arguments = "<tv|movie>";
        this.aliases = new String[]{"re", "Recent", "Re"};
        this.ownerCommand = false;
        this.guildOnly = true;


        ePBuilder = new RecentPaginator.Builder()

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

        //Create new objects
        OmbiCallers caller = new OmbiCallers();
        EmbedManager embedManager = new EmbedManager();

        if (args[0].toLowerCase().matches("(t(v|el(evision|ly|e))?|s(how)?)")) {
            // TV
            // RecentTv[] result = caller.getRecentTvArray();
            RecentTv[] result = new RecentTv[1];
            RecentTv recentTv = new RecentTv();
            recentTv.setId(11234);
            recentTv.setTitle("aaaaaaaaaaaaaaaab");
            recentTv.setTvDbId("stringy");
            recentTv.setReleaseYear("3041");
            recentTv.setAddedAt("now");
            recentTv.setSeasonNumber(-5);
            recentTv.setEpisodeNumber(100000000);
            result[0] = recentTv;

            ePBuilder.setPages(embedManager.getRecentTVArray(result));

            RecentPaginator p = ePBuilder
                    .setUsers(event.getAuthor())
                    .build();

            p.paginate(event.getChannel(), 1);

        } else if (args[0].toLowerCase().matches("((m(ovie)?|film|feature|flick)s?)|(cine(matic)?)")) {
            // Movie
            RecentMovie[] result = caller.getRecentMovieArray();

            ePBuilder.setPages(embedManager.getRecentMovieArray(result));


            RecentPaginator p = ePBuilder
                    .setUsers(event.getAuthor())
                    .build();

            p.paginate(event.getChannel(), 1);

        } else {
            event.getChannel().sendMessage("Malformed Command!").queue();
        }
    }
}

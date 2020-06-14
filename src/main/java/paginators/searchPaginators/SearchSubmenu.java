package paginators.searchPaginators;

import apis.ombi.OmbiCallers;
import apis.ombi.templateClasses.movies.moreInfo.MovieInfo;
import apis.ombi.templateClasses.tv.moreInfo.TvInfo;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.jagrosh.jdautilities.menu.Menu;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.requests.RestAction;
import net.dv8tion.jda.internal.utils.Checks;
import paginators.Paginator;

import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class SearchSubmenu extends Paginator {

    //this class has some additional variables to keep track of

    //automatically filled in globals
    int MEDIA_TYPE;
    String MEDIA_ID;
    boolean IS_REQUESTED;
    //this can be a bit funky, but tv shows can have 3 forms all, partial or nothing available.
    // 0 = nothing  -- 1 = partial -- 2 = full
    int AVAILABILITY;
    TvInfo TV_INFO;
    MovieInfo MOVIE_INFO;


    public SearchSubmenu(EventWaiter waiter, Set<User> users, Set<Role> roles, long timeout, TimeUnit timeUnit, ArrayList<EmbedBuilder> pages, boolean wrapPages, Consumer<Message> finalAction, TvInfo tvinfo, MovieInfo movieInfo) {
        super(waiter, users, roles, timeout, timeUnit, new String[]{":thumbsup:", "thumbsdown", ":new:", ":stop_sign:"}, pages, wrapPages, finalAction);

        //globals need to be set differently depending on the media type
        if (tvinfo != null) {
            //TV media type
            MEDIA_TYPE = 1;
            MEDIA_ID = String.valueOf(tvinfo.getId());
            IS_REQUESTED = tvinfo.getRequested();
            AVAILABILITY = tvinfo.getPlexAvailabilityInt();
            TV_INFO = tvinfo;
        } else {
            //movie media type
            MEDIA_TYPE = 2;
            MEDIA_ID = String.valueOf(movieInfo.getId());
            IS_REQUESTED = movieInfo.getRequested();
            AVAILABILITY = movieInfo.getAvailable() ? 2 : 0;
            MOVIE_INFO = movieInfo;
        }

    }

    @Override
    protected void handleMessageReactionAddAction(MessageReactionAddEvent event, Message message, int pageNum) {

        OmbiCallers caller = new OmbiCallers();

        if (REACTIONS[0].getUnicode().equals(event.getReaction().getReactionEmote().getName())) { // the first item in the array is 👍
            //we need to determine the media type so we can make the proper API call
            //Media type 1 is for TV; media type 2 is for movies
            if (MEDIA_TYPE == 1) {
                event.getChannel().sendMessage(caller.requestTv(MEDIA_ID, false, AVAILABILITY, TV_INFO)).queue();
            } else if (MEDIA_TYPE == 2) {
                event.getChannel().sendMessage(caller.requestMovie(MEDIA_ID)).queue();
            }

        } else if (REACTIONS[1].getUnicode().equals(event.getReaction().getReactionEmote().getName())) { //the second item in the array is 👎
            //remove media from the request list
            //TODO: Make this do something - note, this is currently not implemented in OmbiCaller at all (fix that too)
            event.getChannel().sendMessage("Sorry, we cant remove requests at the moment").queue();

        } else if (REACTIONS[2].getUnicode().equals(event.getReaction().getReactionEmote().getName())) { //the third item in the array is 🆕
            //there *should* be no way to get here if the media type isnt 1, but still
            if (MEDIA_TYPE == 1) {
                event.getChannel().sendMessage(caller.requestTv(MEDIA_ID, true, AVAILABILITY, TV_INFO)).queue();
            }

        } //the stop emote will do nothing but it will still end the paginator

        //since we know the reaction is valid due to the parent class checking, we *should* be safe to call the final action after any reaction
        //This will close the paginator, remove reactions and stop it from listening for events.
        FINAL_ACTION.accept(message);
    }


    @Override
    protected void enterSubmenu(Message oldMessage, int pageNum) {
        //This menu does not have a submenu
    }

    //This is overridden because we need to do some special logic around adding emotes based upon media info
    @Override
    protected void initialize(RestAction<Message> action, int pageNum) {
        action.queue(m -> {
            m.clearReactions().queue();

            //add emotes depending on media status
            if (!IS_REQUESTED && (AVAILABILITY == 1 || AVAILABILITY == 0)) {
                m.addReaction(REACTIONS[0].getUnicode()).queue();
            } else if (IS_REQUESTED && (AVAILABILITY == 1 || AVAILABILITY == 0)) {
                m.addReaction(REACTIONS[1].getUnicode()).queue();
            }
            //add new reaction if the media type is a tv show (1)
            if (MEDIA_TYPE == 1) {
                m.addReaction(REACTIONS[2].getUnicode()).queue();
            }
            m.addReaction(REACTIONS[3].getUnicode()).queue(v -> pagination(m, pageNum), t -> pagination(m, pageNum));
        });
    }

    //create temp builder class
    //TODO: Remove builder and replace
    public static class Builder extends Menu.Builder<Builder, SearchSubmenu> {

        private ArrayList<EmbedBuilder> embeds = new ArrayList<>();

        private boolean wrapPageEnds = false;
        private Consumer<Message> finalAction = m -> m.delete().queue();
        private String text = null;
        private int type = 1;
        private CommandEvent event;
        private TvInfo tvInfo = null;
        private MovieInfo movieInfo = null;

        @Override
        public SearchSubmenu build() {
            Checks.check(waiter != null, "Must set an EventWaiter");
            Checks.check(!embeds.isEmpty(), "Must include at least one item to paginate");
            Checks.check(tvInfo == null ^ movieInfo == null, "Must include info object!");

            return new SearchSubmenu(waiter, users, roles, timeout, unit, embeds, wrapPageEnds, finalAction, tvInfo, movieInfo);

        }

        public Builder setEmbedArray(ArrayList<EmbedBuilder> embeds) {
            this.embeds = embeds;
            return this;
        }


        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Builder setFinalAction(Consumer<Message> finalAction) {
            this.finalAction = finalAction;
            return this;
        }

        public Builder setTvType() {
            this.type = 1;
            return this;
        }

        public Builder setMovieType() {
            this.type = 2;
            return this;
        }

        public Builder setTvInfo(TvInfo newTvInfo) {
            tvInfo = newTvInfo;
            return this;
        }

        public Builder setMovieInfo(MovieInfo newMovieInfo) {
            movieInfo = newMovieInfo;
            return this;
        }

        public void setCommandEvent(CommandEvent event) {
            this.event = event;
        }

        public void setWrapPageEnds(boolean wrapPageEnds) {
            this.wrapPageEnds = wrapPageEnds;
        }
    }

}





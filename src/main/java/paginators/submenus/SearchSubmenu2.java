package paginators.submenus;

import apis.ombi.OmbiCallers;
import apis.ombi.templateClasses.movies.moreInfo.MovieInfo;
import apis.ombi.templateClasses.tv.moreInfo.TvInfo;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.requests.RestAction;
import paginators.Paginator;

import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class SearchSubmenu2 extends Paginator {

    //this class has some additional variables to keep track of

    //automatically filled in globals
    int MEDIA_TYPE;
    int MEDIA_ID;
    boolean IS_REQUESTED;
    //this can be a bit funky, but tv shows can have 3 forms all, partial or nothing available.
    // 0 = nothing  -- 1 = partial -- 2 = full
    int AVAILABILITY;


    public SearchSubmenu2(EventWaiter waiter, Set<User> users, Set<Role> roles, long timeout, TimeUnit timeUnit, ArrayList<EmbedBuilder> pages, boolean wrapPages, Consumer<Message> finalAction, TvInfo tvinfo, MovieInfo movieInfo) {
        super(waiter, users, roles, timeout, timeUnit, new String[]{":thumbsup:", "thumbsdown", ":new:", ":stop_sign:"}, pages, wrapPages, finalAction);

        //globals need to be set differently depending on the media type
        if (tvinfo != null) {
            //TV media type
            MEDIA_TYPE = 1;
            MEDIA_ID = tvinfo.getId();
            IS_REQUESTED = tvinfo.getRequested();
            AVAILABILITY = tvinfo.getPlexAvailabilityInt();
        } else {
            //movie media type
            MEDIA_TYPE = 2;
            MEDIA_ID = movieInfo.getId();
            IS_REQUESTED = movieInfo.getRequested();
            AVAILABILITY = movieInfo.getAvailable() ? 2 : 0;

        }

    }

    @Override
    protected void handleMessageReactionAddAction(MessageReactionAddEvent event, Message message, int pageNum) {

        OmbiCallers caller = new OmbiCallers();

        if (REACTIONS[0].getUnicode().equals(event.getReaction().getReactionEmote().getName())) { // the first item in the array is 👍
            //we need to determine the media type so we can make the proper API call
            //Media type 1 is for TV; media type 2 is for movies
            if (MEDIA_TYPE == 1) {
                //TODO: Implement ombi TV caller
            } else if (MEDIA_TYPE == 2) {
                //TODO: Implement ombi movie caller
            }

        } else if (REACTIONS[1].getUnicode().equals(event.getReaction().getReactionEmote().getName())) { //the second item in the array is 👎
            //remove media from the request list
            //TODO: Make this do something - note, this is currently not implemented in OmbiCaller at all (fix that too)
            event.getChannel().sendMessage("Sorry, we cant remove requests at the moment").queue();

        } else if (REACTIONS[2].getUnicode().equals(event.getReaction().getReactionEmote().getName())) { //the third item in the array is 🆕
            //there *should* be no way to get here if the media type isnt 1, but still
            if (MEDIA_TYPE == 1) {
                //TODO: Implement ombi latest TV caller
            }

        } //the stop emote will do nothing but it will still end the paginator

        //since we know the reaction is valid due to the parent class checking, we *should* be safe to call the final action after any reaction
        //This will close the paginator, remove reactions and stop it from listening for events.
        FINAL_ACTION.accept(message);
    }

    @Override
    protected void enterSubmenu(MessageChannel channel) {
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
            m.addReaction(STOP).queue(v -> pagination(m, pageNum), t -> pagination(m, pageNum));
        });
    }


}

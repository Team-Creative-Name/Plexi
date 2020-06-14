package paginators.searchPaginators;

import apis.ombi.OmbiCallers;
import apis.ombi.templateClasses.movies.moreInfo.MovieInfo;
import apis.ombi.templateClasses.tv.moreInfo.TvInfo;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.jagrosh.jdautilities.menu.Menu;
import discordBot.EmbedManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.exceptions.PermissionException;
import net.dv8tion.jda.internal.utils.Checks;
import paginators.Paginator;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

//This is what the reaction array contains for this menu
//String[] REACTIONS = {"\u25C0",        //◀️
//                      "\uD83D\uDED1",  //🛑
//                      "\u2705",        //✅
//                      "\u25B6"}        //▶️️


public class SearchPaginator extends Paginator {

    //add a few variables pertaining to media type
    private final int MEDIA_TYPE;


    public SearchPaginator(EventWaiter waiter, Set<User> users, Set<Role> roles, long timeout, TimeUnit timeUnit, ArrayList<EmbedBuilder> pages, boolean wrapPages, Consumer<Message> finalAction, int mediaType) {
        super(waiter, users, roles, timeout, timeUnit, new String[]{":arrow_backward:", ":stop_sign:", ":white_check_mark:", ":arrow_forward:"}, pages, wrapPages, finalAction);
        MEDIA_TYPE = mediaType;
    }

    @Override
    protected void handleMessageReactionAddAction(MessageReactionAddEvent event, Message message, int pageNum) {
        int newPageNum = pageNum;


        if (REACTIONS[0].getUnicode().equals(event.getReaction().getReactionEmote().getName())) { // the first item in the array is the left arrow
            //decrement pageNum
            if (pageNum == 1 && WRAP_PAGES) {
                newPageNum = MENU_PAGE_COUNT;
            }
            if (pageNum > 1) {
                newPageNum--;
            }
        } else if (REACTIONS[1].getUnicode().equals(event.getReaction().getReactionEmote().getName())) { //the second item in the array is the stop sign
            //perform the final action
            FINAL_ACTION.accept(message);

            //return bc we dont want to do anything else
            return;

        } else if (REACTIONS[2].getUnicode().equals(event.getReaction().getReactionEmote().getName())) { //the third item in the array is the check mark
            //close this paginator
            FINAL_ACTION.accept(message);
            //Enter the submenu
            enterSubmenu(message, pageNum - 1);

            //return bc we dont want the main menu to override anything about the submenu
            return;

        } else if (REACTIONS[3].getUnicode().equals(event.getReaction().getReactionEmote().getName())) { //the last item is the right arrow
            //Increment pageNum
            if (pageNum == MENU_PAGE_COUNT && WRAP_PAGES) {
                newPageNum = 1;
            }
            if (pageNum < MENU_PAGE_COUNT) {
                newPageNum++;
            }
        }

        try {
            event.getReaction().removeReaction(event.getUser()).queue();
        } catch (PermissionException ignored) {
        }

        changePage(message, newPageNum);
    }

    @Override
    protected void enterSubmenu(Message oldMessage, int pageNum) {
        //Create SubmenuPaginator
        OmbiCallers caller = new OmbiCallers();
        EmbedManager embedManager = new EmbedManager();
        SearchSubmenu.Builder submenuBuilder = new SearchSubmenu.Builder()
                .setFinalAction(message -> {
                    try {
                        message.clearReactions().queue();
                    } catch (PermissionException ex) {
                        message.delete().queue();
                    }
                })
                .setEventWaiter(waiter);
        //Set Media type
        if (MEDIA_TYPE == 1) {

            //extract the media ID from the tvEmbed
            int mediaID = Integer.parseInt(Objects.requireNonNull(EMBED_ARRAYLIST.get(pageNum).getFields().get(0).getValue(), "Media ID is null! Impossible to continue!"));

            //get the media embed and the status
            TvInfo info = caller.ombiTvInfo(String.valueOf(mediaID));


            submenuBuilder.setTvInfo(info);
            submenuBuilder.setEmbedArray(embedManager.toArrayList(embedManager.createTvMoreInfoEmbed(info)));
            submenuBuilder.setTvType();
        } else if (MEDIA_TYPE == 2) {
            //extract the media ID from the tvEmbed
            int mediaID = Integer.parseInt(Objects.requireNonNull(EMBED_ARRAYLIST.get(pageNum).getFields().get(0).getValue(), "Media ID is null! Impossible to continue!"));

            //we need to generate the MovieInfo object in order to pass it to the paginator and get the required embedArray
            MovieInfo info = caller.ombiMovieInfo(String.valueOf(mediaID));

            submenuBuilder.setMovieType();
            submenuBuilder.setEmbedArray(embedManager.toArrayList(embedManager.createMovieMoreInfoEmbed(info)));
            submenuBuilder.setMovieInfo(info);
        }
        SearchSubmenu p = submenuBuilder
                .build();

        p.paginate(oldMessage, 1);

    }

    public static class Builder extends Menu.Builder<SearchPaginator.Builder, SearchPaginator> {

        private ArrayList<EmbedBuilder> embeds = new ArrayList<>();
        private ArrayList<Integer> submenuEmbeds = new ArrayList<Integer>();

        private boolean wrapPageEnds = false;
        private Consumer<Message> finalAction = m -> m.delete().queue();
        private String text = null;
        private int type = 1;
        private CommandEvent event;

        @Override
        public SearchPaginator build() {
            Checks.check(waiter != null, "Must set an EventWaiter");
            Checks.check(!embeds.isEmpty(), "Must include at least one item to paginate");

            return new SearchPaginator(waiter, users, roles, timeout, unit, embeds, wrapPageEnds, finalAction, type);

        }

        public SearchPaginator.Builder setEmbedArray(ArrayList<EmbedBuilder> embeds) {
            this.embeds = embeds;
            return this;
        }

        public SearchPaginator.Builder setSubmenuEmbedArray(ArrayList<Integer> submenuEmbeds) {
            this.submenuEmbeds = submenuEmbeds;
            return this;
        }

        public SearchPaginator.Builder setText(String text) {
            this.text = text;
            return this;
        }

        public SearchPaginator.Builder setFinalAction(Consumer<Message> finalAction) {
            this.finalAction = finalAction;
            return this;
        }

        public SearchPaginator.Builder setTvType() {
            this.type = 1;
            return this;
        }

        public SearchPaginator.Builder setMovieType() {
            this.type = 2;
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

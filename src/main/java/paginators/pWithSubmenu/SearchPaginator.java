package paginators.pWithSubmenu;

import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.jagrosh.jdautilities.menu.Menu;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.exceptions.PermissionException;
import net.dv8tion.jda.internal.utils.Checks;
import paginators.Paginator;

import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

//This is what the reaction array contains for this menu
//String[] REACTIONS = {"\u25C0",        //◀️
//                      "\uD83D\uDED1",  //🛑
//                      "\u2705",        //✅
//                      "\u25B6"}        //▶️️

public class SearchPaginator extends Paginator {
    public SearchPaginator(EventWaiter waiter, Set<User> users, Set<Role> roles, long timeout, TimeUnit timeUnit, ArrayList<EmbedBuilder> pages, boolean wrapPages, Consumer<Message> finalAction) {
        super(waiter, users, roles, timeout, timeUnit, new String[]{":arrow_backward:", ":stop_sign:", ":white_check_mark:", ":arrow_forward:"}, pages, wrapPages, finalAction);
    }

    @Override
    protected void handleMessageReactionAddAction(MessageReactionAddEvent event, Message message, int pageNum) {
        int newPageNum = pageNum;

        //DEBUG TIME!
        System.out.println("JDA says: " + event.getReaction().getReactionEmote().getName());


        if (REACTIONS[0].getUnicode().equals(event.getReaction().getReactionEmote().getName())) { // the first item in the array is the left arrow
            if (newPageNum == 1 && WRAP_PAGES) {
                newPageNum = MENU_PAGE_COUNT + 1;
            } else if (newPageNum > 1) {
                newPageNum--;
            }
        } else if (REACTIONS[1].getUnicode().equals(event.getReaction().getReactionEmote().getName())) { //the second item in the array is the stop sign
            FINAL_ACTION.accept(message);
            return;
        } else if (REACTIONS[2].getUnicode().equals(event.getReaction().getReactionEmote().getName())) { //the third item in the array is the check mark
            System.out.println(event.getUser() + " has used the select button in the search paginator!");

            //We need to go into a submenu at this point!
            //enterSubmenu(subMenuIds.get(pageNum - 1), message, null);

        } else if (REACTIONS[3].getUnicode().equals(event.getReaction().getReactionEmote().getName())) { //the last item is the right arrow
            if (newPageNum == MENU_PAGE_COUNT && WRAP_PAGES) {
                newPageNum = 0;
            } else if (newPageNum < MENU_PAGE_COUNT) {
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
    protected void enterSubmenu(MessageChannel channel) {
        System.out.println("not a thing yet!");
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

            return new SearchPaginator(waiter, users, roles, timeout, unit, embeds, wrapPageEnds, finalAction);

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

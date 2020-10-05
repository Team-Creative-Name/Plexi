package com.github.tcn.plexi.paginators.simplePaginators;

import com.github.tcn.plexi.paginators.Paginator;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.internal.utils.Checks;

import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class SelectorPaginator extends Paginator {
    final Consumer<EmbedBuilder> SELECTION_ACTION;

    public SelectorPaginator(EventWaiter waiter, Set<User> users, Set<Role> roles, long timeout, TimeUnit timeUnit, String[] reactions, ArrayList<EmbedBuilder> pages, boolean wrapPages, Consumer<Message> finalAction, Consumer<EmbedBuilder> selectionAction) {
        super(waiter, users, roles, timeout, timeUnit, reactions, pages, wrapPages, finalAction);
        this.SELECTION_ACTION = selectionAction;
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

            //perform the selection action - we will pass the embed object for the current page so the user of this
            //  class can do something with it if they need to
            SELECTION_ACTION.accept(EMBED_ARRAYLIST.get(pageNum));

        } else if (REACTIONS[3].getUnicode().equals(event.getReaction().getReactionEmote().getName())) { //the last item is the right arrow
            //Increment pageNum
            if (pageNum == MENU_PAGE_COUNT && WRAP_PAGES) {
                newPageNum = 1;
            }
            if (pageNum < MENU_PAGE_COUNT) {
                newPageNum++;
            }
        }
    }

    @Override
    protected void enterSubmenu(Message oldMessage, int pageNum) {
        //This class is not designed to use a submenu at this time
    }

    @Override
    protected boolean additionalReactionChecks(String emoteUnicode) {
        return false;
    }

    public static class Builder extends Paginator.Builder<SelectorPaginator.Builder, SelectorPaginator>{
        String[] reactions = new String[]{":arrow_backward:", ":stop_sign:", ":white_check_mark:", ":arrow_forward:"};
        private Consumer<EmbedBuilder> selectionAction = null;

        @Override
        public SelectorPaginator build() {
            runBasicChecks();
            //check to make sure that the selection action is not null;
            Checks.check(selectionAction != null, "A selection action must be set for a selectorPaginator!");
            return new SelectorPaginator(waiter, users, roles, timeout, unit, reactions, pages, wrapPages, finalAction, selectionAction);
        }

        public final Builder setSelectionAction(Consumer<EmbedBuilder> selectionAction){
            this.selectionAction = selectionAction;
            return this;
        }
    }
}

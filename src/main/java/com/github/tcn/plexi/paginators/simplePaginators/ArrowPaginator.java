package com.github.tcn.plexi.paginators.simplePaginators;

import com.github.tcn.plexi.paginators.Paginator;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.exceptions.PermissionException;

import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class ArrowPaginator extends Paginator {
    public ArrowPaginator(EventWaiter waiter, Set<User> users, Set<Role> roles, long timeout, TimeUnit timeUnit, String[] reactions, ArrayList<EmbedBuilder> pages, boolean wrapPages, Consumer<Message> finalAction) {
        super(waiter, users, roles, timeout, timeUnit, reactions, pages, wrapPages, finalAction);
    }

    @Override
    protected void handleMessageReactionAddAction(MessageReactionAddEvent event, Message message, int pageNum) {
        int newPageNum = pageNum;

        if(REACTIONS[0].getUnicode().equals(event.getReaction().getReactionEmote().getName())){ //first item in array is the left arrow
            //decrement pageNum
            if (pageNum == 1 && WRAP_PAGES) {
                newPageNum = MENU_PAGE_COUNT;
            }
            if (pageNum > 1) {
                newPageNum--;
            }
        }else if(REACTIONS[1].getUnicode().equals(event.getReaction().getReactionEmote().getName())){ //second item in the array is the stop sign
            //perform the final action
            FINAL_ACTION.accept(message);

            //return bc we dont want to do anything else
            return;

        }else if(REACTIONS[2].getUnicode().equals(event.getReaction().getReactionEmote().getName())){ //last item in the array is the right arrow
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

    }

    @Override
    protected boolean additionalReactionChecks(String emoteUnicode) {
        return true;
    }

    public static class Builder extends Paginator.Builder<ArrowPaginator.Builder, ArrowPaginator>{

        //This is meant to be a standard paginator to call. Hard-code its reactions
        String[] reactions = new String[]{":arrow_backward:",":stop_sign:",":arrow_forward:"};

        @Override
        public ArrowPaginator build() {
            //run basic builder checks
            runBasicChecks();

            //return a new ArrowOnlyPaginator object
            return new ArrowPaginator(waiter, users, roles, timeout, unit, reactions, pages, wrapPages, finalAction);
        }

    }

}

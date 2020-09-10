package com.github.tcn.plexi.paginators.simplePaginators;

import com.github.tcn.plexi.paginators.Paginator;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;

import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class SelectorPaginator extends Paginator {
    public SelectorPaginator(EventWaiter waiter, Set<User> users, Set<Role> roles, long timeout, TimeUnit timeUnit, String[] reactions, ArrayList<EmbedBuilder> pages, boolean wrapPages, Consumer<Message> finalAction) {
        super(waiter, users, roles, timeout, timeUnit, reactions, pages, wrapPages, finalAction);
    }

    @Override
    protected void handleMessageReactionAddAction(MessageReactionAddEvent event, Message message, int pageNum) {

    }

    @Override
    protected void enterSubmenu(Message oldMessage, int pageNum) {

    }

    @Override
    protected boolean additionalReactionChecks(String emoteUnicode) {
        return false;
    }
}

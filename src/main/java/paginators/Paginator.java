package paginators;

import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.jagrosh.jdautilities.menu.Menu;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.requests.RestAction;

import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public abstract class Paginator extends Menu {


    //All paginators MUST have the following emoji buttons
    public static final String LEFT = "\u25C0"; //◀️
    public static final String STOP = "\uD83D\uDED1"; //🛑
    public static final String SELECT = "\u2705"; //👍
    public static final String RIGHT = "\u25B6"; //▶️️

    //An arraylist of embeds to display to the user
    private final ArrayList<EmbedBuilder> embedArrayList;

    //internal variables that the object will automatically fill
    private final int menuPageCount;

    //required info about the media
    private final boolean wrapPageEnds;
    private final Consumer<Message> finalAction;


    //Constructor
    Paginator(EventWaiter waiter, Set<User> users, Set<Role> roles, long timeout, TimeUnit timeUnit,
              //this class's required bits of data
              ArrayList<EmbedBuilder> pages, boolean wrapPageEnds, Consumer<Message> finalAction) {

        //provide super class with required information
        super(waiter, users, roles, timeout, timeUnit);

        //set local data fields
        embedArrayList = pages;
        this.wrapPageEnds = wrapPageEnds;
        this.finalAction = finalAction;

        //calculate additional fields based upon previously set fields
        menuPageCount = embedArrayList.size();

    }

    //from super method, we want to start on the first page in any case
    @Override
    public void display(MessageChannel channel) {
        paginate(channel, 1);
    }

    //from super method, we want to start on the first page in any case
    @Override
    public void display(Message message) {
        paginate(message, 1);
    }

    public void paginate(MessageChannel channel, int pageNum) {
        if (pageNum < 1) {
            pageNum = 1;
        } else if (pageNum > menuPageCount) {
            pageNum = menuPageCount;
        }
        Message msg = embedToMessage(pageNum);
        initialize(channel.sendMessage(msg), pageNum);
    }

    public void paginate(Message message, int pageNum) {
        if (pageNum < 1)
            pageNum = 1;
        else if (pageNum > menuPageCount)
            pageNum = menuPageCount;

        Message msg = embedToMessage(pageNum);
        initialize(message.editMessage(msg), pageNum);
    }

    private void pagination(Message m, int n) {
        waiter.waitForEvent(MessageReactionAddEvent.class,
                event -> checkReaction(event, m.getIdLong()), // Check Reaction
                event -> handleMessageReactionAddAction(event, m, n), // Handle Reaction
                timeout, unit, () -> finalAction.accept(m));
    }

    private Message embedToMessage(int pagenum) {
        MessageBuilder messageBuilder = new MessageBuilder();
        //create a sendable message containing the embed we generated earlier from the embedArrayList
        messageBuilder.setEmbed(embedArrayList.get(pagenum - 1).build());
        return messageBuilder.build();
    }

    //handles emote add events
    protected abstract void handleMessageReactionAddAction(MessageReactionAddEvent event, Message message, int pageNum);

    //allows the menu to go into a submenu
    protected void enterSubmenu(MessageChannel channel) {
        //TODO: Check to see if this is an acceptable thing to even do... I have literally no idea.
        //this method is intentionally left blank. In order for it do do something, it MUST be overridden.
    }

    //The following methods MUST be overridden if we want to use any emotes that are NOT part of the default set.

    private boolean checkReaction(MessageReactionAddEvent event, long messageId) {
        if (event.getMessageIdLong() != messageId)
            return false;
        switch (event.getReactionEmote().getName()) {
            // LEFT, STOP, RIGHT, Select all fall-through to
            // return if the user is valid or not. If none trip, this defaults
            // and returns false.
            case LEFT:
            case STOP:
            case RIGHT:
            case SELECT:
                return isValidUser(event.getUser(), event.isFromGuild() ? event.getGuild() : null);

            default:
                return false;
        }
    }

    private void initialize(RestAction<Message> action, int pageNum) {
        action.queue(m -> {
            if (pageNum > 1) {

                m.addReaction(LEFT).queue();
                m.addReaction(STOP).queue();
                m.addReaction(SELECT).queue();
                m.addReaction(RIGHT).queue(v -> pagination(m, pageNum), t -> pagination(m, pageNum));
            } else {
                finalAction.accept(m);
            }
        });
    }

}

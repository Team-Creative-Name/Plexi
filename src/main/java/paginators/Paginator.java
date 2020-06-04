package paginators;

import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.menu.Menu;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;

import java.util.ArrayList;
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
    private final CommandEvent event;

    //required info about the media
    private final boolean wrapPageEnds;
    private final Consumer<Message> finalAction;


    //Constructor
    Paginator(ArrayList<EmbedBuilder> pages) {
        super(waiter, users, roles, timeout, units);


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
        } else if (pageNum > pages) {
            pageNum = pages;
        }

        //if there is only 1 item, go directly to the detailed view

        Message msg = renderSearchPage(pageNum);
        if (pages == 1) {
            enterInfoSubMenu(subMenuIds.get(0), null, channel);
            return;
        }
        initialize(channel.sendMessage(msg), pageNum);
    }

    public void paginate(Message message, int pageNum) {
        if (pageNum < 1)
            pageNum = 1;
        else if (pageNum > pages)
            pageNum = pages;

        Message msg = renderSearchPage(pageNum);
        initialize(message.editMessage(msg), pageNum);
    }

    //handles emote add events
    protected abstract void handleMessageReactionAddAction(MessageReactionAddEvent event, Message message, int pageNum);

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

}

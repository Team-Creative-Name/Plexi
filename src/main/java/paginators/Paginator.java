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


    //These are leftover Strings and need to be removed in the future
    //TODO: Delete these variables
    protected final String LEFT = "\u25C0"; //◀️
    protected final String STOP = "\uD83D\uDED1"; //🛑
    protected final String SELECT = "\u2705"; //✅ - Select Menu and perform action
    protected final String RIGHT = "\u25B6"; //▶️️

    //An arraylist of the reactions displayed under the embed.
    protected final String[] REACTIONS;

    //An arraylist of embeds to display to the user
    protected final ArrayList<EmbedBuilder> EMBED_ARRAYLIST;

    //internal variables that the object will automatically fill
    protected final int MENU_PAGE_COUNT;

    //required info about the media
    protected final boolean WRAP_PAGES;
    protected final Consumer<Message> FINAL_ACTION;


    //Constructor
    public Paginator(EventWaiter waiter, Set<User> users, Set<Role> roles, long timeout, TimeUnit timeUnit,
                     //this class's required bits of data
                     String[] reactions, ArrayList<EmbedBuilder> pages, boolean wrapPages, Consumer<Message> finalAction) {

        //provide super class with required information
        super(waiter, users, roles, timeout, timeUnit);
        this.REACTIONS = reactions;

        //set local data fields
        EMBED_ARRAYLIST = pages;
        WRAP_PAGES = wrapPages;
        FINAL_ACTION = finalAction;

        //calculate additional fields based upon previously set fields
        MENU_PAGE_COUNT = EMBED_ARRAYLIST.size();

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
        } else if (pageNum > MENU_PAGE_COUNT) {
            pageNum = MENU_PAGE_COUNT;
        }
        Message msg = embedToMessage(pageNum);
        initialize(channel.sendMessage(msg), pageNum);
    }

    public void paginate(Message message, int pageNum) {
        if (pageNum < 1)
            pageNum = 1;
        else if (pageNum > MENU_PAGE_COUNT)
            pageNum = MENU_PAGE_COUNT;

        Message msg = embedToMessage(pageNum);
        initialize(message.editMessage(msg), pageNum);
    }

    private void pagination(Message m, int n) {
        waiter.waitForEvent(MessageReactionAddEvent.class,
                event -> checkReaction(event, m.getIdLong()), // Check Reaction
                event -> handleMessageReactionAddAction(event, m, n), // Handle Reaction
                timeout, unit, () -> FINAL_ACTION.accept(m));
    }

    private Message embedToMessage(int pageNum) {
        MessageBuilder messageBuilder = new MessageBuilder();
        //create a sendable message containing the embed we generated earlier from the embedArrayList
        messageBuilder.setEmbed(EMBED_ARRAYLIST.get(pageNum - 1).build());
        return messageBuilder.build();
    }

    //handles emote add events
    protected abstract void handleMessageReactionAddAction(MessageReactionAddEvent event, Message message, int pageNum);

    //allows the menu to go into a submenu - child class defines what this method does (if anything)!
    protected abstract void enterSubmenu(MessageChannel channel);

    private boolean checkReaction(MessageReactionAddEvent event, long messageId) {
        if (event.getMessageIdLong() != messageId){
            return false;
        }

        //run through reaction array and check to see if a reaction is valid
        for (String reactions:REACTIONS) {
            if(reactions.equals(event.getReactionEmote().getName())){
                return isValidUser(event.getUser(), event.isFromGuild() ? event.getGuild() : null);
            }
        }

        //if that for loop doesnt return true, return false
        return false;
    }

    //adds reactions to the embed and sets final action
    private void initialize(RestAction<Message> action, int pageNum) {
        action.queue(m -> {
            if (pageNum > 1) {
                for(int i = 0; i < REACTIONS.length - 1; i++){
                    m.addReaction(REACTIONS[i]);
                }
                m.addReaction(REACTIONS[REACTIONS.length - 1]).queue(v -> pagination(m, pageNum), t -> pagination(m, pageNum));
            } else {
                FINAL_ACTION.accept(m);
            }
        });
    }

    protected void changePage(Message originalMessage, int pageNum){
        originalMessage.editMessage(embedToMessage(pageNum)).queue(m -> pagination(m, pageNum));
    }

}

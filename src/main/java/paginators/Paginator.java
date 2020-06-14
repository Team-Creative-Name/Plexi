package paginators;

import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.jagrosh.jdautilities.menu.Menu;
import com.vdurmont.emoji.Emoji;
import com.vdurmont.emoji.EmojiManager;
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

//This class uses emoji aliases via com.vdurmont.emoji. The full list of aliases as of this version can be found here: https://gist.github.com/collectioncard/913937204a3b3d63438a9fbcc34f5bba

public abstract class Paginator extends Menu {


    //These are leftover Strings and need to be removed in the future
    //TODO: Delete these variables
    protected final String LEFT = "\u25C0"; //◀️
    protected final String STOP = "\uD83D\uDED1"; //🛑
    protected final String SELECT = "\u2705"; //✅ - Select Menu and perform action
    protected final String RIGHT = "\u25B6"; //▶️️

    //An array of emojis to be displayed under the embed
    protected final Emoji[] REACTIONS;

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
        this.REACTIONS = toEmojiArray(reactions);

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

    protected void pagination(Message m, int n) {
        System.out.println("Here1!");
        waiter.waitForEvent(MessageReactionAddEvent.class,
                event -> checkReaction(event, m.getIdLong()), // Check Reaction
                event -> handleMessageReactionAddAction(event, m, n), // Handle Reaction
                timeout, unit, () -> FINAL_ACTION.accept(m));

        System.out.println("Exited Pagination waiter");
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
    protected abstract void enterSubmenu(Message oldMessage, int pageNum);


    private boolean checkReaction(MessageReactionAddEvent event, long messageId) {
        if (event.getMessageIdLong() != messageId) {
            return false;
        }

        //run through reaction array and check to see if a reaction is valid
        for (Emoji reactions : REACTIONS) {
            if (reactions.getUnicode().equals(event.getReactionEmote().getName())) {
                return isValidUser(event.getUser(), event.isFromGuild() ? event.getGuild() : null);
            }
        }

        //if that for loop doesnt return true, return false
        return false;
    }

    //adds reactions to the embed and sets final action
    protected void initialize(RestAction<Message> action, int pageNum) {
        action.queue(m -> {
            if (MENU_PAGE_COUNT > 1) {
                for (int i = 0; i < REACTIONS.length - 1; i++) {
                    m.addReaction(REACTIONS[i].getUnicode()).queue();
                    System.out.println("Adding emote: " + REACTIONS[i].getUnicode());
                }
                m.addReaction(REACTIONS[REACTIONS.length - 1].getUnicode()).queue(v -> pagination(m, pageNum), t -> pagination(m, pageNum));
            } else {
                FINAL_ACTION.accept(m);
            }
        });
    }

    protected void changePage(Message originalMessage, int newPageNum) {
        originalMessage.editMessage(embedToMessage(newPageNum)).queue(m -> pagination(m, newPageNum));
    }

    //I got frustrated with unicode, so I made it so we only have to provide emoji aliases to this class. This method changes them into Emoji
    private Emoji[] toEmojiArray(String[] stringEmojiArray) {
        Emoji[] toReturn = new Emoji[stringEmojiArray.length];
        for (int i = 0; i < stringEmojiArray.length; i++) {
            toReturn[i] = EmojiManager.getForAlias(stringEmojiArray[i]);
        }
        return toReturn;
    }


}

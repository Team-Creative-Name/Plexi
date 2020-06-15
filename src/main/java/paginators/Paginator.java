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
import net.dv8tion.jda.internal.utils.Checks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

//This class uses emoji aliases via com.vdurmont.emoji. The full list of aliases as of this version can be found here: https://gist.github.com/collectioncard/913937204a3b3d63438a9fbcc34f5bba

public abstract class Paginator extends Menu {

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
            //clear previous reactions
            m.clearReactions().queue();
            if (MENU_PAGE_COUNT > 1) {
                for (int i = 0; i < REACTIONS.length - 1; i++) {
                    m.addReaction(REACTIONS[i].getUnicode()).queue();
                }
                m.addReaction(REACTIONS[REACTIONS.length - 1].getUnicode()).queue(v -> pagination(m, pageNum), t -> pagination(m, pageNum));
            } else if (MENU_PAGE_COUNT == 1) {
                //close this paginator
                FINAL_ACTION.accept(m);
                //Enter the submenu - actions depend on code in overridden method
                enterSubmenu(m, pageNum - 1);
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

    //Builder patter that *should* make it pretty easy to create this object properly
    @SuppressWarnings("unchecked")
    public abstract static class Builder<T extends Builder<T, V>, V extends Paginator> {
        //global vars
        protected EventWaiter waiter;
        protected Set<User> users = new HashSet<>();
        protected Set<Role> roles = new HashSet<>();
        protected long timeout = 1;
        protected TimeUnit unit = TimeUnit.MINUTES;
        protected ArrayList<EmbedBuilder> pages = new ArrayList<>();
        protected boolean wrapPages = true;
        protected Consumer<Message> finalAction = m -> m.delete().queue();

        //This generic abstract method needs to build the object and return it
        public abstract V build();

        //A method that does basic checking
        public final void runBasicChecks() {
            //check for an eventwaiter
            Checks.check(waiter != null, "An eventwaiter must be set for this object!");
            //check to make sure there are items to paginate
            Checks.check(!pages.isEmpty(), "You need at least one item for a menu!");

        }

        //setters
        public final T setEventWaiter(EventWaiter waiter) {
            this.waiter = waiter;
            return (T) this;
        }

        public final T addUsers(User... users) {
            this.users.addAll(Arrays.asList(users));
            return (T) this;
        }

        public final T setUsers(User... users) {
            this.users.clear();
            this.users.addAll(Arrays.asList(users));
            return (T) this;
        }

        public final T addRoles(Role... roles) {
            this.roles.addAll(Arrays.asList(roles));
            return (T) this;
        }

        public final T setRoles(Role... roles) {
            this.roles.clear();
            this.roles.addAll(Arrays.asList(roles));
            return (T) this;
        }

        public final T setTimeout(long timeout, TimeUnit unit) {
            this.timeout = timeout;
            this.unit = unit;
            return (T) this;
        }

        public final T setPages(ArrayList<EmbedBuilder> pages) {
            this.pages = pages;
            return (T) this;
        }

        public final T setWrapPages(boolean wrapPages) {
            this.wrapPages = wrapPages;
            return (T) this;
        }

        public final T setFinalAction(Consumer<Message> finalAction) {
            this.finalAction = finalAction;
            return (T) this;
        }

    }

}

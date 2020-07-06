package com.github.tcn.plexi.paginators;

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


/**
 * A frame for creating page-based menus from an emoji array and an EmbedBuilder array.
 *
 * <p><br>This menu is strictly built to have interactions via emote reactions. As of right now, it does not have support
 * for any form of interaction aside from that.</p>
 *
 * <p>This class extends {@link com.jagrosh.jdautilities.menu.Menu jdaUtil's Menu class}</p>
 *
 * @implNote The only abstract method that *MUST* be implemented is {@link Paginator#additionalReactionChecks(String emoteUnicode)}
 * if it does not return true for an emote, no action will be performed. Because of this, it MUST return true if not used
 */
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

    /**
     * Calls {@link Paginator#paginate(MessageChannel channel, int)} which creates a menu and displays the first page in
     * a {@link net.dv8tion.jda.api.entities.MessageChannel MessageChannel}
     *
     * @param channel The channel that the menu is to be displayed in
     */
    //from super method, we want to start on the first page in any case
    @Override
    public void display(MessageChannel channel) {
        paginate(channel, 1);
    }


    /**
     * Calls {@link Paginator#paginate(Message message, int)} which creates a menu and replaces a passed
     * a {@link net.dv8tion.jda.api.entities.Message Message's} contents with the first page of a menu
     *
     * @param message The message to replace with a menu
     */
    //from super method, we want to start on the first page in any case
    @Override
    public void display(Message message) {
        paginate(message, 1);
    }


    /**
     * Starts the pagination process by getting the requested page of the embed array and sends it to the designated
     * channel. Also calls method that adds reactions
     *
     * @param channel The channel that the new Paginator should be sent
     * @param pageNum The page number that the Paginator should present
     */
    public void paginate(MessageChannel channel, int pageNum) {
        if (pageNum < 1) {
            pageNum = 1;
        } else if (pageNum > MENU_PAGE_COUNT) {
            pageNum = MENU_PAGE_COUNT;
        }
        Message msg = embedToMessage(pageNum);
        initialize(channel.sendMessage(msg), pageNum);
    }

    /**
     * Starts the pagination process by getting the requested page of the embed array and replaces the designated
     * {@link net.dv8tion.jda.api.entities.Message Message's} content with the new page.
     *
     * @param message The message to replace with the Paginator menu
     * @param pageNum The page number that the Paginator should display
     */
    public void paginate(Message message, int pageNum) {
        if (pageNum < 1)
            pageNum = 1;
        else if (pageNum > MENU_PAGE_COUNT)
            pageNum = MENU_PAGE_COUNT;

        Message msg = embedToMessage(pageNum);
        initialize(message.editMessage(msg), pageNum);
    }

    /**
     * Waits for an event to happen and then reacts based upon that. First it calls {@link Paginator#checkReaction(MessageReactionAddEvent, long)}
     * which checks for reaction validity. Second it calls {@link Paginator#handleMessageReactionAddAction(MessageReactionAddEvent, Message, int)}
     * which handles the reaction. This method also starts a "timeout" counter that executes {@link Paginator#FINAL_ACTION FINAL_ACTION} when it expires.
     * <p><br></p>
     *
     * @param message The {@link net.dv8tion.jda.api.entities.Message } that contains the page we are currently on.
     * @param pageNum The index of the {@link Paginator#EMBED_ARRAYLIST} we are currently on.
     */
    protected void pagination(Message message, int pageNum) {
        waiter.waitForEvent(MessageReactionAddEvent.class,
                event -> checkReaction(event, message.getIdLong()), // Check Reaction
                event -> handleMessageReactionAddAction(event, message, pageNum), // Handle Reaction
                timeout, unit, () -> FINAL_ACTION.accept(message));
    }


    /**
     * Takes a page number and turns the embed array into a message
     *
     * @param pageNum The Page number (index number) of {@link Paginator#EMBED_ARRAYLIST} to turn into a message
     * @return {@link Paginator#EMBED_ARRAYLIST}[pageNum] as a  {@link net.dv8tion.jda.api.entities.Message}
     */
    private Message embedToMessage(int pageNum) {
        MessageBuilder messageBuilder = new MessageBuilder();
        //create a sendable message containing the embed we generated earlier from the embedArrayList
        messageBuilder.setEmbed(EMBED_ARRAYLIST.get(pageNum - 1).build());
        return messageBuilder.build();
    }

    /**
     * An abstract class that is passed a MessageReactionAddEvent, Message, and integer and performs actions based upon which
     * reaction was added. If child class has empty implementation, every valid reaction should cause the {@link Paginator#FINAL_ACTION}
     * to execute.
     *
     * @param event   the {@link net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent} triggered by adding a reaction
     * @param message The {@link net.dv8tion.jda.api.entities.Message} that the reaction was added to
     * @param pageNum the page number that the Paginator is currently on
     */
    //handles emote add events
    protected abstract void handleMessageReactionAddAction(MessageReactionAddEvent event, Message message, int pageNum);

    /**
     * An abstract method that allows a child class to enter a submenu if desired. If it has an empty implementation in the child
     * class, the Paginator will simply be unable to enter a submenu.
     *
     * @param oldMessage The {@link net.dv8tion.jda.api.entities.Message} that contains the current Paginator
     * @param pageNum    The Page number (index number) of {@link Paginator#EMBED_ARRAYLIST} that the Paginator is currently showing
     * @implNote If there is no submenu, leave this method blank.
     */
    //allows the menu to go into a submenu - child class defines what this method does (if anything!)
    protected abstract void enterSubmenu(Message oldMessage, int pageNum);


    /**
     * performs checks on the reaction added via event.
     * Checks done:
     * <p><ul>
     * <li>checks message ID against the Paginator's Message ID
     * <li>checks to see if added reaction is one of the ones recognized by this Paginator
     * </ul></p>
     * if all of those checks pass, we call {@link Paginator#additionalReactionChecks(String emoteUnicode)} to see if the
     * child class needed any additional reaction verification
     *
     * @param event     the {@link net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent} triggered by adding a reaction
     * @param messageId The ID from {@link Message#getId()}}
     * @return {@code true} if the {@link net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent} is valid, {@code false} otherwise.
     */
    private boolean checkReaction(MessageReactionAddEvent event, long messageId) {
        if (event.getMessageIdLong() != messageId) {
            return false;
        }

        //run through reaction array and check to see if a reaction is valid
        for (Emoji reactions : REACTIONS) {
            //check to make sure the name is correct
            if (reactions.getUnicode().equals(event.getReactionEmote().getName())) {
                //check to see if the child class defined any additional checks
                if (additionalReactionChecks(event.getReactionEmote().getName())) {//should return true if no additional checks
                    //returns true if a valid user created the reaction
                    return isValidUser(event.getUser(), event.isFromGuild() ? event.getGuild() : null);
                }
            }
        }

        //if that for loop doesnt return true, return false
        return false;
    }


    /**
     * Allows a child class to run additional checks on an added emote before an assigned action is executed. If a reaction
     * is found to be invalid, the method should return false. For anything else, it should return true.
     *
     * @param emoteUnicode The unicode form of an emote that is going to be checked. Example: "\uD83D\uDC4D"
     * @return {@code false} if the {@link net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent} is invalid, {@code true} otherwise.
     */
    protected abstract boolean additionalReactionChecks(String emoteUnicode);


    /**
     * Adds reactions to Paginator.
     * <p>If there is more than one page denoted by {@link Paginator#MENU_PAGE_COUNT}, then we add all reactions from {@link Paginator#REACTIONS}.
     * <br>If there is only one page in that list, we call {@link Paginator#FINAL_ACTION} and attempt to enter the submenu if one exists.</p>
     *
     * @param action  An action on a message. Usually a {@link net.dv8tion.jda.api.entities.Message#editMessage(Message)} action
     * @param pageNum The Page number (index number) of {@link Paginator#EMBED_ARRAYLIST} that the Paginator is currently showing
     */
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

    /**
     * Updates Paginator to show new page
     * @param originalMessage
     *              The {@link net.dv8tion.jda.api.entities.Message} that contains the Paginator that needs edited
     * @param newPageNum
     *              The Page number (index number) of {@link Paginator#EMBED_ARRAYLIST} that the Paginator needs to be edited to show
     */
    protected void changePage(Message originalMessage, int newPageNum) {
        originalMessage.editMessage(embedToMessage(newPageNum)).queue(m -> pagination(m, newPageNum));
    }


    /**
     * Converts emoji aliases to a {@link com.vdurmont.emoji.Emoji} array containing all emoji aliases passed to it
     *
     * @param stringEmojiArray A string array of emoji aliases as defined by <a href="https://gist.github.com/collectioncard/913937204a3b3d63438a9fbcc34f5bba">this github gist</a>
     * @return {@link com.vdurmont.emoji.Emoji} array containing all emoji passed to it
     */
    //I got frustrated with unicode, so I made it so we only have to provide emoji aliases to this class. This method changes them into Emoji
    private Emoji[] toEmojiArray(String[] stringEmojiArray) {
        Emoji[] toReturn = new Emoji[stringEmojiArray.length];
        for (int i = 0; i < stringEmojiArray.length; i++) {
            toReturn[i] = EmojiManager.getForAlias(stringEmojiArray[i]);
        }
        return toReturn;
    }


    /**
     * An extendable frame for a chain-method builder that constructs a specified type of
     * {@link com.jagrosh.jdautilities.menu.Menu Menu}.
     *
     * Conventionally, implementations of Menu should have a static nested class called
     * {@code Builder}, which extends this superclass:
     * <pre><code>
     * public class MyMenu extends Menu
     * {
     *     // Menu Code
     *
     *    {@literal public static class Builder extends Menu.Builder<Builder, MyMenu>}
     *     {
     *         // Builder Code
     *     }
     * }
     * </code></pre>
     *
     * @author John Grosh with changes made by Thomas Wessel
     * @implNote This is pretty much a clone of {@link com.jagrosh.jdautilities.menu.Menu.Builder} and because of that, I felt it
     * necessary to not only use his javadoc, but keep him as co-author of this particular builder class
     */
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

        /**
         * Builds the Paginator object that corresponds to this builder class
         *
         * @return a fully constructed {@link com.github.tcn.plexi.paginators.Paginator} object
         */
        //This generic abstract method needs to build the object and return it
        public abstract V build();


        /**
         * runs checks on some of the data entered into the builder to ensure that it can be built
         * checks run:
         * <ul>
         * <li>checks that an {@link com.jagrosh.jdautilities.commons.waiter.EventWaiter EventWaiter} was set
         * <li>checks to see if there are {@link Builder#pages pages} instead of an empty arraylist
         * </ul>
         */
        //A method that does basic checking
        public final void runBasicChecks() {
            //check for an eventwaiter
            Checks.check(waiter != null, "An event waiter must be set for this object!");
            //check to make sure there are items to paginate
            Checks.check(!pages.isEmpty(), "You need at least one item for a menu!");

        }

        /**
         * sets the EventWaiter defined in the waiter parameter
         *
         * @param waiter The {@link com.jagrosh.jdautilities.commons.waiter.EventWaiter EventWaiter}
         * @return this builder
         */
        //setters
        public final T setEventWaiter(EventWaiter waiter) {
            this.waiter = waiter;
            return (T) this;
        }

        /**
         * adds users to the list of users that are allowed to use this Paginator
         *
         * @param users The {@link net.dv8tion.jda.api.entities.User Users} allowed to use this paginator
         * @return this builder
         */
        public final T addUsers(User... users) {
            this.users.addAll(Arrays.asList(users));
            return (T) this;
        }

        /**
         * erases and sets the list of users that are allowed to use this Paginator
         *
         * @param users The {@link net.dv8tion.jda.api.entities.User Users} allowed to use this paginator
         * @return this builder
         */
        public final T setUsers(User... users) {
            this.users.clear();
            this.users.addAll(Arrays.asList(users));
            return (T) this;
        }


        /**
         * adds to the list of roles that are allowed to use this Paginator
         *
         * @param roles The {@link net.dv8tion.jda.api.entities.Role Roles} allowed to use the Paginator
         * @return this builder
         */
        public final T addRoles(Role... roles) {
            this.roles.addAll(Arrays.asList(roles));
            return (T) this;
        }

        /**
         * erases and sets the list of roles that are allowed to use this Paginator
         *
         * @param roles The {@link net.dv8tion.jda.api.entities.Role Roles} allowed to use the Paginator
         * @return this builder
         */
        public final T setRoles(Role... roles) {
            this.roles.clear();
            this.roles.addAll(Arrays.asList(roles));
            return (T) this;
        }

        /**
         * Sets the amount of time that the Paginator should wait before performing {@link com.github.tcn.plexi.paginators.Paginator#FINAL_ACTION the final action}
         *
         * @param timeout The amount of time
         * @param unit    The {@link java.util.concurrent.TimeUnit unit} of time the timeout parameter is in
         * @return this builder
         * @implNote if this is not set, the builder defaults to 1 minute
         */
        public final T setTimeout(long timeout, TimeUnit unit) {
            this.timeout = timeout;
            this.unit = unit;
            return (T) this;
        }

        /**
         * Sets the pages for the Paginator as an arraylist of {@link net.dv8tion.jda.api.EmbedBuilder EmbedBuilder} objects
         *
         * @param pages All of the pages that make up the paginator
         * @return this builder
         */
        public final T setPages(ArrayList<EmbedBuilder> pages) {
            this.pages = pages;
            return (T) this;
        }

        /**
         * Sets whether or not the Paginator should wrap pages - defaults to {@code true}
         *
         * @param wrapPages A boolean that states if pages should wrap or not
         * @return This builder
         */
        public final T setWrapPages(boolean wrapPages) {
            this.wrapPages = wrapPages;
            return (T) this;
        }

        /**
         * Sets what the final action should be for the Paginator
         *
         * @param finalAction The final action in the form of a {@link java.util.function.Consumer Consumer}
         * @return This builder
         */
        public final T setFinalAction(Consumer<Message> finalAction) {
            this.finalAction = finalAction;
            return (T) this;
        }
    }

}

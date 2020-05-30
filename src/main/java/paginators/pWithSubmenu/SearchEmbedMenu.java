package paginators.pWithSubmenu;

import apis.ombi.OmbiCallers;
import apis.ombi.templateClasses.movies.moreInfo.MovieInfo;
import apis.ombi.templateClasses.tv.moreInfo.TvInfo;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.jagrosh.jdautilities.menu.Menu;
import discordBot.EmbedManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.exceptions.PermissionException;
import net.dv8tion.jda.api.requests.RestAction;
import net.dv8tion.jda.internal.utils.Checks;
import paginators.submenus.SearchSubmenu;

import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class SearchEmbedMenu extends Menu {


    //Strings to represent possible emotes
    //:arrow_backward:
    public static final String LEFT = "\u25C0";
    //:octagonal_sign:
    public static final String STOP = "\uD83D\uDED1";
    //:White_check_mark:
    public static final String SELECT = "\u2705";
    //:arrow_forward:
    public static final String RIGHT = "\u25B6";
    //Big list of global variables
    private final ArrayList<EmbedBuilder> embeds;
    private final ArrayList<Integer> subMenuIds;
    private final int pages;
    private final boolean wrapPageEnds;
    private final Consumer<Message> finalAction;
    private final String text;
    private final int type;
    private final CommandEvent event;


    SearchEmbedMenu(EventWaiter waiter, Set<User> users, Set<Role> roles, long timeout, TimeUnit unit, ArrayList<EmbedBuilder> embeds, ArrayList<Integer> submenuEmbeds, boolean wrapPageEnds, Consumer<Message> finalAction, String text, int type, CommandEvent event) {
        super(waiter, users, roles, timeout, unit);
        this.embeds = embeds;
        this.pages = (int) Math.ceil((double) embeds.size() / 1);
        this.wrapPageEnds = wrapPageEnds;
        this.finalAction = finalAction;
        this.text = text;
        this.subMenuIds = submenuEmbeds;
        this.event = event;
        if (type > 2 || type < 1) {
            System.out.println("The type is: " + type);
            throw new IllegalArgumentException("Must specify embed type!");
        } else {
            this.type = type;
        }

    }


    //The two following methods are just constructors for the menu class.
    @Override
    public void display(MessageChannel channel) {
        paginate(channel, 1);
    }

    //Look at above comment
    @Override
    public void display(Message message) {
        paginate(message, 1);
    }

    /**
     * This method is what creates the actual paginator. It gets passed a channel and a starting page#
     * The method then goes on to call renderPage in order to get the message to send and then calls initialize
     * which handles queueing the message for sending along with the emote handling..
     */
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

    private void handleMessageReactionAddAction(MessageReactionAddEvent event, Message message, int pageNum) {
        int newPageNum = pageNum;
        switch (event.getReaction().getReactionEmote().getName()) {
            case LEFT:
                if (newPageNum == 1 && wrapPageEnds)
                    newPageNum = pages + 1;
                if (newPageNum > 1)
                    newPageNum--;
                break;
            case RIGHT:
                if (newPageNum == pages && wrapPageEnds)
                    newPageNum = 0;
                if (newPageNum < pages)
                    newPageNum++;
                break;
            case SELECT:
                System.out.println("The button was selected!");

                //We need to go into a submenu at this point!
                enterInfoSubMenu(subMenuIds.get(pageNum - 1), message, null);
                return;

            case STOP:
                finalAction.accept(message);
                return;
        }

        try {
            event.getReaction().removeReaction(event.getUser()).queue();
        } catch (PermissionException ignored) {
        }

        int n = newPageNum;
        message.editMessage(renderSearchPage(newPageNum)).queue(m -> pagination(m, n));

    }

    private void pagination(Message m, int n) {
        waiter.waitForEvent(MessageReactionAddEvent.class,
                event -> checkReaction(event, m.getIdLong()), // Check Reaction
                event -> handleMessageReactionAddAction(event, m, n), // Handle Reaction
                timeout, unit, () -> finalAction.accept(m));
    }

    //This is the final method called by the Paginate method. It sends the original message, adds reactions
    private void initialize(RestAction<Message> action, int pageNum) {
        action.queue(m -> {
            if (pages > 1) {

                m.addReaction(LEFT).queue();
                m.addReaction(STOP).queue();
                m.addReaction(SELECT).queue();
                m.addReaction(RIGHT).queue(v -> pagination(m, pageNum), t -> pagination(m, pageNum));
            } else {
                finalAction.accept(m);
            }
        });
    }


    private boolean checkReaction(MessageReactionAddEvent event, long messageId) {
        if (event.getMessageIdLong() != messageId)
            return false;
        switch (event.getReactionEmote().getName()) {
            // LEFT, STOP, RIGHT, BIG_LEFT, BIG_RIGHT all fall-through to
            // return if the User is valid or not. If none trip, this defaults
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

    //This method is called by the paginate method and returns a message type object that is ready to send
    private Message renderSearchPage(int tpageNum) {
        MessageBuilder mbuilder = new MessageBuilder();

        mbuilder.setEmbed(embeds.get(tpageNum - 1).build());

        return mbuilder.build();
    }

    private Message renderInfoPage(EmbedBuilder eb) {
        MessageBuilder mbuilder = new MessageBuilder();

        mbuilder.setEmbed(eb.build());

        return mbuilder.build();
    }

    private void enterInfoSubMenu(int id, Message oldMessage, MessageChannel channel) {
        //Create SubmenuPaginator
        OmbiCallers caller = new OmbiCallers();
        EmbedManager embedManager = new EmbedManager();
        SearchSubmenu.Builder ePBuilder = new SearchSubmenu.Builder()
                .setFinalAction(message -> {
                    try {
                        message.clearReactions().queue();
                    } catch (PermissionException ex) {
                        message.delete().queue();
                    }
                })
                .setEventWaiter(waiter);
        //Set Media type
        if (type == 1) {
            ePBuilder.setTvType();
            //get the media embed and the status
            TvInfo info = caller.ombiTvInfo(String.valueOf(id));
            boolean isRequested = info.getRequested();
            int isAvailable = info.getPlexAvailabilityInt();
            String mediaId = String.valueOf(info.getId());
            EmbedBuilder infoEmbed = embedManager.createTvMoreInfoEmbed(info);

            ePBuilder.setEmbedArray(infoEmbed);
            ePBuilder.setRequestState(isRequested);
            ePBuilder.setAvailability(isAvailable);
            ePBuilder.setMediaID(String.valueOf(info.getId()));
            ePBuilder.setMediaID(mediaId);

        } else if (type == 2) {
            ePBuilder.setMovieType();
            MovieInfo info = caller.ombiMovieInfo(String.valueOf(id));
            boolean isRequested = info.getRequested();
            int isAvailable = info.getAvailabilityInt();
            String mediaId = String.valueOf(info.getId());
            EmbedBuilder infoEmbed = embedManager.createMovieMoreInfoEmbed(info);

            ePBuilder.setEmbedArray(infoEmbed);
            ePBuilder.setRequestState(isRequested);
            ePBuilder.setAvailability(isAvailable);
            ePBuilder.setMediaID(mediaId);
        }
        SearchSubmenu p = ePBuilder
                .build();

        if (oldMessage == null) {
            p.paginate(channel, 1);
        } else {
            p.paginate(oldMessage, 1);
        }

    }

    public static class Builder extends Menu.Builder<Builder, SearchEmbedMenu> {

        private ArrayList<EmbedBuilder> embeds = new ArrayList<>();
        private ArrayList<Integer> submenuEmbeds = new ArrayList<Integer>();

        private boolean wrapPageEnds = false;
        private Consumer<Message> finalAction = m -> m.delete().queue();
        private String text = null;
        private int type = 1;
        private CommandEvent event;

        @Override
        public SearchEmbedMenu build() {
            Checks.check(waiter != null, "Must set an EventWaiter");
            Checks.check(!embeds.isEmpty(), "Must include at least one item to paginate");

            return new SearchEmbedMenu(waiter, users, roles, timeout, unit, embeds, submenuEmbeds, wrapPageEnds, finalAction, text, type, event);

        }

        public Builder setEmbedArray(ArrayList<EmbedBuilder> embeds) {
            this.embeds = embeds;
            return this;
        }

        public Builder setSubmenuEmbedArray(ArrayList<Integer> submenuEmbeds) {
            this.submenuEmbeds = submenuEmbeds;
            return this;
        }

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Builder setFinalAction(Consumer<Message> finalAction) {
            this.finalAction = finalAction;
            return this;
        }

        public Builder setTvType() {
            this.type = 1;
            return this;
        }

        public Builder setMovieType() {
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

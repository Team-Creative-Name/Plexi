package paginators.submenus;

import apis.ombi.OmbiCallers;
import apis.ombi.templateClasses.tv.moreInfo.TvInfo;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.jagrosh.jdautilities.menu.Menu;
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

import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class SearchSubmenu extends Menu {


    private static final String ADD_TO_REQUESTLIST = "\uD83D\uDC4D";
    private static final String STOP = "\uD83D\uDED1";
    private static final String REMOVE_FROM_REQUESTLIST = "\uD83D\uDC4E";
    private static final String REQUEST_LATEST = "\uD83C\uDD95";
    //Big list of global variables
    private final EmbedBuilder embeds;
    private final int pages;
    private final boolean wrapPageEnds;
    private final Consumer<Message> finalAction;
    private final String text;
    private final int type;
    private final boolean isRequested;
    private final int isAvailable;
    private final String mediaId;
    private final TvInfo tvInfo;


    SearchSubmenu(EventWaiter waiter, Set<User> users, Set<Role> roles, long timeout, TimeUnit unit, EmbedBuilder embeds, boolean wrapPageEnds, Consumer<Message> finalAction, String text, int type, boolean isRequested, int isAvailable, String mediaId, TvInfo tvInfo) {
        super(waiter, users, roles, timeout, unit);
        this.embeds = embeds;
        this.pages = 1;
        this.wrapPageEnds = wrapPageEnds;
        this.finalAction = finalAction;
        this.text = text;
        this.isRequested = isRequested;
        this.isAvailable = isAvailable;
        this.mediaId = mediaId;
        this.tvInfo = tvInfo;
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
        Message msg = renderSearchPage(pageNum);
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

    //TODO: Figure out a way to validate reactions.
    private void handleMessageReactionAddAction(MessageReactionAddEvent event, Message message, int pageNum) {
        int newPageNum = pageNum;
        OmbiCallers caller = new OmbiCallers();

        switch (event.getReaction().getReactionEmote().getName()) {
            case ADD_TO_REQUESTLIST:

                //Type 1 is tv, 2 is movie - chooses method based upon type
                if (type == 1) {
                    //event.getChannel().sendMessage("TV requests are currently disabled").queue();
                    event.getChannel().sendMessage(caller.requestTv(mediaId, false, isAvailable, tvInfo)).queue();
                } else if (type == 2) {
                    event.getChannel().sendMessage(caller.requestMovie(mediaId)).queue();
                }
                //Stop the submenu after the request is sent
                finalAction.accept(message);
                return;
            case REMOVE_FROM_REQUESTLIST:
                //TODO: Make this do something
                event.getChannel().sendMessage("Removing from requestList").queue();
                finalAction.accept(message);
                return;
            case STOP:
                finalAction.accept(message);
                return;
            case REQUEST_LATEST:
                //ensure this is a tv request
                if (type == 1) {
                    event.getChannel().sendMessage(caller.requestTv(mediaId, true, isAvailable, tvInfo)).queue();
                }
                finalAction.accept(message);

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
            m.clearReactions().queue();

            //add emotes depending on media status
            if (!isRequested && (isAvailable == 1 || isAvailable == 0)) {
                m.addReaction(ADD_TO_REQUESTLIST).queue();
            } else if (isRequested && (isAvailable == 1 || isAvailable == 0)) {
                m.addReaction(REMOVE_FROM_REQUESTLIST).queue();
            }
            //add new reaction if the media type is a tv show (1)
            if(type == 1){
                m.addReaction(REQUEST_LATEST).queue();
            }
            m.addReaction(STOP).queue(v -> pagination(m, pageNum), t -> pagination(m, pageNum));
        });
    }

    private boolean checkReaction(MessageReactionAddEvent event, long messageId) {
        if (event.getMessageIdLong() != messageId)
            return false;
        switch (event.getReactionEmote().getName()) {
            // LEFT, STOP, RIGHT, BIG_LEFT, BIG_RIGHT all fall-through to
            // return if the User is valid or not. If none trip, this defaults
            // and returns false.
            case REMOVE_FROM_REQUESTLIST:
            case STOP:
            case ADD_TO_REQUESTLIST:
            case REQUEST_LATEST:
                return isValidUser(event.getUser(), event.isFromGuild() ? event.getGuild() : null);
            //return true;
            default:
                return false;
        }
    }

    //This method is called by the paginate method and returns a message type object that is ready to send
    private Message renderSearchPage(int tpageNum) {
        MessageBuilder mbuilder = new MessageBuilder();

        mbuilder.setEmbed(embeds.build());

        return mbuilder.build();
    }

    public static class Builder extends Menu.Builder<Builder, SearchSubmenu> {

        private EmbedBuilder embeds = new EmbedBuilder();


        private final boolean wrapPageEnds = false;
        private Consumer<Message> finalAction = m -> m.delete().queue();
        private String text = null;
        private int type = 1;
        private boolean isRequested = false;
        private int isAvailable = 1;
        private String mediaID = "";
        private TvInfo tvInfo = null;

        @Override
        public SearchSubmenu build() {
            Checks.check(waiter != null, "Must set an EventWaiter");
            Checks.check(!embeds.isEmpty(), "Must include at least one item to paginate");

            return new SearchSubmenu(waiter, users, roles, timeout, unit, embeds, wrapPageEnds, finalAction, text, type, isRequested, isAvailable, mediaID, tvInfo);
        }

        public Builder setEmbedArray(EmbedBuilder embeds) {
            this.embeds = embeds;
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

        public Builder setMediaID(String mediaID) {
            this.mediaID = mediaID;
            return this;
        }

        public void setRequestState(boolean isRequested) {
            this.isRequested = isRequested;
        }

        public void setAvailability(int isAvailable) {
            this.isAvailable = isAvailable;
        }

        public Builder setTvInfo(TvInfo tvInfo) {
            this.tvInfo = tvInfo;
            return this;
        }
    }

}

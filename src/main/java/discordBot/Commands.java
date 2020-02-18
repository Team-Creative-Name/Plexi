package discordBot;

import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import discordBot.commands.*;

public class Commands extends CommandClientBuilder {


    public void commandList(EventWaiter waiter) {
        this.addCommand(new SearchCommand(waiter));
        this.addCommand(new MediaInfoCommand());
        this.addCommand(new RequestCommand());
        this.addCommand(new GetMissingEpisodes());
        this.addCommand(new ShutdownCommand());
        this.addCommand(new RestartCommand());
    }
}
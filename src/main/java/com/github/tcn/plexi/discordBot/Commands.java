package com.github.tcn.plexi.discordBot;

import com.github.tcn.plexi.discordBot.commands.*;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;

public class Commands extends CommandClientBuilder {

    public void commandList(EventWaiter waiter) {
        this.addCommand(new SearchCommand(waiter));
        this.addCommand(new MediaInfoCommand());
        this.addCommand(new RequestCommand());
        this.addCommand(new GetMissingEpisodes());
        this.addCommand(new ShutdownCommand());
        this.addCommand(new RestartCommand());
        this.addCommand(new getPingCommand());
        this.addCommand(new ViewRequestsCommand(waiter));
        this.addCommand(new UpcomingCommand(waiter));
    }
}
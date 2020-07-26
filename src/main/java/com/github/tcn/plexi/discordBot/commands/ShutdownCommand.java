package com.github.tcn.plexi.discordBot.commands;

import com.github.tcn.plexi.discordBot.PlexiBot;
import com.github.tcn.plexi.settingsManager.Settings;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class ShutdownCommand extends Command {
    public ShutdownCommand() {
        this.name = "shutdown";
        this.aliases = new String[]{"stop"};
        this.help = "safely shuts off the bot";
        this.guildOnly = false;
        this.ownerCommand = true;
    }

    @Override
    protected void execute(CommandEvent event) {
        event.reply("Plexi is shutting down.");

        //log that the command was used
        Settings.getInstance().getLogger().info(event.getAuthor().getName() + " has used the shutdown command");

        //turn off plexi
        PlexiBot.getInstance().stopBot();
        System.exit(0);
    }
}

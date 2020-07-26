package com.github.tcn.plexi.discordBot.commands;

import com.github.tcn.plexi.discordBot.PlexiBot;
import com.github.tcn.plexi.settingsManager.Settings;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;


public class RestartCommand extends Command {

    public RestartCommand() {
        this.name = "restart";
        this.help = "This command allows the owner to restart plexi if needed";
        this.ownerCommand = true;
        this.guildOnly = false;
    }

    @Override
    protected void execute(CommandEvent event) {
        event.reply("Plexi will be back in 1 second. Please wait.");
        //log the command's usage
        Settings.getInstance().getLogger().info(event.getAuthor().getName() + " has used the restart command");

        PlexiBot.getInstance().restartBot();
    }
}

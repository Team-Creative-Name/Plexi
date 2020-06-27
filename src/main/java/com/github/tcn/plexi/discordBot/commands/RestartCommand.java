package com.github.tcn.plexi.discordBot.commands;

import com.github.tcn.plexi.discordBot.PlexiBot;
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
        PlexiBot.getInstance().restartBot();
    }
}

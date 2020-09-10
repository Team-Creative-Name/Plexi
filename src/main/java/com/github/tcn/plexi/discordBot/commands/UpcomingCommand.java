package com.github.tcn.plexi.discordBot.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class UpcomingCommand extends Command {

    public UpcomingCommand(){
        this.name = "upcoming";
        this.help = "This command lists upcoming movie and tv releases";
        this.arguments = "<tv|movie>";
        this.aliases = new String[]{"u", "Upcoming", "up", "U", "Up"};
        this.ownerCommand = false;
        this.guildOnly = true;      
    }

    @Override
    protected void execute(CommandEvent event) {

    }
}

package discordBot.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class ShutdownCommand extends Command {
    public ShutdownCommand() {
        this.name = "shutdown";
        this.help = "safely shuts off the bot";
        this.guildOnly = false;
        this.ownerCommand = true;
    }

    @Override
    protected void execute(CommandEvent event) {
        event.reactWarning();
        event.getJDA().retrieveApplicationInfo().queue();


        System.exit(0);
    }
}

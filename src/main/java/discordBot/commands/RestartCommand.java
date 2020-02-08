package discordBot.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import discordBot.PlexiBot;

import javax.security.auth.login.LoginException;

public class RestartCommand extends Command {

    public RestartCommand() {
        this.name = "restart";
        this.help = "This command allows the owner to restart plexi if needed";
        this.ownerCommand = true;
        this.guildOnly = false;
    }

    @Override
    protected void execute(CommandEvent event) {
        event.reply("System is going down NOW!");
        try {
            PlexiBot.restartBot(event.getJDA());
        } catch (LoginException e) {
            System.out.println("ERROR RESTARTING JDA");
            e.printStackTrace();
        }

    }


}

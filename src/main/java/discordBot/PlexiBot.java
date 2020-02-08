package discordBot;

import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import settingsManager.Settings;

import javax.security.auth.login.LoginException;


public class PlexiBot {

    //Create settings object to get settings


    public static void startBot() throws LoginException {
        //Create Commands object
        Commands commandList = new Commands();

        //create EventWaiter object
        EventWaiter waiter = new EventWaiter();

        //grab this from the settings object
        commandList.setOwnerId(Settings.getOwnerId());
        commandList.setPrefix(Settings.getPrefix());

        //send the waiter to the CommandList object
        commandList.commandList(waiter);


        //Create JDA bot instance
        JDA botInstance = new JDABuilder(AccountType.BOT)
                .setToken(Settings.getToken())
                .addEventListeners(commandList.build(), waiter)
                .build();

    }

    public static void shutdownBot(JDA botInstance) {
        botInstance.shutdown();
    }

    public static void restartBot(JDA botInstance) throws LoginException {
        shutdownBot(botInstance);
        startBot();
    }
}

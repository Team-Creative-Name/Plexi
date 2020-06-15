package com.github.tcn.plexi.discordBot;

import com.github.tcn.plexi.settingsManager.Settings;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;


public class PlexiBot {

    //reference to JDA - also used to determine if the bot is running
    private static JDA bot = null;


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
        JDA botInstance;

        try {
            botInstance = JDABuilder.createDefault(Settings.getToken()).build();
            botInstance.addEventListener(commandList.build(), waiter);


        } catch (Exception e) {
            System.out.println("Error: " + e.getLocalizedMessage());
            return;
        }
        try {
            botInstance.awaitReady();
            System.out.println("Startup Complete!");
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("Error Starting Bot");
            shutdownBot();

        }


        bot = botInstance;
    }

    public static void shutdownBot() {

        bot.shutdown();
        bot = null;
        System.out.println("Shutdown Complete");
    }

    public static void restartBot(JDA botInstance) throws LoginException {
        shutdownBot();
        startBot();
    }

    public static JDA getJDAInstance() {
        return bot;
    }

    public static boolean isRunning() {
        return bot != null;
    }
}

package com.github.tcn.plexi.discordBot;

import com.github.tcn.plexi.settingsManager.Settings;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlexiBot {

    //globals

    //reference to PlexiBot obj
    private static PlexiBot botObj = null;
    //reference to plexi object
    private JDA botInstance = null;
    

    //lock the constructor
    private PlexiBot() {

    }

    //static methods
    public static PlexiBot getInstance() {
        //if there is no plexi obj, create one.
        if (botObj == null) {
            botObj = new PlexiBot();
        }
        //return the bot object
        return botObj;
    }

    //public methods
    public JDA getJDAInstance() {
        if (botInstance == null) {
            return null;
        }
        return botInstance;
    }

    public boolean isRunning() {
        return botInstance != null;
    }

    public void startBot() {
        //get instance of settings class
        Settings settings = Settings.getInstance();

        //Create Commands object
        Commands commandList = new Commands();

        //create EventWaiter object
        EventWaiter waiter = new EventWaiter();

        //grab this from the settings object
        commandList.setOwnerId(settings.getOwnerID());
        commandList.setPrefix(settings.getPrefix());

        //send the waiter to the CommandList object
        commandList.commandList(waiter);


        //Create JDA bot instance
        JDA botInstance;

        try {
            botInstance = JDABuilder.createDefault(settings.getToken()).build();
            botInstance.addEventListener(commandList.build(), waiter);



        } catch (Exception e) {
            Settings.getInstance().getLogger().warn("Unable to start discord bot: " + e.getLocalizedMessage() );
            return;
        }
        try {
            botInstance.awaitReady();
            Settings.getInstance().getLogger().info("Startup Complete!");
        } catch (InterruptedException e) {
            Settings.getInstance().getLogger().error("Discord element interrupted while starting. Error: " + e.getLocalizedMessage());
            stopBot();
        }
        //set global botInstance obj to the newly created one
        this.botInstance = botInstance;

    }

    public void stopBot() {
        //ensure that there is a bot instance running
        if (botInstance != null) {
            botInstance.shutdownNow();
            //remove reference to other bot
            botInstance = null;
            Settings.getInstance().getLogger().info("Discord Shutdown Complete");
        } else {
            //we cant call the settings file version of the logger bc the settings file is calling this method on startup
            Logger plexiLogger = LoggerFactory.getLogger("Plexi");
            plexiLogger.error("Unable to shut down discord connection: no bot is currently running");
        }
    }

    public void restartBot() {
        stopBot();
        startBot();
    }

    //methods to return discord ping times
    public long getGatewayPing() {
        return botInstance.getGatewayPing();
    }

    public long getRestPing() {
        return botInstance.getRestPing().complete();
    }
}

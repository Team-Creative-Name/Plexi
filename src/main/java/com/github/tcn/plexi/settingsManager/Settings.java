package com.github.tcn.plexi.settingsManager;

import com.github.tcn.plexi.discordBot.PlexiBot;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Properties;


public class Settings {

    //Variables

    //reference to this object - the only one
    private static Settings SETTINGS_INSTANCE = null;
    //stuff loaded from the config file
    private String token = null;
    private String prefix = null;
    private String ombiURL = null;
    private String ombiKey = null;
    private String ownerID = null;
    //Variables useful
    private Path JAR_PATH;
    private URL INTERNAL_CONFIG_PATH;
    private Path USER_CONFIG_PATH;

    //privatized constructor to ensure that nothing else is able to instantiate this class
    private Settings() {
        //calls initVariables
        initVariables();
    }

    //this is the only way for an outside class to obtain a reference to this object
    public static Settings getInstance() {
        //if the object does not exist, create it
        if (SETTINGS_INSTANCE == null) {
            SETTINGS_INSTANCE = new Settings();
        }
        //return the object reference
        return SETTINGS_INSTANCE;
    }

    //attempt to set all of the global variables
    private void initVariables() {

        try {
            //first attempt to get the resource path and jar path
            JAR_PATH = Paths.get(Settings.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
            INTERNAL_CONFIG_PATH = this.getClass().getResource("/assets/config.txt");

            //now attempt to read from the configuration file and set values accordingly
            USER_CONFIG_PATH = JAR_PATH.getParent().resolve("config.txt");
            FileInputStream config = new FileInputStream(USER_CONFIG_PATH.toString());
            Properties properties = new Properties();

            properties.load(config);

            //Load settings
            token = properties.getProperty("token").replaceAll("^\"|\"$", "");
            System.out.println(token);
            ownerID = properties.getProperty("ownerID").replaceAll("^\"|\"$", "");
            System.out.println("Owner ID: " + ownerID);
            prefix = properties.getProperty("prefix").replaceAll("^\"|\"$", "");
            ombiURL = properties.getProperty("ombiURL").replaceAll("^\"|\"$", "");
            ombiKey = properties.getProperty("ombiKey").replaceAll("^\"|\"$", "");

        } catch (FileNotFoundException e) {
            System.out.println("Unable to locate configuration file!");
            try {
                generateConfigFile();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            System.out.println("A new one has been generated in " + JAR_PATH.getParent().toString());
            JOptionPane.showMessageDialog(null, "The config file was unable to be found. A new one has been generated at: " + JAR_PATH.getParent().toString(), "Plexi - Configuration Issue", JOptionPane.INFORMATION_MESSAGE);
            PlexiBot.shutdownBot();
            System.exit(1);

        } catch (Exception e) {
            //if we cant get these two, there is absolutely no way to continue program execution. We inform the user of an issue and terminate
            System.out.println("Error while initializing settings. We should never have this issue; what did you do? Eh, it was probably my fault.");
            System.out.println("Anyway, have a stacktrace. I've heard it can be useful when debugging");
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
            //pop open a dialog box to ensure the user is aware of what happened
            JOptionPane.showMessageDialog(null, "Unknown error, unable to continue program execution. Error: " + e.getLocalizedMessage() + "\nCheck the log for a bit more info (Hopefully)", "Plexi - Unknown Error", JOptionPane.INFORMATION_MESSAGE);
            //after the user closes that, shut down the bot if it is running and terminate the program
            PlexiBot.shutdownBot();
            System.exit(-1);
        }
    }

    //destroys the reference class and creates a new one. Also re-reads the config.txt file
    public void reloadSettings() {

    }

    //if the initVariables method is unable to locate the config file, we need to create a new one.
    private void generateConfigFile() {
        try {
            //open an inputStream for the internal config file
            InputStream resourceConfigStream = INTERNAL_CONFIG_PATH.openStream();
            //make sure that it was initialized properly
            if (resourceConfigStream == null) {
                System.out.println();
            }
            //now attempt to copy the file to the destination
            Files.copy(resourceConfigStream, USER_CONFIG_PATH, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println("Error creating config file: ");
            e.printStackTrace();
            PlexiBot.shutdownBot();
            System.exit(-1);
        } catch (Exception e) {
            System.out.println("Unknown Error: ");
            e.printStackTrace();
            PlexiBot.shutdownBot();
            System.exit(-1);
        }

    }


    //getters and setters for globals
    public String getToken() {
        return token;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getOmbiURL() {
        return ombiURL;
    }

    public String getOmbiKey() {
        return ombiKey;
    }

    public String getOwnerID() {
        return ownerID;
    }
}






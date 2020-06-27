package com.github.tcn.plexi.settingsManager;

import com.github.tcn.plexi.discordBot.PlexiBot;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Properties;


public class Settings {

    //Variables

    //reference to this object - the only one
    private static Settings SETTINGS_INSTANCE = null;
    //the version number
    private final String VERSION_NUMBER = "v1.0-beta.5.2";
    //stuff loaded from the config file
    private String TOKEN = null;
    private String PREFIX = null;
    private String OMBI_URL = null;
    private String OMBI_KEY = null;
    private String OWNER_ID = null;
    private URL INTERNAL_CONFIG_PATH;
    private Path USER_CONFIG_PATH;
    //Variables useful for class operations
    private Path JAR_PATH;

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
            //JAR_PATH = Paths.get(Settings.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
            JAR_PATH = new File(Settings.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).toPath();
            INTERNAL_CONFIG_PATH = this.getClass().getResource("/assets/config.txt");

            //now attempt to read from the configuration file and set values accordingly
            USER_CONFIG_PATH = JAR_PATH.getParent().resolve("config.txt");
            FileInputStream config = new FileInputStream(USER_CONFIG_PATH.toString());
            Properties properties = new Properties();

            properties.load(config);

            //Load settings
            TOKEN = properties.getProperty("token").replaceAll("^\"|\"$", "");
            System.out.println(TOKEN);
            OWNER_ID = properties.getProperty("ownerID").replaceAll("^\"|\"$", "");
            System.out.println("Owner ID: " + OWNER_ID);
            PREFIX = properties.getProperty("prefix").replaceAll("^\"|\"$", "");
            OMBI_URL = properties.getProperty("ombiURL").replaceAll("^\"|\"$", "");
            OMBI_KEY = properties.getProperty("ombiKey").replaceAll("^\"|\"$", "");

            if (!validateGlobals()) {
                JOptionPane.showMessageDialog(null, "The config file contains invalid settings, please check it and try again.", "Plexi - Configuration Issue", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }

        } catch (FileNotFoundException e) {
            System.out.println("Unable to locate configuration file!");
            generateConfigFile();
            System.out.println("A new one has been generated in " + JAR_PATH.getParent().toString());
            JOptionPane.showMessageDialog(null, "The config file was unable to be found. A new one has been generated at: " + JAR_PATH.getParent().toString(), "Plexi - Configuration Issue", JOptionPane.INFORMATION_MESSAGE);
            PlexiBot.shutdownBot();
            System.exit(0);

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

    //we need a way to validate as many of the settings variables as possible
    //This is very rudimentary validation that checks to ensure that values are not empty and arent default. The Ombi API is checked to see if it can connect.
    //TODO: Make this method a bit less clunky
    private boolean validateGlobals() {
        boolean isValid = true;

        //check to ensure token != null
        if (TOKEN.equals("") || TOKEN.equals("BOT_TOKEN_HERE")) {
            isValid = false;
        }
        //check to ensure prefix != null;
        if (PREFIX.equals("")) {
            isValid = false;
        }
        //check to ensure ombiURL != null;
        if (OMBI_URL.equals("") || OMBI_URL.equals("URL_HERE")) {
            isValid = false;
        }
        //check to ensure ombiKey != null;
        if (OMBI_KEY.equals("") || OMBI_KEY.equals("KEY_HERE")) {
            isValid = false;
        }
        //check to ensure ownerID != null;
        if (OWNER_ID.equals("") || OWNER_ID.equals("0")) {
            isValid = false;
        }

        //ping the ombi API to see if we have valid settings
        //if this throws an exception, we have invalid values
        //NOTE: There is no reason to check this if isValid is false
        if (isValid) {
            if (!checkOmbiConnectivity()) {
                isValid = false;
                //inform the user that we cannot connect to ombi
                JOptionPane.showMessageDialog(null, "Unable to connect to Ombi - Please check your settings", "Plexi - Connectivity Issue", JOptionPane.INFORMATION_MESSAGE);
            }
        }


        return isValid;
    }


    //returns true if we are able to connect to the Ombi API
    private boolean checkOmbiConnectivity() {
        Response response = null;
        try {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(OMBI_URL + "/api/v1/Status")
                    .addHeader("Accept", "application/json")
                    .addHeader("ApiKey", OMBI_KEY)
                    .build();

            response = client.newCall(request).execute();
            //if that call didnt fail, we were able to connect
            return true;
        } catch (Exception e) {
            //if that call failed, we couldn't connect to ombi
            return false;
        } finally {
            //close the response if one was created 
            if (response != null) {
                response.close();
            }
        }
    }


    //getters and setters for globals
    public String getToken() {
        return TOKEN;
    }

    public String getPrefix() {
        return PREFIX;
    }

    public String getOmbiUrl() {
        return OMBI_URL;
    }

    public String getOmbiKey() {
        return OMBI_KEY;
    }

    public String getOwnerID() {
        return OWNER_ID;
    }

    public String getVersionNumber() {
        return VERSION_NUMBER;
    }
}

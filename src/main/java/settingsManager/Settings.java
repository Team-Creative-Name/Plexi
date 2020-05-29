package settingsManager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

//TODO: Is this a good way to handle settings? It is certainly easier to use (Settings.getSetting) but there must be some convention
public class Settings {

    //This class loads values from a config file - if one does not exits, we make one

    private static Path path = null;
    private static String token, prefix, ombiURL, ombiKey, ownerID;


    //this is NOT user configurable
    //TODO find a better way to store the version num. This string works, but there must be a way to automate changes.
    private static final String version = "v1.0-beta.2";
    private boolean exists;

    //begin getters/setters

    public static Path getPath() {
        return path;
    }

    public static String getToken() {
        return token;
    }

    public static String getPrefix() {
        return prefix;
    }

    public static String getOmbiUrl() {
        return ombiURL;
    }

    public static String getOmbiKey() {
        return ombiKey;
    }

    public static String getOwnerId() {
        return ownerID;
    }

    public static String getVersion() {
        return version;
    }

    //begin methods

    public static void init() throws IllegalArgumentException {

        try {

            Properties properties = new Properties();
            path = Paths.get(System.getProperty("config.file", System.getProperty("config", "config.txt")));
            FileInputStream config = new FileInputStream(path.toString());


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
            System.out.println("Missing Config File! Generating new one and terminating program.");
            generateConfig();
            System.exit(0);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error reading settings file!");
        }


    }

    //TODO determine if we should attempt a reload or not. Maybe add a gui button or something... 
    public static void reload() {
        init();
    }

    //this will create a new config file in the same folder as the plexi jar
    private static void generateConfig() {


    }


}

package settingsManager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class Settings {

    //This class loads values from a config file - if one does not exits, we make one

    private static Path path = null;
    private static String token, prefix, ombiURL, ombiKey;

    private static String ownerID;

    private boolean exists;

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

    public void init() {

        try {

            Properties properties = new Properties();
            path = Paths.get(System.getProperty("config.file", System.getProperty("config", "config.txt")));
            FileInputStream config = new FileInputStream(path.toString());

            properties.load(config);

            //Load settings
            token = properties.getProperty("token").replaceAll("^\"|\"$", "");
            ownerID = properties.getProperty("ownerID").replaceAll("^\"|\"$", "");
            prefix = properties.getProperty("prefix").replaceAll("^\"|\"$", "");
            ombiURL = properties.getProperty("ombiURL").replaceAll("^\"|\"$", "");
            ombiKey = properties.getProperty("ombiKey").replaceAll("^\"|\"$", "");


        } catch (FileNotFoundException e) {
            System.out.println("Missing Config File! Generating new one and terminating program.");
            generateConfig();
            System.exit(0);
        } catch (Exception e) {
            System.out.println("An error occurred while reading properties file");
            System.out.println(e);
        }


    }

    public void reload() {
        init();
    }

    private void generateConfig() {

    }


}

import discordBot.PlexiBot;
import settingsManager.Settings;

import javax.security.auth.login.LoginException;

public class Main {

    public static void main(String[] args) {

        String logo = "               /$$                     /$$           \n" +
                "              | $$                    |__/           \n" +
                "      /$$$$$$ | $$  /$$$$$$  /$$   /$$ /$$           \n" +
                "     /$$__  $$| $$ /$$__  $$|  $$ /$$/| $$           \n" +
                "    | $$  \\ $$| $$| $$$$$$$$ \\  $$$$/ | $$           \n" +
                "    | $$  | $$| $$| $$_____/  >$$  $$ | $$           \n" +
                "    | $$$$$$$/| $$|  $$$$$$$ /$$/\\  $$| $$           \n" +
                "    | $$____/ |__/ \\_______/|__/  \\__/|__/           \n" +
                "    | $$                                             \n" +
                "    | $$                                             \n" +
                "    |__/";

        System.out.println(logo);

        Settings loader = new Settings();
        loader.init();

        try {
            PlexiBot.startBot();
        } catch (LoginException e) {
            System.out.println("It died");
        }
    }
}

//http://www.jsonschema2pojo.org is great!
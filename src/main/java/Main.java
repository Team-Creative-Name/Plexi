import gui.MainView;
import settingsManager.Settings;

import javax.swing.*;

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


        Settings.init();


        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainView().setVisible(true);
                System.out.println(logo);
                System.out.println("\n\nPress the start button to start Plexi.");
            }
        });


    }
}

//http://www.jsonschema2pojo.org is great!
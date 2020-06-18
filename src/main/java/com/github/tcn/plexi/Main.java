package com.github.tcn.plexi;

import com.github.tcn.plexi.gui.MainView;
import com.github.tcn.plexi.settingsManager.Settings;

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


        //check to make sure that we can find the config file - this will fail on first run

        //boolean settingsSuccess = false;

        //call getInstance just to make sure that we can load it
        try {
            Settings.getInstance();
           // settingsSuccess = true;
        } catch (IllegalArgumentException e) {
           // settingsSuccess = false;
        }


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
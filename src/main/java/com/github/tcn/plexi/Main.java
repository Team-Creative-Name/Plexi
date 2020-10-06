package com.github.tcn.plexi;

import com.github.tcn.plexi.gui.MainView;
import com.github.tcn.plexi.pluginManager.PluginManager;
import com.github.tcn.plexi.settingsManager.Settings;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        //call getInstance in order to ensure that it is loaded before anything else
        Settings.getInstance();


        String logo =   "               /$$                     /$$           \n" +
                "              | $$                    |__/           \n" +
                "      /$$$$$$ | $$  /$$$$$$  /$$   /$$ /$$           \n" +
                "     /$$__  $$| $$ /$$__  $$|  $$ /$$/| $$           \n" +
                "    | $$  \\ $$| $$| $$$$$$$$ \\  $$$$/ | $$         \n" +
                "    | $$  | $$| $$| $$_____/  >$$  $$ | $$           \n" +
                "    | $$$$$$$/| $$|  $$$$$$$ /$$/\\  $$| $$          \n" +
                "    | $$____/ |__/ \\_______/|__/  \\__/|__/         \n" +
                "    | $$                                             \n" +
                "    | $$   Created by Team Creative Name             \n" +
                "    |__/   Version " + Settings.getInstance().getVersionNumber();







        SwingUtilities.invokeLater(() -> {
            new MainView().setVisible(true);

            System.out.println(logo);

            //TODO: This needs to be moved to a different point in the program if we want to interact with JDA at all.
            //check to see if we want to load plugins
            if(Settings.getInstance().getAllowPluginLoading()){
                PluginManager manager = new PluginManager();
                manager.loadPlugins();
            }else{
                //inform the user that plugins are disabled
                Settings.getInstance().getLogger().info("Plugin loading is disabled. If you want plugins, change settings in the config file.");
            }

            System.out.println("\n\nPress the start button to start Plexi.");

        });
    }
}

//http://www.jsonschema2pojo.org is great!
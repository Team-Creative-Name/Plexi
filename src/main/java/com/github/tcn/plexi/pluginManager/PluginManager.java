package com.github.tcn.plexi.pluginManager;

import com.github.tcn.plexi.discordBot.PlexiBot;
import com.github.tcn.plexi.settingsManager.Settings;
import org.slf4j.Logger;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.jar.JarFile;

public class PluginManager {
    final String PLUGIN_FOLDER = "plugins";
    final Logger logger = Settings.getInstance().getLogger();
    private int pluginCount = 0;

    public void loadPlugins(){
        File pluginFolder = new File(PLUGIN_FOLDER);
        //first we need to make sure that the plugin folder exists at all
        if(!pluginFolder.exists()){
            logger.error("The Plugins folder cannot be found!");
            //if it doesn't, create one and log its creation
            if(pluginFolder.mkdirs()){
                //this is only true if a folder was made successfully
                logger.info("A new plugins folder was created at" + pluginFolder.getPath());
            }else{
                //There is something wrong. Inform the user and stop execution
                logger.info("Unable to generate the plugins folder! Please check file permission in the plexi directory");
                JOptionPane.showMessageDialog(null, "A plugin folder was not found and a new one could not be generated. \n" +
                        "Please check permissions in your Plexi folder");
                PlexiBot.getInstance().stopBot();
                System.exit(-1);
            }
        }
        //if we get to this point, we are certain that a plugin folder exists
        //lets see if it has anything in it
        File[] pluginJars = pluginFolder.listFiles(
                (dir, name) -> name.endsWith(".jar")
        );

        ArrayList<URL> urls = new ArrayList<>();
        ArrayList<String> classes = new ArrayList<>();

        //if we have anything in the folder, attempt to load it
        if(pluginJars != null){
            Arrays.stream(pluginJars).forEach(file -> {
                try{
                    JarFile jarFile = new JarFile(file);
                    urls.add(new URL("jar:file:" + PLUGIN_FOLDER + "/" + file.getName() + "!/"));
                    jarFile.stream().forEach(jarEntry -> {
                        if(jarEntry.getName().endsWith(".class")){
                            classes.add(jarEntry.getName());
                        }
                    });
                }catch (IOException e){
                    logger.error("Error While reading from Plugin. " + e.getMessage());
                    //TODO: This is going to need to be a bit more verbose in the future
                }
            });
            //At this point, we have an arrayList full of plugin classes. Now we can work on loading them
            URLClassLoader pluginLoader = new URLClassLoader(urls.toArray(new URL[urls.size()]));
            classes.forEach(s ->{
                try{
                    Class loadingClass = pluginLoader.loadClass(s.replaceAll("/",".").replace(".class",""));
                    Class[] interfaces = loadingClass.getInterfaces();
                    for(Class currentInterface : interfaces){

                        //here we can work on loading based upon the interface type
                        if(currentInterface == PluginInterface.class){
                            PluginInterface plugin = (PluginInterface) loadingClass.getDeclaredConstructor().newInstance();
                            //we now have an instance of the plugin, work on loading it
                            if(plugin.load()){
                                System.out.println("Loaded Plugin " + loadingClass.getCanonicalName());
                                pluginCount++;
                            }
                            break;
                        }
                    }

                }catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e){
                    logger.error("Error while attempting to load plugins");
                }

            });
        }

        //at this point, all valid plugins should be properly loaded
        logger.info("Plugin loading complete; " + pluginCount + " plugin(s) loaded!");
    }

}

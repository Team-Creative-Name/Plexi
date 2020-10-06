package com.github.tcn.plexi.pluginManager;

public interface PluginInterface {
    //allow a plugin to perform actions on initial load
    boolean load();

    //allow program to register a command
    boolean registerCommand();
}

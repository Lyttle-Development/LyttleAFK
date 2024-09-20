package com.lyttldev.lyttleafk.types;

import com.lyttldev.lyttleafk.LyttleAFK;

public class Configs {
    private final LyttleAFK plugin;

    // Configs
    public Config general;
    public Config messages;

    // Default configs
    public Config defaultMessages;


    public Configs(LyttleAFK plugin) {
        this.plugin = plugin;

        // Configs
        general = new Config(plugin, "config.yml");
        messages = new Config(plugin, "messages.yml");

        // Default configs
        defaultMessages = new Config(plugin, "#defaults/messages.yml");
    }

    public void reload() {
        general.reload();
        messages.reload();

        plugin.reloadConfig();
    }

    private String getConfigPath(String path) {
        return plugin.getConfig().getString("configs." + path);
    }
}

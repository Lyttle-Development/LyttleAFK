package com.lyttldev.lyttleafk.types;

import com.lyttldev.lyttleafk.LyttleAFK;

public class Configs {
    private final LyttleAFK plugin;

    // Configs
    public Config general;

    public Configs(LyttleAFK plugin) {
        this.plugin = plugin;

        // Configs
        general = new Config(plugin, "config.yml");
    }

    public void reload() {
        general.reload();

        plugin.reloadConfig();
    }

    private String getConfigPath(String path) {
        return plugin.getConfig().getString("configs." + path);
    }
}

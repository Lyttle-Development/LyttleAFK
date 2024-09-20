package com.lyttldev.lyttleafk;

import com.lyttldev.lyttleafk.commands.*;
import com.lyttldev.lyttleafk.handlers.PlayerJoinListener;
import com.lyttldev.lyttleafk.types.Configs;
import com.lyttldev.lyttleafk.utils.Console;
import com.lyttldev.lyttleafk.utils.Message;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class LyttleAFK extends JavaPlugin {
    public Configs config;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        // Setup config after creating the configs
        config = new Configs(this);
        // Migrate config
        migrateConfig();

        // Plugin startup logic
        Console.init(this);
        Message.init(this);

        // Commands
        new LyttleAFKCommand(this);

        // Listeners
        new PlayerJoinListener(this);
    }

    @Override
    public void onDisable() {
    }

    @Override
    public void saveDefaultConfig() {
        String configPath = "config.yml";
        if (!new File(getDataFolder(), configPath).exists())
            saveResource(configPath, false);

        String messagesPath = "messages.yml";
        if (!new File(getDataFolder(), messagesPath).exists())
            saveResource(messagesPath, false);

        // Defaults:
        String defaultPath = "#defaults/";
        String defaultGeneralPath =  defaultPath + configPath;
        saveResource(defaultGeneralPath, true);

        String defaultMessagesPath =  defaultPath + messagesPath;
        saveResource(defaultMessagesPath, true);
    }

    private void migrateConfig() {
        if (!config.general.contains("config_version")) {
            config.general.set("config_version", 0);
        }

        switch (config.general.get("config_version").toString()) {
            case "0":
                // Migrate config entries.
                config.messages.set("prefix", config.defaultMessages.get("prefix"));
                config.messages.set("no_permission", config.defaultMessages.get("no_permission"));
                config.messages.set("player_not_found", config.defaultMessages.get("player_not_found"));
                config.messages.set("must_be_player", config.defaultMessages.get("must_be_player"));
                config.messages.set("message_not_found", config.defaultMessages.get("message_not_found"));
                config.messages.set("staff_usage", config.defaultMessages.get("staff_usage"));
                config.messages.set("staff_no_reason", config.defaultMessages.get("staff_no_reason"));
                config.messages.set("staff_no_location", config.defaultMessages.get("staff_no_location"));
                config.messages.set("staff_no_inventory", config.defaultMessages.get("staff_no_inventory"));
                config.messages.set("staff_inventory_restore_failed", config.defaultMessages.get("staff_inventory_restore_failed"));
                config.messages.set("staff_enabled", config.defaultMessages.get("staff_enabled"));
                config.messages.set("staff_enable_failed", config.defaultMessages.get("staff_enable_failed"));
                config.messages.set("staff_disabled", config.defaultMessages.get("staff_disabled"));
                config.messages.set("staff_disable_failed", config.defaultMessages.get("staff_disable_failed"));
                config.messages.set("staff_log", config.defaultMessages.get("staff_log"));

                // Update config version.
                config.general.set("config_version", 1);

                // Recheck if the config is fully migrated.
                migrateConfig();
                break;
            default:
                break;
        }
    }
}

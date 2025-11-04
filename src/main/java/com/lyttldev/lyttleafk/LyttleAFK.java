package com.lyttldev.lyttleafk;

import com.lyttldev.lyttleafk.commands.*;
import com.lyttldev.lyttleafk.modules.*;
import com.lyttldev.lyttleafk.handlers.PlayerMoveListener;
import com.lyttldev.lyttleafk.types.Configs;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.plugin.Plugin;
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
        PlayerAFK.initTimer();

        // Register commands
        LifecycleEventManager<Plugin> manager = this.getLifecycleManager();
        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            this.registerCommands(commands);
        });

        // Listeners
        new PlayerMoveListener(this);
    }

    public void registerCommands(Commands commands) {
        LyttleAFKCommand.createCommand(this, commands);
    }

    @Override
    public void saveDefaultConfig() {
        String configPath = "config.yml";
        if (!new File(getDataFolder(), configPath).exists())
            saveResource(configPath, false);

        // Defaults:
        String defaultPath = "#defaults/";
        String defaultGeneralPath =  defaultPath + configPath;
        saveResource(defaultGeneralPath, true);
    }

    private void migrateConfig() {
        if (!config.general.contains("config_version")) {
            config.general.set("config_version", 0);
        }

        switch (config.general.get("config_version").toString()) {
            case "0":
                // Update config version.
                config.general.set("scoreboard_name", config.general.get("scoreboard_name"));
                config.general.set("config_version", 1);

                // Recheck if the config is fully migrated.
                migrateConfig();
                break;
            default:
                break;
        }
    }
}

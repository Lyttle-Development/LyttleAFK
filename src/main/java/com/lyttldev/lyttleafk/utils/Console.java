package com.lyttldev.lyttleafk.utils;

import com.lyttldev.lyttleafk.LyttleAFK;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;

public class Console {
    public static LyttleAFK plugin;

    public static void init(LyttleAFK plugin) {
        Console.plugin = plugin;
    }

    public static void run(String command) {
        if (command == null || command.isEmpty()) return;
        ConsoleCommandSender console = Bukkit.getConsoleSender();
        Bukkit.getScheduler().callSyncMethod( plugin, () -> Bukkit.dispatchCommand( console, command ) );
    }

    public static void log(String message) {
        plugin.getLogger().info(message);
    }
}

package com.lyttldev.lyttleafk.handlers;

import com.lyttldev.lyttleafk.LyttleAFK;
import com.lyttldev.lyttleafk.commands.StaffCommand;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    public PlayerJoinListener(LyttleAFK plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        StaffCommand.onPlayerJoin(event.getPlayer());
    }
}

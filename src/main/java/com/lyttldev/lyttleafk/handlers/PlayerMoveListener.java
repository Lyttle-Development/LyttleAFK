package com.lyttldev.lyttleafk.handlers;

import com.lyttldev.lyttleafk.LyttleAFK;
import com.lyttldev.lyttleafk.modules.PlayerAFK;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener  implements Listener {
    private static PlayerAFK playerAFK;

    public PlayerMoveListener(LyttleAFK plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
        PlayerMoveListener.playerAFK = new PlayerAFK(plugin);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Location from = event.getFrom();
        Location to = event.getTo();

        // If 'to' or 'from' is null, exit early
        if (to == null || from == null) return;

        // Check if only the player's yaw (horizontal rotation) or pitch (vertical rotation) has changed
        if (from.getX() != to.getX() && from.getY() != to.getY() && from.getZ() != to.getZ()
            && (from.getYaw() != to.getYaw() || from.getPitch() != to.getPitch())) {

            // This means the player moved their mouse (changed view direction), not their position
            playerAFK.onMouseMove(event.getPlayer());
            return;
        }
    }
}

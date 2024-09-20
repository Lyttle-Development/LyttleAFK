package com.lyttldev.lyttleafk.modules;

import com.lyttldev.lyttleafk.LyttleAFK;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.Scoreboard;

public class PlayerAFK {
    private static LyttleAFK plugin;
    private static  String objectiveName = "afk-time";
    private final Scoreboard scoreboard;

    public PlayerAFK(LyttleAFK plugin) {
        PlayerAFK.plugin = plugin;
        objectiveName = (String) plugin.config.general.get("scoreboard_name");

        scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        registerObjective(scoreboard);
    }

    public void onMouseMove(Player player) {
        // Set player's AFK status to 0.
        Objective objective = scoreboard.getObjective(objectiveName);
        if (objective != null) {
            objective.getScore(player.getName()).setScore(0);
        }
    }

    private static void registerObjective(Scoreboard scoreboard) {
        Objective objective = scoreboard.getObjective(objectiveName);
        if (objective == null) {
            Component displayName = Component.text("AFK Time");
            Criteria criteria = Criteria.DUMMY;
            scoreboard.registerNewObjective(objectiveName, criteria, displayName);
        }
    }

    public static void initTimer() {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        registerObjective(scoreboard);

        // Add 1 to all players' AFK status every second.
        Bukkit.getScheduler().runTaskTimer(Bukkit.getPluginManager().getPlugin("LyttleAFK"), () -> {
            Objective objective = scoreboard.getObjective(objectiveName);
            if (objective != null) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    int score = objective.getScore(player.getName()).getScore();
                    objective.getScore(player.getName()).setScore(score + 1);
                }
            }
        }, 0, 20);
    }
}

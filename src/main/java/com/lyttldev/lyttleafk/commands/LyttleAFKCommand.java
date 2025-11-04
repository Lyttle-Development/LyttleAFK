package com.lyttldev.lyttleafk.commands;

import com.lyttldev.lyttleafk.LyttleAFK;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;

public class LyttleAFKCommand {
    private static LyttleAFK plugin;

    public static void createCommand(LyttleAFK lyttlePlugin, Commands commands) {
        plugin = lyttlePlugin;

        // Define the different nodes
        LiteralArgumentBuilder<CommandSourceStack> top = Commands.literal("lyttleafk")
                .then(Commands.literal("reload")
                        .requires(source -> source.getSender().hasPermission("lyttleafk.lyttleafk.reload"))
                        .executes(LyttleAFKCommand::reloadNode));

        // Defines root node functions
        top.requires(source -> source.getSender().hasPermission("lyttleafk.lyttleafk"));
        top.executes(LyttleAFKCommand::rootNode);

        // Finish the command
        commands.register(
                top.build(),
                "Admin command for the LyttleAFK plugin"
        );
    }


    private static int rootNode(CommandContext<CommandSourceStack> context) {
        CommandSender sender = context.getSource().getSender();
        Component version = Component.text("Plugin version: " + plugin.getDescription().getVersion());
        sender.sendMessage(version);
        return Command.SINGLE_SUCCESS;
    }

    private static int reloadNode(CommandContext<CommandSourceStack> context) {
        final CommandSender sender = context.getSource().getSender();
        plugin.config.reload();
        sender.sendMessage(Component.text("The config has been reloaded"));
        return Command.SINGLE_SUCCESS;
    }

}

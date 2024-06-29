package io.papermc.spawn.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import io.papermc.spawn.Broadcasting;
import io.papermc.spawn.Main;

public class CommandTeleportCooldown implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("minecraft.op")) {
            Broadcasting.sendPermissionsErrorResponse(sender);
            return true;
        }

        if (args.length==0) {
            Broadcasting.sendMessageResponse(sender, "Teleport cooldown is currently configured to " + Main.teleportCooldown + " seconds.");
            return true;
        }

        int teleportCooldown = 0;

        try {
            teleportCooldown = Integer.parseInt(args[0]);
            if (teleportCooldown < 0) {
                Broadcasting.sendErrorResponse(sender, "Please enter a valid integer.");
                return true;
            }
        } catch (NumberFormatException e) {
            Broadcasting.sendErrorResponse(sender, "Please enter a valid integer.");
            return true;
        }

        Main.getPlugin().setTeleportCooldown(teleportCooldown);
        Broadcasting.sendSuccessResponse(sender, "Set teleport cooldown to " + teleportCooldown + " seconds.");
        return true;
    }
}
package io.papermc.spawn.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import io.papermc.spawn.Broadcasting;
import io.papermc.spawn.Main;

public class CommandGetSpawnConfig implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("minecraft.op")) {
            Broadcasting.sendPermissionsErrorResponse(sender);
            return true;
        }

        Broadcasting.sendSuccessResponse(sender, "[ Spawn plugin configuration ]");
        Broadcasting.sendMessageResponse(sender, "RTP radius: " + Main.rtpRadius);
        Broadcasting.sendMessageResponse(sender, "Teleport cooldown: " + Main.teleportCooldown);
        return true;
    }
}
package io.papermc.spawn.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import io.papermc.spawn.Broadcasting;
import io.papermc.spawn.Main;

public class CommandRTPRadius implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("minecraft.op")) {
            Broadcasting.sendPermissionsErrorResponse(sender);
            return true;
        }

        if (args.length==0) {
            Broadcasting.sendMessageResponse(sender, "RTP radius is currently configured to " + Main.rtpRadius + " blocks.");
            return true;
        }

        int rtpRadius = 0;

        try {
            rtpRadius = Integer.parseInt(args[0]);
            if (rtpRadius < 0) {
                Broadcasting.sendErrorResponse(sender, "Please enter a valid integer.");
                return true;
            }
        } catch (NumberFormatException e) {
            Broadcasting.sendErrorResponse(sender, "Please enter a valid integer.");
            return true;
        }

        Main.getPlugin().setRtpRadius(rtpRadius);
        Broadcasting.sendSuccessResponse(sender, "Set RTP radius to " + rtpRadius + " blocks.");
        return true;
    }
}
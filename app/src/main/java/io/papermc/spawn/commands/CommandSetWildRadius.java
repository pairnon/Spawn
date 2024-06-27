package io.papermc.spawn.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import io.papermc.spawn.Broadcasting;
import io.papermc.spawn.Main;

public class CommandSetWildRadius implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("minecraft.op")) {
            Broadcasting.sendErrorResponse(sender, "You do not have access to this command.");
            return true;
        }

        if (args.length==0) {
            Broadcasting.sendMessageResponse(sender, "Wild radius is currently configured to " + Main.wildRadius + " blocks.");
            return true;
        }

        int wildRadius = 0;

        try {
            wildRadius = Integer.parseInt(args[0]);
            if (wildRadius < 0) {
                Broadcasting.sendErrorResponse(sender, "Please enter a valid integer.");
                return true;
            }
        } catch (NumberFormatException e) {
            Broadcasting.sendErrorResponse(sender, "Please enter a valid integer.");
            return true;
        }

        Main.getPlugin().setWildRadius(wildRadius);

        Broadcasting.sendSuccessResponse(sender, "Set wild radius to " + wildRadius + " blocks.");


        return true;
    }
    
}

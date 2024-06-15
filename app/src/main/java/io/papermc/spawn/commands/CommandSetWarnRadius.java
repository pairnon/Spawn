package io.papermc.spawn.commands;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import io.papermc.spawn.Broadcasting;
import io.papermc.spawn.Main;

public class CommandSetWarnRadius implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("minecraft.op")) {
            Broadcasting.sendErrorResponse(sender, "You do not have access to this command.");
            return true;
        }

        if (args.length==0) {
            Broadcasting.sendErrorResponse(sender, "You must specify a warning radius in number of blocks from spawn.");
        }

        int warningRadius = 0;

        try {
            warningRadius = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            Broadcasting.sendErrorResponse(sender, "Please enter a number from 1 to 5.");
            return true;
        }

        World world = Bukkit.getWorld("world");

        PersistentDataContainer pdc = world.getPersistentDataContainer();

        pdc.set(new NamespacedKey(Main.getPlugin(), "warningRadius"), PersistentDataType.INTEGER, warningRadius);

        Broadcasting.sendSuccessResponse(sender, "Set warning radius to " + warningRadius + " blocks.");

        return true;
    }
    
}

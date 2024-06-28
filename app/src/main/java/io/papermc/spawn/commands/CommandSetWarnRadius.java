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
            Broadcasting.sendPermissionsErrorResponse(sender);
            return true;
        }

        if (args.length==0) {
            int warningRadius = getWarningRadius();
            Broadcasting.sendMessageResponse(sender, "Warning radius is currently configured to " + warningRadius + " blocks.");
            return true;
        }

        int warningRadius = 0;

        try {
            warningRadius = Integer.parseInt(args[0]);
            if (warningRadius < 0) {
                Broadcasting.sendErrorResponse(sender, "Please enter a valid integer.");
                return true;
            }
        } catch (NumberFormatException e) {
            Broadcasting.sendErrorResponse(sender, "Please enter a valid integer.");
            return true;
        }

        World world = Bukkit.getWorld("world");

        PersistentDataContainer pdc = world.getPersistentDataContainer();

        pdc.set(new NamespacedKey(Main.getPlugin(), "warningRadius"), PersistentDataType.INTEGER, warningRadius);

        Broadcasting.sendSuccessResponse(sender, "Set warning radius to " + warningRadius + " blocks.");

        return true;
    }

    private int getWarningRadius() {
        World world = Bukkit.getWorld("world");
        PersistentDataContainer pdc = world.getPersistentDataContainer();
        if (!pdc.has(new NamespacedKey(Main.getPlugin(), "warningRadius"), PersistentDataType.INTEGER)) {
            return -1;
        }
        return pdc.get(new NamespacedKey(Main.getPlugin(), "warningRadius"), PersistentDataType.INTEGER);    
    }   
}
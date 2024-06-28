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

public class CommandRemoveWarnRadius implements CommandExecutor {
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("minecraft.op")) {
            Broadcasting.sendErrorResponse(sender, "You do not have access to this command.");
            return true;
        }

        World world = Bukkit.getWorld("world");

        PersistentDataContainer pdc = world.getPersistentDataContainer();

        if (!pdc.has(new NamespacedKey(Main.getPlugin(), "warningRadius"), PersistentDataType.INTEGER)) {
            Broadcasting.sendErrorResponse(sender, "The warning radius has not been set.");
            return true;
        }

        int warningRadius = pdc.get(new NamespacedKey(Main.getPlugin(), "warningRadius"), PersistentDataType.INTEGER);
        pdc.remove(new NamespacedKey(Main.getPlugin(), "warningRadius"));
        Broadcasting.sendMessageResponse(sender, "The warning radius of size " + warningRadius + " has been removed.");
        return true;
    }
}
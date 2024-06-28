package io.papermc.spawn.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import io.papermc.spawn.Broadcasting;
import io.papermc.spawn.Main;

public class CommandSpawn implements CommandExecutor {
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            Broadcasting.sendErrorResponse(sender, "You cannot execute this command from the server console.");
            return true;
        }
        
        Player player = (Player) sender;

        World world = Bukkit.getWorld("world");

        PersistentDataContainer worldPdc = world.getPersistentDataContainer();

        if (!worldPdc.has(new NamespacedKey(Main.getPlugin(), "spawncoordx")) || !worldPdc.has(new NamespacedKey(Main.getPlugin(), "spawncoordy")) || !worldPdc.has(new NamespacedKey(Main.getPlugin(), "spawncoordz"))) {
            Location defaultSpawnLocation = world.getSpawnLocation();
            player.teleport(defaultSpawnLocation);
            Broadcasting.sendSuccessResponse(player, "Teleported you to spawn!");
            return true;
        }

        double x = worldPdc.get(new NamespacedKey(Main.getPlugin(), "spawncoordx"), PersistentDataType.DOUBLE);
        double y = worldPdc.get(new NamespacedKey(Main.getPlugin(), "spawncoordy"), PersistentDataType.DOUBLE);
        double z = worldPdc.get(new NamespacedKey(Main.getPlugin(), "spawncoordz"), PersistentDataType.DOUBLE);

        Location loc = new Location(world, x, y, z);

        player.teleport(loc);
        Broadcasting.sendSuccessResponse(player, "Teleported you to spawn!");

        return true;
    }
    
}

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
            Broadcasting.sendConsoleErrorResponse(sender);
            return true;
        }
        
        Player player = (Player) sender;

        PersistentDataContainer playerPdc = player.getPersistentDataContainer();

        if (playerPdc.has(new NamespacedKey(Main.getPlugin(), "teleportcooldown"))) {
            int teleportCooldown = playerPdc.get(new NamespacedKey(Main.getPlugin(), "teleportcooldown"), PersistentDataType.INTEGER);
            if (teleportCooldown != 0) {
                Broadcasting.sendErrorResponse(player, "You must wait " + teleportCooldown + " seconds before teleporting.");
                return true;
            }
        }

        World world = Bukkit.getWorld("world");

        PersistentDataContainer worldPdc = world.getPersistentDataContainer();

        if (!worldPdc.has(new NamespacedKey(Main.getPlugin(), "spawncoordx")) || !worldPdc.has(new NamespacedKey(Main.getPlugin(), "spawncoordy")) || !worldPdc.has(new NamespacedKey(Main.getPlugin(), "spawncoordz"))) {
            Location defaultSpawnLocation = world.getSpawnLocation();
            player.teleport(defaultSpawnLocation);
            Broadcasting.sendSuccessResponse(player, "Teleported you to spawn!");
            return true;
        }

        double spawnCoordX = worldPdc.get(new NamespacedKey(Main.getPlugin(), "spawncoordx"), PersistentDataType.DOUBLE);
        double spawnCoordY = worldPdc.get(new NamespacedKey(Main.getPlugin(), "spawncoordy"), PersistentDataType.DOUBLE);
        double spawnCoordZ = worldPdc.get(new NamespacedKey(Main.getPlugin(), "spawncoordz"), PersistentDataType.DOUBLE);

        Location loc = new Location(world, spawnCoordX, spawnCoordY, spawnCoordZ);
        player.teleport(loc);
        Broadcasting.sendSuccessResponse(player, "Teleported you to spawn!");
        playerPdc.set(new NamespacedKey(Main.getPlugin(), "teleportcooldown"), PersistentDataType.INTEGER, Main.teleportCooldown);
        return true;
    }   
}
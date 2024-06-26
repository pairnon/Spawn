package io.papermc.spawn.listeners;

import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import io.papermc.spawn.Broadcasting;
import io.papermc.spawn.Main;
import io.papermc.spawn.classes.Message;

public class PlayerMoveListener implements Listener {
    
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {

        Player player = event.getPlayer();

        double distanceMoved = event.getFrom().distance(event.getTo());
        if (distanceMoved < 0.1) { return; }

        World world = player.getWorld();

        if (world == null || !world.getName().equals("world")) { return; }
        
        PersistentDataContainer pdc = world.getPersistentDataContainer();
        
        if (!pdc.has(new NamespacedKey(Main.getPlugin(), "warningRadius"), PersistentDataType.INTEGER)) {
            return;
        }

        int warningRadius = pdc.get(new NamespacedKey(Main.getPlugin(), "warningRadius"), PersistentDataType.INTEGER);
        
        Location playerLoc = player.getLocation();
        Location spawnLoc = world.getSpawnLocation();

        double distanceFromSpawn = getDistanceIgnoringY(playerLoc, spawnLoc);

        if (Math.abs(distanceFromSpawn - warningRadius) < 1) {
            Broadcasting.pushToMessageQueue(new Message(player, "You are now " + warningRadius + " blocks from spawn.", 0));
            Broadcasting.pushToMessageQueue(new Message(player, "You have reached the spawn region boundary!", 2));
        }

    }

    private double getDistanceIgnoringY(Location loc1, Location loc2) {
        double xDiff = Math.abs(loc1.getX() - loc2.getX());
        double zDiff = Math.abs(loc1.getZ() - loc2.getZ());
        return Math.sqrt(xDiff * xDiff + zDiff * zDiff);
    }
}
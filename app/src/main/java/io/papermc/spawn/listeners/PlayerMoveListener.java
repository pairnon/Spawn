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

public class PlayerMoveListener implements Listener {
    
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {

        Player player = event.getPlayer();
        World world = player.getWorld();

        if (world == null || !world.getName().equals("world")) { return; }
        
        PersistentDataContainer pdc = world.getPersistentDataContainer();
        
        if (!pdc.has(new NamespacedKey(Main.getPlugin(), "warningRadius"), PersistentDataType.INTEGER)) {
            return;
        }

        int warningRadius = pdc.get(new NamespacedKey(Main.getPlugin(), "warningRadius"), PersistentDataType.INTEGER);
        
        Location playerLoc = player.getLocation();
        Location spawnLoc = world.getSpawnLocation();

        double distanceFromSpawn = playerLoc.distance(spawnLoc);

        if (Math.abs(distanceFromSpawn - warningRadius) < 1) {
            Broadcasting.sendAlert(player, "You have left the spawn region!");
        }

    }
}

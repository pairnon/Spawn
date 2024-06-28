package io.papermc.spawn;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Cooldown {
    
    public static void startCooldownLoop() {
        new BukkitRunnable() {
            @Override
            public void run() {

                for (Player player : Bukkit.getOnlinePlayers()) {

                    PersistentDataContainer playerPdc = player.getPersistentDataContainer();
                    if (playerPdc.has(new NamespacedKey(Main.getPlugin(), "teleportcooldown"))) {
                        int teleportCooldown = playerPdc.get(new NamespacedKey(Main.getPlugin(), "teleportcooldown"), PersistentDataType.INTEGER);
                        if (teleportCooldown > 0) {
                            int newTeleportCooldown = teleportCooldown - 1;
                            playerPdc.set(new NamespacedKey(Main.getPlugin(), "teleportcooldown"), PersistentDataType.INTEGER, newTeleportCooldown);
                        }
                    }
                }

            }
        }.runTaskTimer(JavaPlugin.getPlugin(Main.class), 0L, 20L);
    }

}

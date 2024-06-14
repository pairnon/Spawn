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


        if (sender instanceof Player) {
            Player player = (Player) sender;

            World world = Bukkit.getWorld("world");

            PersistentDataContainer pdc = world.getPersistentDataContainer();

            double x = pdc.get(new NamespacedKey(Main.getPlugin(), "spawncoordx"), PersistentDataType.DOUBLE);
            double y = pdc.get(new NamespacedKey(Main.getPlugin(), "spawncoordy"), PersistentDataType.DOUBLE);
            double z = pdc.get(new NamespacedKey(Main.getPlugin(), "spawncoordz"), PersistentDataType.DOUBLE);

            Location loc = new Location(world, x, y, z);

            player.teleport(loc);
            Broadcasting.sendSuccessResponse(player, "Teleported you to spawn!");

        }

        return true;
    }
    
}

package io.papermc.spawn.commands;

import org.bukkit.GameRule;
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

public class CommandSetSpawn implements CommandExecutor {
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("minecraft.op")) {
            Broadcasting.sendPermissionsErrorResponse(sender);
            return true;
        }

        if (!(sender instanceof Player)) {
            Broadcasting.sendConsoleErrorResponse(sender);
            return true;
        }

        Player player = (Player) sender;
        Location loc = player.getLocation();

        World world = player.getWorld();

        if (world == null || !world.getName().equals("world")) {
            Broadcasting.sendErrorResponse(player, "You must be in the overworld to execute this command.");
            return true;
        }

        PersistentDataContainer pdc = world.getPersistentDataContainer();

        pdc.set(new NamespacedKey(Main.getPlugin(), "spawncoordx"), PersistentDataType.DOUBLE, loc.getX());
        pdc.set(new NamespacedKey(Main.getPlugin(), "spawncoordy"), PersistentDataType.DOUBLE, loc.getY());
        pdc.set(new NamespacedKey(Main.getPlugin(), "spawncoordz"), PersistentDataType.DOUBLE, loc.getZ());

        world.setSpawnLocation(loc);

        int worldSpawnRadius = world.getGameRuleValue(GameRule.SPAWN_RADIUS);
        if (worldSpawnRadius != 0) {
            world.setGameRule(GameRule.SPAWN_RADIUS, 0);
            Broadcasting.sendMessageResponse(player, "Set spawn radius to 0 from " + worldSpawnRadius + ".");
        }

        String coords = (int)loc.getX() + ", " + (int)loc.getY() + ", " + (int)loc.getZ();
        Broadcasting.sendSuccessResponse(player, "Set server spawn to " + coords + ".");
        return true;
    }
}
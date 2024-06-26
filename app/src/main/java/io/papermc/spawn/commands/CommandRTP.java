package io.papermc.spawn.commands;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import io.papermc.spawn.Broadcasting;
import io.papermc.spawn.Main;

public class CommandRTP implements CommandExecutor {

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

        World world = player.getWorld();

        if (!world.getName().equals("world")) {
            Broadcasting.sendErrorResponse(player, "You must be in the overworld to execute this command.");
            return true;
        }

        Location location = getSafeRandomLocation(world);
        if (location == null) {
            Broadcasting.sendErrorResponse(player, "Could not find a safe location.");
            return true;
        }
        
        location.setY(location.getY() + 1); // prevent teleportation into the ground
        player.teleport(location);
        Broadcasting.sendSuccessResponse(player, "Teleported you to a random location.");
        resetPlayerTeleportCooldown(playerPdc);
        return true;
    }

    private void resetPlayerTeleportCooldown(PersistentDataContainer p) {
        p.set(new NamespacedKey(Main.getPlugin(), "teleportcooldown"), PersistentDataType.INTEGER, Main.teleportCooldown);
    }

    private Location getSafeRandomLocation(World world) {
        Location possibleLocation = getRandomLocation(world);
        int counter = 0;
        do {
            if (isSafeLocation(possibleLocation)) {
                return possibleLocation;
            }
            possibleLocation = getRandomLocation(world);
            counter++;
        } while (counter < Main.RTP_MAX_TRIES);
        return null;
    }

    private Location getRandomLocation(World world) {
        Random rand = new Random();
        int randomX = rand.nextInt((Main.rtpRadius * 2) + 1) - Main.rtpRadius;
        int randomZ = rand.nextInt((Main.rtpRadius * 2) + 1) - Main.rtpRadius;
        
        Location randomLocation = new Location(world, randomX, 0, randomZ);
        Location surfaceLocation = getSurfaceLocation(randomLocation);
        return surfaceLocation;
    }

    private boolean isSafeLocation(Location location) {
        for (Material material : Main.UNSAFE_MATERIALS) {
            if (location.getBlock().getType().equals(material)) { return false; }
        }
        return true;
    }

    private Location getSurfaceLocation(Location location) {
        int yCounter = Main.MAX_BUILD_HEIGHT;
        Location checkLocation = location;
        while (yCounter > -63) {
            checkLocation.setY(yCounter);
            Block checkBlock = checkLocation.getBlock();
            if (!checkBlock.getType().equals(Material.AIR)) {
                return checkLocation;
            }
            yCounter--;
        }
        return null;
    }   
}
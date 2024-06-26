package io.papermc.spawn.commands;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.papermc.spawn.Broadcasting;
import io.papermc.spawn.Main;

public class CommandWild implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        if (!(sender instanceof Player)) {
            Broadcasting.sendErrorResponse(sender, "You cannot execute this command from the server console.");
            return true;
        }

        Player player = (Player) sender;

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
        location.setY(location.getY() + 1);
        player.teleport(location);
        
        Broadcasting.sendSuccessResponse(player, "Teleported you to a random location.");
        return true;

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
        } while (counter < Main.WILD_COMMAND_MAX_TRIES);
        return null;
    }

    private Location getRandomLocation(World world) {
        Random rand = new Random();
        int randomX = rand.nextInt(201) - 100;
        int randomZ = rand.nextInt(201) - 100;
        
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

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

        Random rand = new Random();
        int randomX = rand.nextInt(201) - 100;
        int randomZ = rand.nextInt(201) - 100;
        
        Location randomLocation = new Location(world, randomX, 0, randomZ);
        Location surfaceLocation = getSurfaceLocation(randomLocation);
        surfaceLocation.setY(surfaceLocation.getY() + 1);
        player.teleport(surfaceLocation);
        
        Broadcasting.sendSuccessResponse(player, "Teleporting you...");

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

package io.papermc.spawn.commands;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.papermc.spawn.Broadcasting;

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

        Broadcasting.sendSuccessResponse(player, "Teleporting you...");

        return true;
    }
    
}

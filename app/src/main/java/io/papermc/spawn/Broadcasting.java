package io.papermc.spawn;

import org.bukkit.command.CommandSender;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class Broadcasting {

    public static void sendMessageResponse(CommandSender c, String message) {
        c.sendMessage(Component.text(message, NamedTextColor.GOLD));
    }

    public static void sendSuccessResponse(CommandSender c, String message) {
        c.sendMessage(Component.text(message, NamedTextColor.GREEN));
    }
    
    public static void sendErrorResponse(CommandSender c, String message) {
        c.sendMessage(Component.text(message, NamedTextColor.RED));
    }

}
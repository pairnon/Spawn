package io.papermc.spawn;

import java.util.List;
import java.util.ArrayList;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class Broadcasting {

    private static ArrayList<Alert> alerts;
    private static ArrayList<Alert> tempQueue;

    public static void pushToAlertQueue(Player p, String message) {
        Alert alert = new Alert(p, message);
        alerts.add(alert);
    }

    public static void startAlertLoop() {
        new BukkitRunnable() {
            @Override
            public void run() {
                tempQueue = removeDuplicates(alerts);
                for (Alert alert : tempQueue) {
                    alert.send();
                }
                alerts.clear();
                tempQueue.clear();
            }
        }.runTaskTimer(JavaPlugin.getPlugin(Main.class), 0L, 20L);
    }

    public static void initializeAlertQueue() {
        alerts = new ArrayList<Alert>();
        tempQueue = new ArrayList<Alert>();
    }

    public static void sendAlert(Player p, String message) {
        p.sendMessage(Component.text("[ALERT] " + message, NamedTextColor.RED));
    }

    public static void sendMessageResponse(CommandSender c, String message) {
        c.sendMessage(Component.text(message, NamedTextColor.GOLD));
    }

    public static void sendSuccessResponse(CommandSender c, String message) {
        c.sendMessage(Component.text(message, NamedTextColor.GREEN));
    }
    
    public static void sendErrorResponse(CommandSender c, String message) {
        c.sendMessage(Component.text(message, NamedTextColor.RED));
    }

    public static <T> ArrayList<T> removeDuplicates(List<T> list) {
        ArrayList<T> newList = new ArrayList<>();
        for (T element : list) {
            if (!newList.contains(element)) {
                newList.add(element);
            }
        }
        return newList;
    }
}
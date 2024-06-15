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

    private static ArrayList<Message> messages;
    private static ArrayList<Message> tempMessageQueue;

    public static void pushToMessageQueue(Player p, String message) {
        Message m = new Message(p, message);
        messages.add(m);
    }

    public static void startMessageLoop() {
        new BukkitRunnable() {
            @Override
            public void run() {
                tempMessageQueue = removeDuplicates(messages);
                for (Message message : tempMessageQueue) {
                    message.send();
                }
                messages.clear();
                tempMessageQueue.clear();
            }
        }.runTaskTimer(JavaPlugin.getPlugin(Main.class), 0L, 20L);
    }

    public static void initializeMessageQueue() {
        messages = new ArrayList<Message>();
        tempMessageQueue = new ArrayList<Message>();
        startMessageLoop();
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
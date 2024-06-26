package io.papermc.spawn;

import java.util.List;
import java.util.ArrayList;

import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import io.papermc.spawn.classes.Message;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class Broadcasting {

    public static final Sound MESSAGE_SOUND = Sound.BLOCK_NOTE_BLOCK_SNARE;

    private static ArrayList<Message> messages;
    private static ArrayList<Message> tempMessageQueue;

    public static void pushToMessageQueue(Message m) {
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
        }.runTaskTimer(JavaPlugin.getPlugin(Main.class), 0L, 40L);
    }

    public static void initializeMessageQueue() {
        messages = new ArrayList<Message>();
        tempMessageQueue = new ArrayList<Message>();
        startMessageLoop();
    }

    public static void sendMessageResponse(CommandSender c, String message) {
        c.sendMessage(Component.text(message, NamedTextColor.GOLD));
    }

    public static void sendSuccessResponse(CommandSender c, String message) {
        c.sendMessage(Component.text(message, NamedTextColor.GREEN));
    }
    
    public static void sendConsoleErrorResponse(CommandSender c) {
        sendErrorResponse(c, "You cannot execute this command from the server console.");
    }

    public static void sendPermissionsErrorResponse(CommandSender c) {
        sendErrorResponse(c, "You do not have access to this command.");
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
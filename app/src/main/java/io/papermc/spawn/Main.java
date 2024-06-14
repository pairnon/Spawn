package io.papermc.spawn;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import io.papermc.spawn.commands.CommandSetSpawn;
import io.papermc.spawn.commands.CommandSpawn;

public class Main extends JavaPlugin implements Listener {

    private static Main plugin;

    public static Main getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        plugin = this;

        this.getCommand("setspawn").setExecutor(new CommandSetSpawn());
        this.getCommand("spawn").setExecutor(new CommandSpawn());
    }
    
}

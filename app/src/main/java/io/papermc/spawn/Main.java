package io.papermc.spawn;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import io.papermc.spawn.commands.CommandRemoveWarnRadius;
import io.papermc.spawn.commands.CommandSetSpawn;
import io.papermc.spawn.commands.CommandSetWarnRadius;
import io.papermc.spawn.commands.CommandSetWildRadius;
import io.papermc.spawn.commands.CommandSpawn;
import io.papermc.spawn.commands.CommandWild;
import io.papermc.spawn.listeners.PlayerMoveListener;

public class Main extends JavaPlugin implements Listener {

    private static Main plugin;

    public static final int MAX_BUILD_HEIGHT = 319;
    public static final int WILD_COMMAND_MAX_TRIES = 50;
    public static final Material[] UNSAFE_MATERIALS = {Material.WATER, Material.LAVA};

    public static int wildRadius = 0;
    public static int teleportCooldown = 5;
    
    private FileConfiguration config;

    public static Main getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        plugin = this;

        this.saveDefaultConfig();
        config = this.getConfig();
        wildRadius = config.getInt("rtp-radius");
        
        Broadcasting.initializeMessageQueue();

        Cooldown.startCooldownLoop();

        getServer().getPluginManager().registerEvents(new PlayerMoveListener(), this);

        this.getCommand("setspawn").setExecutor(new CommandSetSpawn());
        this.getCommand("setwarnradius").setExecutor(new CommandSetWarnRadius());
        this.getCommand("removewarnradius").setExecutor(new CommandRemoveWarnRadius());
        this.getCommand("spawn").setExecutor(new CommandSpawn());
        this.getCommand("wild").setExecutor(new CommandWild());
        this.getCommand("setwildradius").setExecutor(new CommandSetWildRadius());
    }

    public void setWildRadius(int blocks) {
        config.set("rtp-radius", blocks);
        wildRadius = config.getInt("rtp-radius");
    }   
}
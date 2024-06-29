package io.papermc.spawn;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import io.papermc.spawn.commands.CommandSpawnConfig;
import io.papermc.spawn.commands.CommandRemoveWarnRadius;
import io.papermc.spawn.commands.CommandSetSpawn;
import io.papermc.spawn.commands.CommandTeleportCooldown;
import io.papermc.spawn.commands.CommandWarnRadius;
import io.papermc.spawn.commands.CommandRTPRadius;
import io.papermc.spawn.commands.CommandSpawn;
import io.papermc.spawn.commands.CommandRTP;
import io.papermc.spawn.listeners.PlayerMoveListener;

public class Main extends JavaPlugin implements Listener {

    private static Main plugin;

    public static final int MAX_BUILD_HEIGHT = 319;
    public static final int RTP_MAX_TRIES = 50;
    public static final Material[] UNSAFE_MATERIALS = {Material.WATER, Material.LAVA};

    public static int rtpRadius;
    public static int teleportCooldown;
    
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
        rtpRadius = config.getInt("rtp-radius");
        teleportCooldown = config.getInt("tp-cooldown");
        
        Broadcasting.initializeMessageQueue();

        Cooldown.startCooldownLoop();

        getServer().getPluginManager().registerEvents(new PlayerMoveListener(), this);

        this.getCommand("setspawn").setExecutor(new CommandSetSpawn());
        this.getCommand("warnradius").setExecutor(new CommandWarnRadius());
        this.getCommand("removewarnradius").setExecutor(new CommandRemoveWarnRadius());
        this.getCommand("spawn").setExecutor(new CommandSpawn());
        this.getCommand("rtp").setExecutor(new CommandRTP());
        this.getCommand("rtpradius").setExecutor(new CommandRTPRadius());
        this.getCommand("teleportcooldown").setExecutor(new CommandTeleportCooldown());
        this.getCommand("spawnconfig").setExecutor(new CommandSpawnConfig());
    }

    public void setRtpRadius(int blocks) {
        config.set("rtp-radius", blocks);
        this.saveConfig();
        rtpRadius = config.getInt("rtp-radius");
    }

    public void setTeleportCooldown(int seconds) {
        config.set("tp-cooldown", seconds);
        this.saveConfig();
        teleportCooldown = config.getInt("tp-cooldown");
    }
}
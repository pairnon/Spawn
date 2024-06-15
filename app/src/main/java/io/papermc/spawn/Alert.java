package io.papermc.spawn;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

@SuppressWarnings("deprecation")
public class Alert {

    private String playerDisplayName;
    private String message;

    public String getPlayerDisplayName() {
        return playerDisplayName;
    }

    public void setPlayerDisplayName(String playerDisplayName) {
        this.playerDisplayName = playerDisplayName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Alert)) {
            return false;
        }
        Alert alert = (Alert) obj;
        return (playerDisplayName.equals(alert.getPlayerDisplayName()) && message.equals(alert.getMessage()));
    }

    public Alert(Player player, String message) {
        playerDisplayName = player.getDisplayName();
        this.message = message;
    }

    public void send() {
        Player player = Bukkit.getPlayer(playerDisplayName);
        if (player == null) { return; }
        player.sendMessage(Component.text("[ALERT] " + message, NamedTextColor.RED));
    }
    
}

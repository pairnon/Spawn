package io.papermc.spawn;

import org.bukkit.entity.Player;

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

    public Alert(Player player, String message) {
        playerDisplayName = player.getDisplayName();
        this.message = message;
    }
    
}

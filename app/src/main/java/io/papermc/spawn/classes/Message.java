package io.papermc.spawn.classes;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

@SuppressWarnings("deprecation")
public class Message {

    private String playerDisplayName;
    private String text;

    public String getPlayerDisplayName() {
        return playerDisplayName;
    }

    public void setPlayerDisplayName(String playerDisplayName) {
        this.playerDisplayName = playerDisplayName;
    }

    public String getText() {
        return text;
    }

    public void setText(String message) {
        this.text = message;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Message)) {
            return false;
        }
        Message message = (Message) obj;
        return (playerDisplayName.equals(message.getPlayerDisplayName()) && text.equals(message.getText()));
    }

    public Message(Player player, String message) {
        playerDisplayName = player.getDisplayName();
        this.text = message;
    }

    public void send() {
        Player player = Bukkit.getPlayer(playerDisplayName);
        if (player == null) { return; }
        player.sendMessage(Component.text("[MESSAGE] " + text, NamedTextColor.GOLD));
    }
    
}

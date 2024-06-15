package io.papermc.spawn.classes;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import io.papermc.spawn.Broadcasting;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

@SuppressWarnings("deprecation")
public class Message {

    private String playerDisplayName;
    private String text;
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

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
        type = 0;
    }

    public Message(Player player, String message, int type) {
        playerDisplayName = player.getDisplayName();
        this.text = message;
        this.type = type;
    }

    public void send() {
        Player player = Bukkit.getPlayer(playerDisplayName);
        if (player == null) { return; }
        player.playSound(player.getLocation(), Broadcasting.MESSAGE_SOUND, 1.0F, 1.0F);
        if (type == 1) {
            player.sendMessage(Component.text(text, NamedTextColor.GREEN));
        } else if (type == 2) {
            player.sendMessage(Component.text(text, NamedTextColor.RED));
        } else {
            player.sendMessage(Component.text(text, NamedTextColor.GOLD));
        }
    }
    
}

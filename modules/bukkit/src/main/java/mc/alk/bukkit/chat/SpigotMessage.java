package mc.alk.bukkit.chat;

import mc.alk.bukkit.BukkitPlayer;
import mc.alk.mc.MCPlayer;
import mc.alk.mc.chat.Message;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class SpigotMessage extends Message {

    public SpigotMessage() {
        super();
    }

    public SpigotMessage(String message) {
        super(message);
    }

    @Override
    public void sendMessage(MCPlayer player) {
        TextComponent textComponent = new TextComponent();
        textComponent.setText(message);
        if (hoverAction != null && hoverMessage != null) {
            textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.valueOf(hoverAction.name()), new BaseComponent[] {new TextComponent(hoverMessage)}));
        }

        if (clickAction != null && clickValue != null) {
            textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.valueOf(clickAction.name()), clickValue));
        }

        BukkitPlayer bukkitPlayer = (BukkitPlayer) player;
        bukkitPlayer.getHandle().spigot().sendMessage(textComponent);
    }
}

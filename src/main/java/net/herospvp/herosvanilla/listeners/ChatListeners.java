package net.herospvp.herosvanilla.listeners;

import net.herospvp.herosvanilla.HerosVanilla;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListeners implements Listener {
    private HerosVanilla plugin;

    public ChatListeners(HerosVanilla plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void on(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        event.setFormat(ChatColor.translateAlternateColorCodes('&', plugin.getVaultChat()
                .getGroupPrefix(
                        event.getPlayer().getWorld(),
                        plugin.getVaultChat().getPrimaryGroup(event.getPlayer()))
                )+ event.getPlayer().getName() + ChatColor.GRAY + ": " + (player.hasPermission("chat.color") ?
                    ChatColor.translateAlternateColorCodes('&', event.getMessage()) : event.getMessage()));
    }
}

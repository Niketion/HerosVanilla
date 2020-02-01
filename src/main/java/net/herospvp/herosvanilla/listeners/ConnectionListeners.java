package net.herospvp.herosvanilla.listeners;

import net.herospvp.herosvanilla.HerosVanilla;
import net.herospvp.herosvanilla.managers.MembersManager;
import net.herospvp.herosvanilla.structure.Member;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class ConnectionListeners implements Listener {
    private HerosVanilla plugin;

    public ConnectionListeners(HerosVanilla plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void on(PlayerJoinEvent event) {
        event.setJoinMessage(null);
        if (!event.getPlayer().hasPlayedBefore()) {
            event.getPlayer().getInventory().addItem(new ItemStack(Material.OAK_BOAT));
        }
        plugin.getMembersManager().loadMember(event.getPlayer());
    }

    @EventHandler
    public void on(PlayerQuitEvent event) {
        event.setQuitMessage(null);
        MembersManager members = plugin.getMembersManager();
        Player player = event.getPlayer();
        members.getMembers().removeIf(member -> member.getName().equalsIgnoreCase(player.getName()) && !member.hasParty());
    }
}

package net.herospvp.herosvanilla.listeners;

import net.herospvp.herosvanilla.HerosVanilla;
import net.herospvp.herosvanilla.managers.MembersManager;
import net.herospvp.herosvanilla.structure.Member;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class DeathListeners implements Listener {
    private HerosVanilla plugin;

    public DeathListeners(HerosVanilla plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void on(PlayerDeathEvent event) {
        Member member = plugin.getMembersManager().getMember(event.getEntity().getName());

        if (member.hasHome()) {
            event.getEntity().teleport(member.getHome());
        } else {
            event.getEntity().getInventory().addItem(new ItemStack(Material.OAK_BOAT));
        }
    }
}

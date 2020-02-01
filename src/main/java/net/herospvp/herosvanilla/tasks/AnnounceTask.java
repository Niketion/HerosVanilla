package net.herospvp.herosvanilla.tasks;

import net.herospvp.herosvanilla.HerosVanilla;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class AnnounceTask extends BukkitRunnable {
    private HerosVanilla plugin;

    public AnnounceTask(HerosVanilla plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        Bukkit.broadcastMessage(plugin.getAnnounceMessage()[plugin.getAnnounce()]);
        plugin.setAnnounce(plugin.getAnnounce()+1);
        if (plugin.getAnnounce() == 2) {
            plugin.setAnnounce(0);
        }
    }
}

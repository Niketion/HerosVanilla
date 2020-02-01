package net.herospvp.herosvanilla.tasks;

import net.herospvp.herosvanilla.HerosVanilla;
import net.herospvp.herosvanilla.managers.MembersManager;
import net.herospvp.herosvanilla.structure.Member;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class MoveTask extends BukkitRunnable {
    private HerosVanilla plugin;

    public MoveTask(HerosVanilla plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for (Player players : Bukkit.getOnlinePlayers()) {
            if (plugin.getMembersManager() == null || plugin.getMembersManager().getMember(players.getName()) == null) {
                continue;
            }
            Member member = plugin.getMembersManager().getMember(players.getName());

            Member ownerChunk = MembersManager.getClaim(plugin, players.getLocation().getChunk());
            if (ownerChunk == null && member.getLastCheckedLocation() == null) {
                member.setLastCheckedLocation(null);
                continue;
            }

            if (ownerChunk == null) {
                players.sendMessage(ChatColor.YELLOW + "Sei in territorio libero.");
                member.setLastCheckedLocation(null);
                continue;
            }

            if (member.getLastCheckedLocation() == null || ownerChunk != member.getLastCheckedLocation()) {
                players.sendMessage(ChatColor.RED + "Sei nel territorio di " + ownerChunk.getName()
                        + (ownerChunk.hasParty() ? " (" + ownerChunk.getParty().getName() + ")." : "."));
                member.setLastCheckedLocation(ownerChunk);
            }
        }
    }
}

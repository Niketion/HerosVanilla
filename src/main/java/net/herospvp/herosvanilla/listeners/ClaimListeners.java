package net.herospvp.herosvanilla.listeners;

import net.herospvp.herosvanilla.HerosVanilla;
import net.herospvp.herosvanilla.managers.MembersManager;
import net.herospvp.herosvanilla.structure.Member;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClaimListeners implements Listener {
    private HerosVanilla plugin;

    public ClaimListeners(HerosVanilla plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void on(BlockPlaceEvent event) {
        boolean canBuild = canBuild(event.getPlayer(), event.getBlockPlaced().getLocation());
        if (!canBuild) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.RED + "Non puoi costruire in questa zona. ");
            return;
        }
        Player player = event.getPlayer();
        Chunk chunk = event.getBlock().getChunk();

        if (event.getBlockPlaced().getType() == Material.GOLD_BLOCK) {
            if (!plugin.getConfirm().containsKey(player.getName())) {
                player.sendMessage(ChatColor.YELLOW +"Esegui /claim per confermare il claim del chunk");
                plugin.getConfirm().put(player.getName(), Collections.singletonList(chunk));
            }
        } else if (event.getBlockPlaced().getType() == Material.DIAMOND_BLOCK) {
            if (!plugin.getConfirm().containsKey(player.getName())) {
                player.sendMessage(ChatColor.YELLOW +"Esegui /claim per confermare il claim del chunk");
                List<Chunk> list = new ArrayList<>();
                
                World world = chunk.getWorld();
                int x = chunk.getX();
                int z = chunk.getZ();
                
                list.add(chunk);
                list.add(world.getChunkAt(x-1, z));
                list.add(world.getChunkAt(x+1, z));
                list.add(world.getChunkAt(x, z+1));
                list.add(world.getChunkAt(x, z-1));
                list.add(world.getChunkAt(x-1, z-1));
                list.add(world.getChunkAt(x-1, z+1));
                list.add(world.getChunkAt(x+1, z+1));
                list.add(world.getChunkAt(x+1, z-1));
                plugin.getConfirm().put(player.getName(), list);
            }
        }
    }

    @EventHandler
    public void on(BlockBreakEvent event) {
        if (!canBuild(event.getPlayer(), event.getBlock().getLocation())) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.RED + "Non puoi costruire in questa zona. ");
        }
    }


    @EventHandler
    public void on(PlayerInteractEvent event) {
        if (event.getClickedBlock() == null) {
            return;
        }

        if (!canBuild(event.getPlayer(), event.getClickedBlock().getLocation())) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.RED + "Non puoi farlo in questa zona. ");
        }
    }

    private boolean canBuild(Player player, Location location) {
        Member playerMember = plugin.getMembersManager().getMember(player.getName());
        Member member = MembersManager.getClaim(plugin, location.getChunk());
        if (member == playerMember) {
            return true;
        }

        if (member != null) {
            return member.hasParty() && playerMember.hasParty() && member.getParty() == playerMember.getParty();
        }
        return true;
    }
}

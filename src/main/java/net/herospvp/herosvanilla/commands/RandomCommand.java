package net.herospvp.herosvanilla.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RandomCommand extends CommandHandler {

    public RandomCommand(JavaPlugin plugin) {
        super(plugin, null, "random", true, Collections.singletonList("/random"), false);
    }

    @Override
    public boolean command(CommandSender sender, String[] args) {
        if (sender.hasPermission("random.usato")) {
            sender.sendMessage(ChatColor.RED + "Non puoi riutilizzare questo comando.");
            return true;
        }
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + sender.getName() + " add random.usato");

        Player player = ((Player) sender);
        Bukkit.getScheduler().runTaskAsynchronously(getPlugin(), () -> {
            boolean found = true;
            player.sendMessage(ChatColor.YELLOW + "Cercando un posto adatto...");
            while (found) {
                int x = new Random().nextInt(20000);
                int z = new Random().nextInt(20000);

                Location location = new Location(player.getWorld(), x, 30, z);
                if (!location.getBlock().getBiome().name().contains("OCEAN") && !location.getWorld()
                        .getHighestBlockAt(location).getType().name().contains("WATER")) {
                    found = false;
                    player.sendMessage(ChatColor.GREEN + "Trovato! Teletrasporto...");
                    Bukkit.getScheduler().runTask(getPlugin(), () -> {
                        player.teleport(location.getWorld().getHighestBlockAt(location).getLocation().add(0, 2, 0));
                    });
                }
            }
        });
        return true;
    }

    @Override
    public List<String> tabComplete(String[] args) {
        return null;
    }
}

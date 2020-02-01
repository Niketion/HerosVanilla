package net.herospvp.herosvanilla.commands;

import net.herospvp.herosvanilla.HerosVanilla;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.List;

public class ClaimCommand extends CommandHandler {
    private HerosVanilla plugin;

    public ClaimCommand(HerosVanilla plugin) {
        super(plugin, null, "claim", true, Collections.singletonList("/claim"), false);
        this.plugin = plugin;
    }

    @Override
    public boolean command(CommandSender commandSender, String[] strings) {
        String name = commandSender.getName();

        if (plugin.getConfirm().containsKey(name)) {
            for (Chunk chunk : plugin.getConfirm().get(name)) {
                plugin.getMembersManager().getMember(name).claim(plugin, chunk);
            }
            plugin.getConfirm().remove(name);
            commandSender.sendMessage(ChatColor.GREEN + "Zona claimata con successo.");
            return true;
        }
        commandSender.sendMessage(ChatColor.RED + "Non hai piazzato nessun blocco di claim.");
        return true;
    }

    @Override
    public List<String> tabComplete(String[] strings) {
        return null;
    }
}

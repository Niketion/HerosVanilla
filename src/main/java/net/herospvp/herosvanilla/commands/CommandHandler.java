package net.herospvp.herosvanilla.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public abstract class CommandHandler implements CommandExecutor, TabCompleter {
    private boolean onlyPlayer;
    private List<String> usage;
    private String permission;
    private JavaPlugin plugin;

    public CommandHandler(JavaPlugin plugin, String permission, String command, boolean onlyPlayer, List<String> usage,
                          boolean tabCompleteCustom) {
        this.onlyPlayer = onlyPlayer;
        this.usage = usage;
        if (permission != null) {
            this.permission = "herospvp." + permission;
        }
        this.plugin = plugin;

        plugin.getCommand(command).setExecutor(this);
        if (!tabCompleteCustom) {
            plugin.getCommand(command).setTabCompleter(this);
        }
    }

    public abstract boolean command(CommandSender sender, String[] args);

    public abstract List<String> tabComplete(String[] args);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (onlyPlayer) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("Comando eseguibile solo dai player :(");
                return false;
            }
        }

        if (permission != null && !sender.hasPermission(permission)) {
            if (permission.contains("home")) {
                sender.sendMessage(ChatColor.RED + "Permesso negato! Per ottenere l'accesso alla home acquista il pacchetto VIP");
            }
            return false;
        }

        if (!command(sender, args)) {
            for (String s : usage) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', s));
            }
            return false;
        }

        return true;
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> list = new ArrayList<>();
        Bukkit.getOnlinePlayers().forEach((players) -> {
            list.add(players.getName());
        });
        return list;

    }
}

package net.herospvp.herosvanilla.commands;

import net.herospvp.herosvanilla.HerosVanilla;
import net.herospvp.herosvanilla.structure.Member;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.List;

public class HomeCommand extends CommandHandler {
    private HerosVanilla plugin;
    public HomeCommand(HerosVanilla plugin) {
        super(plugin, null, "home", true, Collections.singletonList("home"), false);
        this.plugin = plugin;
    }

    @Override
    public boolean command(CommandSender sender, String[] args) {
        Member member = this.plugin.getMembersManager().getMember(sender.getName());
        if (!member.hasHome()) {
            sender.sendMessage(ChatColor.RED + "Non hai nessuna home...");
            return true;
        }

        ((Player) sender).teleport(member.getHome());
        return true;
    }

    @Override
    public List<String> tabComplete(String[] args) {
        return null;
    }
}

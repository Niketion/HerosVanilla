package net.herospvp.herosvanilla.commands;

import net.herospvp.herosvanilla.HerosVanilla;
import net.herospvp.herosvanilla.structure.Member;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.List;

public class SetHomeCommand extends CommandHandler {
    private HerosVanilla plugin;

    public SetHomeCommand(HerosVanilla plugin) {
        super(plugin, null, "sethome", true, Collections.singletonList("sethome"), false);
        this.plugin = plugin;
    }

    @Override
    public boolean command(CommandSender sender, String[] args) {
        Member member = this.plugin.getMembersManager().getMember(sender.getName());
        member.createHome(plugin, ((Player)sender).getLocation());
        sender.sendMessage(ChatColor.YELLOW + "Home impostata.");
        return true;
    }

    @Override
    public List<String> tabComplete(String[] args) {
        return null;
    }
}

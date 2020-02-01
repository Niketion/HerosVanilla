package net.herospvp.herosvanilla.commands.subcommands;

import net.herospvp.herosvanilla.HerosVanilla;
import net.herospvp.herosvanilla.managers.MembersManager;
import net.herospvp.herosvanilla.managers.PartiesManager;
import net.herospvp.herosvanilla.structure.Member;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PartyInvite extends PartySubCommands {

    public PartyInvite(HerosVanilla plugin, PartiesManager partiesManager, MembersManager membersManager) {
        super(plugin, partiesManager, membersManager);
    }

    @Override
    public boolean run(CommandSender commandSender, String[] strings) {
        Member member = getMembers().getMember(commandSender.getName());
        if (strings.length != 2) {
            return false;
        }

        Player target = Bukkit.getPlayerExact(strings[1]);
        if (target == null) {
            commandSender.sendMessage(ChatColor.RED + "Player non trovato.");
            return true;
        }

        if (!member.hasParty()) {
            commandSender.sendMessage(ChatColor.RED + "Non hai un party.");
            return true;
        }

        Member memberTarget = getMembers().getMember(target.getName());
        if (memberTarget.hasParty()) {
            commandSender.sendMessage(ChatColor.RED + target.getName() + " è già membro di un party.");
            return true;
        }

        if (!member.getParty().isOwner(member)) {
            commandSender.sendMessage(ChatColor.RED + "Non sei owner del party.");
            return true;
        }

        getPlugin().getRequest().put(target.getName(), member.getParty().getName());
        commandSender.sendMessage(ChatColor.GREEN + "Richiesta inviata a " + ChatColor.YELLOW + target.getName());
        target.sendMessage(ChatColor.YELLOW + commandSender.getName() + ChatColor.GREEN
                + " ti ha invitato ad entrare nel party "
                + ChatColor.YELLOW + member.getParty().getName() + ChatColor.GREEN + ". " + ChatColor.GRAY + "Utilizza il comando /party join per accettare");
        return true;
    }
}

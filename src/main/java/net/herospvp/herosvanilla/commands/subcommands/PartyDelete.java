package net.herospvp.herosvanilla.commands.subcommands;

import net.herospvp.herosvanilla.HerosVanilla;
import net.herospvp.herosvanilla.managers.MembersManager;
import net.herospvp.herosvanilla.managers.PartiesManager;
import net.herospvp.herosvanilla.structure.Member;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PartyDelete extends PartySubCommands {

    public PartyDelete(HerosVanilla plugin, PartiesManager partiesManager, MembersManager membersManager) {
        super(plugin, partiesManager, membersManager);
    }

    @Override
    public boolean run(CommandSender commandSender, String[] strings) {
        Member member = getMembers().getMember(commandSender.getName());

        if (!member.hasParty()) {
            commandSender.sendMessage(ChatColor.RED + "Non hai un party.");
            return true;
        }

        if (member != member.getParty().getOwner()) {
            commandSender.sendMessage(ChatColor.RED + "Non sei owner del party.");
            return true;
        }

        getParties().deleteParty(member.getParty().getName());
        commandSender.sendMessage(ChatColor.GRAY + "Hai cancellato il tuo party. Tutti i claim sono stati resettati!");
        return true;
    }
}

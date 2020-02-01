package net.herospvp.herosvanilla.commands.subcommands;

import net.herospvp.herosvanilla.HerosVanilla;
import net.herospvp.herosvanilla.managers.MembersManager;
import net.herospvp.herosvanilla.managers.PartiesManager;
import net.herospvp.herosvanilla.structure.Member;
import net.herospvp.herosvanilla.structure.Party;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class PartyJoin extends PartySubCommands {

    public PartyJoin(HerosVanilla plugin, PartiesManager partiesManager, MembersManager membersManager) {
        super(plugin, partiesManager, membersManager);
    }

    @Override
    public boolean run(CommandSender commandSender, String[] strings) {
        if (!getPlugin().getRequest().containsKey(commandSender.getName())) {
            commandSender.sendMessage(ChatColor.RED + "Non hai nessuna richiesta in sospeso.");
            return true;
        }

        String partyName = getPlugin().getRequest().get(commandSender.getName());
        Party party = getParties().getParty(partyName);
        party.addMember(getPlugin(), getMembers().getMember(commandSender.getName()));

        getPlugin().getRequest().remove(commandSender.getName());

        Member member = getMembers().getMember(commandSender.getName());
        member.setParty(party);
        commandSender.sendMessage(ChatColor.GREEN + "Sei entrato nel party " + partyName + "!");
        return true;
    }
}

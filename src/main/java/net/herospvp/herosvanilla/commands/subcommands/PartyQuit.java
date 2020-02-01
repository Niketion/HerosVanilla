package net.herospvp.herosvanilla.commands.subcommands;

import net.herospvp.herosvanilla.HerosVanilla;
import net.herospvp.herosvanilla.managers.MembersManager;
import net.herospvp.herosvanilla.managers.PartiesManager;
import net.herospvp.herosvanilla.structure.Member;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class PartyQuit extends PartySubCommands {

    public PartyQuit(HerosVanilla plugin, PartiesManager partiesManager, MembersManager membersManager) {
        super(plugin, partiesManager, membersManager);
    }

    @Override
    public boolean run(CommandSender commandSender, String[] strings) {
        Member member = getMembers().getMember(commandSender.getName());

        if (!member.hasParty()) {
            commandSender.sendMessage(ChatColor.RED + "Non sei in un party.");
            return true;
        }

        if (member.getParty().isOwner(member)) {
            commandSender.sendMessage(ChatColor.RED + "Se sei owner, non puoi abbandonare il tuo party! Esegui /party delete");
            return true;
        }

        member.getParty().removeMember(getPlugin(), member);
        member.unclaimAll(getPlugin());
        commandSender.sendMessage(ChatColor.GREEN+"Sei uscito dal party.");
        return true;
    }
}

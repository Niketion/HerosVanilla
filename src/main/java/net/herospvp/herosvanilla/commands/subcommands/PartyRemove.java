package net.herospvp.herosvanilla.commands.subcommands;

import net.herospvp.herosvanilla.HerosVanilla;
import net.herospvp.herosvanilla.managers.MembersManager;
import net.herospvp.herosvanilla.managers.PartiesManager;
import net.herospvp.herosvanilla.structure.Member;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PartyRemove extends PartySubCommands {

    public PartyRemove(HerosVanilla plugin, PartiesManager partiesManager, MembersManager membersManager) {
        super(plugin, partiesManager, membersManager);
    }

    @Override
    public boolean run(CommandSender commandSender, String[] strings) {
        Member member = getMembers().getMember(commandSender.getName());
        if (strings.length != 2) {
            return false;
        }

        if (!member.hasParty()) {
            commandSender.sendMessage(ChatColor.RED + "Non hai un party.");
            return true;
        }

        if (!member.getParty().isMember(strings[1])) {
            commandSender.sendMessage(ChatColor.RED + "Non è membro del party.");
            return true;
        }


        member.getParty().removeMember(getPlugin(), getMembers().getMember(strings[1]));
        commandSender.sendMessage(ChatColor.RED + strings[1] + " è stato rimosso dal party.");
        return true;
    }


}

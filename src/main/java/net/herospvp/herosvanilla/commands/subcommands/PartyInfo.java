package net.herospvp.herosvanilla.commands.subcommands;

import net.herospvp.herosvanilla.HerosVanilla;
import net.herospvp.herosvanilla.managers.MembersManager;
import net.herospvp.herosvanilla.managers.PartiesManager;
import net.herospvp.herosvanilla.structure.Member;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PartyInfo extends PartySubCommands {

    public PartyInfo(HerosVanilla plugin, PartiesManager partiesManager, MembersManager membersManager) {
        super(plugin, partiesManager, membersManager);
    }

    @Override
    public boolean run(CommandSender commandSender, String[] strings) {
        Member member = getMembers().getMember( commandSender.getName());

        if (!member.hasParty()) {
            commandSender.sendMessage(ChatColor.RED + "Non hai un party.");
            return true;
        }

        commandSender.sendMessage((ChatColor.GOLD + "NOME: " + ChatColor.GRAY+  member.getParty().getName()
                + "\n"+ChatColor.GOLD+"OWNER: " +ChatColor.GRAY+ member.getParty().getOwner().getName() +"\n"+ChatColor.GOLD+"MEMBRI: ").split("\n"));
        if (member.getParty().getPlayers() != null && member.getParty().getPlayers().size() != 0) {
            for (Member members : member.getParty().getPlayers()) {
                commandSender.sendMessage(ChatColor.GRAY + "  - " + members.getName());
            }
        } else {
            commandSender.sendMessage(ChatColor.YELLOW + " Non hai membri :(");
        }

        return true;
    }
}

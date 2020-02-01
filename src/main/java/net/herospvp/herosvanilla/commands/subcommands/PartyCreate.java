package net.herospvp.herosvanilla.commands.subcommands;

import net.herospvp.herosvanilla.HerosVanilla;
import net.herospvp.herosvanilla.managers.MembersManager;
import net.herospvp.herosvanilla.managers.PartiesManager;
import net.herospvp.herosvanilla.structure.Member;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PartyCreate extends PartySubCommands {

    public PartyCreate(HerosVanilla plugin, PartiesManager partiesManager, MembersManager membersManager) {
        super(plugin, partiesManager, membersManager);
    }

    @Override
    public boolean run(CommandSender commandSender, String[] strings) {
        Member member = getMembers().getMember(commandSender.getName());
        if (strings.length != 2) {
            return false;
        }

        if (getParties().existParty(strings[1])) {
            commandSender.sendMessage(ChatColor.RED + "Party già esistente.");
            return true;
        }

        if (member.hasParty()) {
            commandSender.sendMessage(ChatColor.RED + "Hai già un party.");
            return true;
        }

        member.setParty(getParties().createParty(strings[1], (Player)commandSender));
        commandSender.sendMessage(ChatColor.GREEN + "Hai creato il party " + ChatColor.YELLOW + strings[1] + ChatColor.GREEN + "!");
        return true;
    }
}

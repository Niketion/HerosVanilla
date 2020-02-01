package net.herospvp.herosvanilla.commands.subcommands;

import lombok.Getter;
import net.herospvp.herosvanilla.HerosVanilla;
import net.herospvp.herosvanilla.managers.MembersManager;
import net.herospvp.herosvanilla.managers.PartiesManager;
import org.bukkit.command.CommandSender;

@Getter
public abstract class PartySubCommands {
    private HerosVanilla plugin;
    private PartiesManager parties;
    private MembersManager members;

    public PartySubCommands(HerosVanilla plugin, PartiesManager partiesManager, MembersManager membersManager) {
        this.parties = partiesManager;
        this.plugin = plugin;
        this.members = membersManager;
    }

    public abstract boolean run(CommandSender commandSender, String[] strings);
}

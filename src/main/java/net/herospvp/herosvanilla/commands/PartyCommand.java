package net.herospvp.herosvanilla.commands;

import net.herospvp.herosvanilla.HerosVanilla;
import net.herospvp.herosvanilla.commands.subcommands.*;
import net.herospvp.herosvanilla.managers.MembersManager;
import net.herospvp.herosvanilla.managers.PartiesManager;
import net.herospvp.herosvanilla.structure.Member;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PartyCommand extends CommandHandler {
    private HerosVanilla plugin;

    public PartyCommand(HerosVanilla plugin) {
        super(plugin, null, "party", true, Arrays.asList(
                "&6Argomenti comando /party:",
                " &e- /party create <nome>",
                " &7Crea il party.",
                " &e- /party invite <player>",
                " &7Invita un giocatore nel tuo party.",
                " &e- /party remove <player>",
                " &7Rimuovi un giocatore dal tuo party.",
                " &e- /party info",
                " &7Informazioni riguardo al party.",
                " &e- /party delete",
                " &7Cancella il party (attenzione: ogni giocatore perderà i propri claim)",
                " &e- /party join",
                " &7Entra nel party in cui ti hanno invitato.",
                " &e- /party quit",
                " &7Esci dal party. (attenzione: perderai i tuoi claim)"
        ), true);
        this.plugin = plugin;
    }

    @Override
    public boolean command(CommandSender commandSender, String[] strings) {
        if (!(strings.length > 0)) {
            return false;
        }

        PartiesManager parties = plugin.getPartiesManager();
        MembersManager members = plugin.getMembersManager();
        Member member = members.getMember(commandSender.getName());
        switch (strings[0].toLowerCase()) {
            case "create": {
                return new PartyCreate(plugin, parties, members).run(commandSender, strings);
            }
            case "invite": {
                return new PartyInvite(plugin, parties, members).run(commandSender, strings);
            }
            case "remove": {
                return new PartyRemove(plugin, parties, members).run(commandSender, strings);
            }
            case "info": {
                return new PartyInfo(plugin, parties, members).run(commandSender, strings);
            }
            case "delete": {
                return new PartyDelete(plugin, parties, members).run(commandSender, strings);
            }
            case "join": {
                return new PartyJoin(plugin, parties, members).run(commandSender, strings);
            }
            case "quit": {
                return new PartyQuit(plugin, parties, members).run(commandSender, strings);
            }
        }
        return false;
    }

    @Override
    public List<String> tabComplete(String[] strings) {
        if (strings.length == 0) return Arrays.asList("create", "invite", "remove", "info", "delete", "join", "quit");
        else {
            List<String> list = new ArrayList<>();
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                list.add(onlinePlayer.getName());
            }

            return list;
        }
    }
}

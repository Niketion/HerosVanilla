package net.herospvp.herosvanilla;

import lombok.Getter;
import lombok.Setter;
import net.herospvp.herosvanilla.commands.*;
import net.herospvp.herosvanilla.listeners.ChatListeners;
import net.herospvp.herosvanilla.listeners.ClaimListeners;
import net.herospvp.herosvanilla.listeners.ConnectionListeners;
import net.herospvp.herosvanilla.listeners.DeathListeners;
import net.herospvp.herosvanilla.managers.MembersManager;
import net.herospvp.herosvanilla.managers.PartiesManager;
import net.herospvp.herosvanilla.structure.Member;
import net.herospvp.herosvanilla.tasks.AnnounceTask;
import net.herospvp.herosvanilla.tasks.MoveTask;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
public class HerosVanilla extends JavaPlugin {
    private HashMap<String, String> request = new HashMap<>();
    private HashMap<String, List<Chunk>> confirm = new HashMap<>();
    private PartiesManager partiesManager;
    @Setter
    private MembersManager membersManager;
    private Chat vaultChat;

    @Setter
    private int announce=0;
    private String[] announceMessage = new String[] {
            ChatColor.RED + "[TIP] Vuoi scrivere con la chat colorata? Vuoi avere accesso al /sethome? Acquista il pacchetto "
                    + ChatColor.YELLOW + "VIP" + ChatColor.RED + " a soli 10euro!",
            ChatColor.RED + "[TIP] Qualcosa ti da noia? Proponici le tue idee su www.herospvp.net!",
    };

    @Override
    public void onEnable() {
        saveDefaultConfig();

        this.partiesManager = new PartiesManager(this);
        partiesManager.loadAllParties(result -> {
            partiesManager.setParties(result);

            for (Player players : Bukkit.getOnlinePlayers()) {
                membersManager.loadMember(players);
            }
        });

        new MoveTask(this).runTaskTimer(this, 10, 10);

        new ConnectionListeners(this);
        new PartyCommand(this);
        new ClaimCommand(this);
        new ClaimListeners(this);
        new ChatListeners(this);
        new RandomCommand(this);
        new HubCommand(this);
        new SetHomeCommand(this);
        new DeathListeners(this);
        new HomeCommand(this);

        vaultChat = getServer().getServicesManager().getRegistration(Chat.class).getProvider();

        new AnnounceTask(this).runTaskTimerAsynchronously(this, 0, 20*60*10);
    }

}

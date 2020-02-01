package net.herospvp.herosvanilla.managers;

import lombok.Getter;
import lombok.Setter;
import net.herospvp.herosvanilla.HerosVanilla;
import net.herospvp.herosvanilla.structure.Member;
import net.herospvp.herosvanilla.structure.Party;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Getter
public class PartiesManager {
    private HerosVanilla plugin;
    @Setter
    private List<Party> parties;

    public PartiesManager(HerosVanilla plugin) {
        this.plugin = plugin;
        this.parties = new ArrayList<>();
    }

    public void loadAllParties(Consumer<List<Party>> result) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            FileConfiguration config = plugin.getConfig();
            List<Party> list = new ArrayList<>();

            if (config.getConfigurationSection("party") == null) {
                result.accept(list);
                return;
            }

            plugin.setMembersManager(new MembersManager(plugin));

            MembersManager membersManager = getPlugin().getMembersManager();
            for (String partyName : config.getConfigurationSection("party").getKeys(false)) {
                partyName = partyName.toLowerCase();
                String ownerName = config.getString("party."+partyName+".owner");
                List<String> players = config.getStringList("party."+partyName+".players");

                Party party = new Party(partyName);
                Member owner = new Member(ownerName, party);
                party.setOwner(owner);
                membersManager.getMembers().add(owner);

                List<Member> members = new ArrayList<>();
                for (String playersName : players) {
                    Member member = new Member(playersName, party);
                    membersManager.loadClaims(member::setClaims);
                    members.add(member);
                    membersManager.getMembers().add(member);
                }
                party.setPlayers(members);
                list.add(party);
            }
            result.accept(list);
        });
    }

    public Party createParty(String name, Player owner) {
        String nameLower = name.toLowerCase();

        plugin.getConfig().set("party." + nameLower + ".owner", owner.getName());
        plugin.getConfig().set("party."+ nameLower + ".players", new ArrayList<>());
        plugin.saveConfig();

        Party party = new Party(name);
        party.setOwner(plugin.getMembersManager().getMember(owner.getName()));
        parties.add(party);
        return party;
    }

    public void deleteParty(String name) {
        plugin.getConfig().set("party." + name.toLowerCase(), null);
        plugin.saveConfig();

        Party party = getParty(name);
        Member owner = party.getOwner();
        owner.setParty(null);
        owner.unclaimAll(plugin);

        if (party.getPlayers() != null) {
            for (Member member : party.getPlayers()) {
                member.setParty(null);
                member.unclaimAll(plugin);
            }
        }

        parties.remove(getParty(name));
    }

    public boolean existParty(String name) {
        return getParty(name) != null;
    }

    public Party getParty(String name) {
        for (Party party : parties) {
            if (party.getName().equalsIgnoreCase(name)) {
                return party;
            }
        }
        return null;
    }
}

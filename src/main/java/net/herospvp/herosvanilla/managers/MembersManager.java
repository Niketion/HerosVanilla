package net.herospvp.herosvanilla.managers;

import lombok.Getter;
import net.herospvp.herosvanilla.HerosVanilla;
import net.herospvp.herosvanilla.structure.Member;
import net.herospvp.herosvanilla.structure.Party;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Getter
public class MembersManager {
    private HerosVanilla plugin;
    private List<Member> members;

    public MembersManager(HerosVanilla plugin) {
        this.plugin = plugin;
        this.members = new ArrayList<>();
    }

    public Party getParty(Player player) {
        if (getMember(player.getName()) != null) {
            return getMember(player.getName()).getParty();
        }

        for (Party party : plugin.getPartiesManager().getParties()) {
            if (player.getName().equalsIgnoreCase(party.getOwner().getName())) {
                return party;
            }

            for (Member member : party.getPlayers()) {
                if (member.getName().equals(player.getName())) {
                    return party;
                }
            }
        }
        return null;
    }

    public void loadMember(Player player) {
        if (plugin.getMembersManager().getMember(player.getName()) != null) {
            return;
        }

        Member member = new Member(player.getName(), getParty(player));

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            if (plugin.getConfig().getString("home." + player.getName()) != null) {
                member.setHome(getLocationFromString(plugin.getConfig().getString("home." + player.getName())));
            }
        });

        plugin.getMembersManager().loadClaims(player, member::setClaims);
        members.add(member);
    }


    public void loadClaims(Consumer<List<Chunk>> result) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            for (String players : plugin.getConfig().getConfigurationSection("claims").getKeys(false)) {
                List<Chunk> list = new ArrayList<>();

                for (String chunkString : plugin.getConfig().getStringList("claims." + players)) {
                    list.add(parseStringToChunk(chunkString));
                }
                result.accept(list);
            }
        });
    }

    public void loadClaims(Player player, Consumer<List<Chunk>> result) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            String players = player.getName();
            List<Chunk> list = new ArrayList<>();

            for (String chunkString : plugin.getConfig().getStringList("claims." + players)) {
                list.add(parseStringToChunk(chunkString));
            }
            result.accept(list);
        });
    }

    public Member getMember(String playerName) {
        for (Member member : members) {
            if (member.getName().equals(playerName)) {
                return member;
            }
        }
        return null;
    }

    public static Member getClaim(HerosVanilla plugin, Chunk chunk) {
        for (Member member : plugin.getMembersManager().getMembers()) {
            if (member.getClaims() == null) {
                continue;
            }
            for (Chunk claim : member.getClaims()) {
                if (claim == chunk) {
                    return member;
                }
            }
        }
        return null;
    }

    private Chunk parseStringToChunk(String string) {
        String[] splitted = string.split(":");
        return Bukkit.getWorld(splitted[0]).getChunkAt(Integer.parseInt(splitted[1]), Integer.parseInt(splitted[2]));
    }

    private Location getLocationFromString(String s) {
        if (s == null || s.trim() == "") {
            return null;
        }
        final String[] parts = s.split(":");
        if (parts.length == 6) {
            World w = Bukkit.getServer().getWorld(parts[0]);
            double x = Double.parseDouble(parts[1]);
            double y = Double.parseDouble(parts[2]);
            double z = Double.parseDouble(parts[3]);
            float yaw = Float.parseFloat(parts[4]);
            float pitch = Float.parseFloat(parts[5]);
            return new Location(w, x, y, z, yaw, pitch);
        }
        return null;
    }
}

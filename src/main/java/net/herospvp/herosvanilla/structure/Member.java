package net.herospvp.herosvanilla.structure;

import lombok.Getter;
import lombok.Setter;
import net.herospvp.herosvanilla.HerosVanilla;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Member {
    @Setter
    private Location home;
    @Setter
    private Member lastCheckedLocation;
    private String name;
    @Setter
    private Party party;
    @Setter
    private List<Chunk> claims;

    public Member(String name, Party party) {
        this.name = name;
        this.party = party;
    }

    public boolean hasParty() {
        return party != null;
    }

    public void claim(HerosVanilla plugin, Chunk chunk) {
        Player player = Bukkit.getPlayerExact(name);

        for (Member member : plugin.getMembersManager().getMembers()) {
            if (member.getClaims() == null || !(member.getClaims().size() > 0)) {
                continue;
            }
            for (Chunk claim : member.getClaims()) {
                if (chunk == claim) {
                    player.sendMessage(ChatColor.RED + "Non puoi claimare il chunk di coords X: " + chunk.getX() + ", Y: " + chunk.getZ()
                            + ". Appartiene a " + member.getName() + "!");
                    return;
                }
            }
        }

        if (claims == null) this.claims = new ArrayList<>();
        if (claims.size() >= 20) {
            player.sendMessage(ChatColor.RED + "Hai raggiunto il limite massimo di claim!");
            return;
        }

        claims.add(chunk);
        List<String> list = new ArrayList<>();
        for (Chunk chunkClaimed : claims) {
            list.add(parseChunkToString(chunkClaimed));
        }
        plugin.getConfig().set("claims." + name, list);
        plugin.saveConfig();
    }

    public void unclaim(HerosVanilla plugin, Chunk chunk) {
        if (claims.contains(chunk)) {
            claims.remove(chunk);

            List<String> list = new ArrayList<>();
            for (String chunkString : plugin.getConfig().getStringList("claims." + name)) {
                if (!chunkString.equals(parseChunkToString(chunk))) {
                    list.add(chunkString);
                }
            }
            plugin.getConfig().set("claims." + name, list);
            plugin.saveConfig();
        }
    }

    public void createHome(HerosVanilla plugin, Location location) {
        this.home = location;

        plugin.getConfig().set("home." + name, getStringFromLocation(location));
        plugin.saveConfig();
    }

    public boolean hasHome() {
        return home != null;
    }

    public void unclaimAll(HerosVanilla plugin) {
        if (claims != null) claims.clear();
        plugin.getConfig().set("claims." + name, null);
        plugin.saveConfig();
    }

    private String parseChunkToString(Chunk chunk) {
        return chunk.getWorld().getName() + ":" + chunk.getX() + ":" + chunk.getZ();
    }

    private String getStringFromLocation(Location loc) {
        if (loc == null) {
            return "";
        }
        return loc.getWorld().getName() + ":" + loc.getX() + ":" + loc.getY() + ":" + loc.getZ() + ":" + loc.getYaw() + ":" + loc.getPitch() ;
    }
}

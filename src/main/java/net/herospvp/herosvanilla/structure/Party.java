package net.herospvp.herosvanilla.structure;

import lombok.Getter;
import lombok.Setter;
import net.herospvp.herosvanilla.HerosVanilla;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Party {
    @Setter
    private List<Member> players;
    private String name;
    @Setter
    private Member owner;

    public Party(String name) {
        this.name = name;
    }

    public void addMember(HerosVanilla plugin, Member member) {
        member.setParty(this);
        if (players == null) players = new ArrayList<>();
        players.add(member);

        plugin.getConfig().set("party."+name+".players", players);
        plugin.saveConfig();
    }

    public void removeMember(HerosVanilla plugin, Member member) {
        players.remove(member);

        plugin.getConfig().set("party." + name + ".players", players);
        plugin.saveConfig();
        member.unclaimAll(plugin);
        member.setParty(null);
    }

    public boolean isMember(String nameMember) {
        for (Member member : players) {
            if (member.getName().equalsIgnoreCase(nameMember))
                return true;
        }
        return false;
    }

    public boolean isOwner(Member member) {
        return owner == member;
    }
}

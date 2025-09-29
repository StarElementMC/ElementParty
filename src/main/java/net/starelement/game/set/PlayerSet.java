package net.starelement.game.set;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.level.Position;
import net.starelement.game.Operable;
import net.starelement.game.PartyPlayer;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class PlayerSet implements Operable {

    private HashSet<PartyPlayer> players = new HashSet<>();

    public PlayerSet() {
        Collection<Player> online = Server.getInstance().getOnlinePlayers().values();
        for (Player player : online) {
            players.add(new PartyPlayer(player));
        }
    }

    public PlayerSet(HashSet<PartyPlayer> players) {
        this.players = players;
    }

    public PartyPlayer getPlayer(Player player) {
        for (PartyPlayer partyPlayer : players) {
            if (partyPlayer.getPlayer() == player) {
                return partyPlayer;
            }
        }
        return null;
    }

    public Set<PartyPlayer> getSet() {
        return players;
    }

    @Override
    public void teleportRoom() {
        for (PartyPlayer player : players) {
            player.teleportRoom();
        }
    }

    @Override
    public void teleport(Position position) {
        for (PartyPlayer player : players) {
            player.teleport(position);
        }
    }

    @Override
    public void sendMessage(String message) {
        for (PartyPlayer player : players) {
            player.sendMessage(message);
        }
    }

    @Override
    public void sendTitle(String title, String subtitle) {
        for (PartyPlayer player : players) {
            player.sendTitle(title, subtitle);
        }
    }

    @Override
    public void sendTitle(String title) {
        for (PartyPlayer player : players) {
            player.sendTitle(title);
        }
    }
}

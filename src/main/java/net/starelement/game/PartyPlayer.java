package net.starelement.game;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.level.Position;

public class PartyPlayer {

    private Player player;
    private boolean active = true;

    protected PartyPlayer(Player player) {
        if (player == null) {
            throw new NullPointerException("Player cannot be null");
        }
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isOnline() {
        return player.isOnline();
    }

    public void lose() {
        active = false;
        player.sendTitle("你被淘汰了");
    }

    public boolean isActive() {
        return active;
    }

    public void teleport(Position position) {
        if (isOnline()) {
            player.teleport(position);
        }
    }

    public void teleportRoom() {
        if (isOnline()) {
            player.teleport(Server.getInstance().getDefaultLevel().getSpawnLocation());
        }
    }

}

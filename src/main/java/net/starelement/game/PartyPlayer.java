package net.starelement.game;

import cn.nukkit.Player;

public class PartyPlayer {

    private Player player;

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

}

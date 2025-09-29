package net.starelement.game;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.level.Position;

public class PartyPlayer implements Operable {

    private Player player;
    private boolean active = true;

    public PartyPlayer(Player player) {
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

    @Override
    public void teleport(Position position) {
        if (isOnline()) {
            player.teleport(position);
        }
    }

    @Override
    public void sendMessage(String message) {
        if (isOnline()) {
            player.sendMessage(message);
        }
    }

    @Override
    public void sendTitle(String title, String subtitle) {
        if (isOnline()) {
            player.sendTitle(title, subtitle);
        }
    }

    @Override
    public void sendTitle(String title) {
        if (isOnline()) {
            player.sendTitle(title);
        }
    }

    @Override
    public void teleportRoom() {
        if (isOnline()) {
            player.teleport(Server.getInstance().getDefaultLevel().getSpawnLocation());
        }
    }

}

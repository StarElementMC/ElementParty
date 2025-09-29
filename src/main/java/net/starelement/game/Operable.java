package net.starelement.game;

import cn.nukkit.level.Position;

public interface Operable {

    void teleportRoom();

    void teleport(Position position);

    void sendMessage(String message);

    void sendTitle(String title, String subtitle);

    void sendTitle(String title);

}

package net.starelement.game;

import cn.nukkit.event.Listener;

public class GameListener implements Listener {

    protected PartyGame game;
    protected Party party;

    public PartyGame getGame() {
        return game;
    }

    public Party getParty() {
        return party;
    }

    public GameManager getGameManager() {
        return GameManager.getInstance();
    }

}

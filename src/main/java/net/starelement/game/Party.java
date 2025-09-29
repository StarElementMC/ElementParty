package net.starelement.game;

import cn.nukkit.Player;
import cn.nukkit.level.Level;
import net.starelement.game.set.PlayerSet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

public class Party {

    private PartyGame currentGame;
    private PlayerSet players;
    private boolean started = false;
    private ArrayList<PartyGame> gameList;
    private int index = 0;
    private HashMap<PartyGame, Level> levels = new HashMap<>();

    protected Party(ArrayList<PartyGame> gameList, PlayerSet players) {
        this.gameList = gameList;
        this.players = players;
    }

    public PartyGame getCurrentGame() {
        return currentGame;
    }

    public PlayerSet getPlayers() {
        return players;
    }

    public ArrayList<PartyGame> getGameList() {
        return gameList;
    }

    protected PartyGame nextGame() {
        currentGame = gameList.get(index);
        index++;
        return currentGame;
    }

    public int getIndex() {
        return index;
    }

    protected void putLevel(PartyGame game, Level level) {
        levels.put(game, level);
        game.setLevel(level);
    }

    public synchronized void start() {
        started = true;
        try {
            for (PartyGame game : gameList) {
                game.party = this;
                currentGame = game;
                index++;
                if (!started) return;
                LevelTemplate template = game.getRandomLevel();
                Level level = template.install();
                if (level != null) {
                    putLevel(game, level);
                } else {
                    throw new RuntimeException("Level not installed " + template);
                }
                players.sendMessage("3秒后传送");
                Thread.sleep(3000);
                game.join(players);
                game.start();
                wait();
            }
            started = false;
            currentGame = null;
            finish();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void next() {
        notifyAll();
    }

    public synchronized void finish() {
        started = false;
        notifyAll();
        if (currentGame != null) currentGame.finish();
        GameManager.getInstance().getLogger().info("游戏结束");
        players.teleportRoom();
    }

}

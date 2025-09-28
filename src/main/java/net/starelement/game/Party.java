package net.starelement.game;

import cn.nukkit.Player;
import cn.nukkit.level.Level;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

public class Party {

    private PartyGame currentGame;
    private HashSet<PartyPlayer> players = new HashSet<>();
    private boolean started = false;
    private ArrayList<PartyGame> gameList;
    private int index = 0;
    private HashMap<PartyGame, Level> levels = new HashMap<>();

    protected Party(ArrayList<PartyGame> gameList, ArrayList<PartyPlayer> players) {
        this.gameList = gameList;
        this.players.addAll(players);
    }

    public PartyGame getCurrentGame() {
        return currentGame;
    }

    public Collection<PartyPlayer> getPlayers() {
        return players;
    }

    public void sendMessage(String message) {
        for (PartyPlayer player : players) {
            player.getPlayer().sendMessage(message);
        }
    }

    public void sendTitle(String title, String subtitle) {
        for (PartyPlayer player : players) {
            player.getPlayer().sendTitle(title, subtitle);
            player.getPlayer().sendActionBar(subtitle);
        }
    }

    public void sendActionBar(String message) {
        for (PartyPlayer player : players) {
            player.getPlayer().sendActionBar(message);
        }
    }

    public PartyPlayer getPlayer(Player player) {
        for (PartyPlayer partyPlayer : players) {
            if (partyPlayer.getPlayer() == player) {
                return partyPlayer;
            }
        }
        return null;
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
        PartyGame game = nextGame();
        game.party = this;
        try {
            for (int i = 0; i < 3; i++) {
                if (!started) return;
                LevelTemplate template = game.getRandomLevel();
                Level level = template.install();
                if (level != null) {
                    putLevel(game, level);
                } else {
                    throw new RuntimeException("Level not installed " + template);
                }
                sendMessage("3秒后传送");
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
    }

}

package net.starelement.game;

import cn.nukkit.Server;
import cn.nukkit.event.HandlerList;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.plugin.Plugin;

import java.util.Collection;
import java.util.HashSet;

public class PartyGame {

    private Plugin plugin;
    private HashSet<GameListener> listeners = new HashSet<>();
    private HashSet<LevelTemplate> levels = new HashSet<>();
    private HashSet<Tag> tags = new HashSet<>();
    private Level level;
    private HashSet<PartyPlayer> players;
    private String name;
    private String displayName;
    protected Party party;

    public PartyGame(Plugin plugin) {
        this.plugin = plugin;
        name = plugin.getName();
        displayName = plugin.getName();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void addListener(GameListener listener) {
        listeners.add(listener);
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void addLevel(LevelTemplate template) {
        levels.add(template);
    }

    public void addTag(Tag tag) {
        tags.add(tag);
    }

    public boolean hasTag(Tag tag) {
        return tags.contains(tag);
    }

    public Collection<Tag> getTags() {
        return new HashSet<>(tags);
    }

    public LevelTemplate getRandomLevel() {
        return levels.stream().findAny().orElse(null);
    }

    public Collection<GameListener> getListeners() {
        return listeners;
    }

    protected void setLevel(Level level) {
        this.level = level;
    }

    public Level getLevel() {
        return level;
    }

    public void movePlayerToLevel(PartyPlayer player) {
        Position location = level.getSpawnLocation();
        location.setY(location.getY() + 2);
        player.getPlayer().teleport(location);
    }

    public void movePlayerToLevel() {
        for (PartyPlayer player : players) {
            movePlayerToLevel(player);
        }
    }

    public void lose(PartyPlayer player) {
        player.lose();
        HashSet<PartyPlayer> activePlayers = getActivePlayers();
    }

    public HashSet<PartyPlayer> getActivePlayers() {
        HashSet<PartyPlayer> list = new HashSet<>();
        for (PartyPlayer player : players) {
            if (player.isActive()) {
                list.add(player);
            }
        }
        return list;
    }

    public void join(HashSet<PartyPlayer> players) {
        this.players = players;
        movePlayerToLevel();
    }

    public void start() throws InterruptedException {
        Thread.sleep(5000);
        for (int i = 3; i > 0; i--) {
            party.sendTitle(String.valueOf(i), "即将开始");
            Thread.sleep(1000);
        }
        Collection<GameListener> listeners = getListeners();
        for (GameListener listener : listeners) {
            listener.game = this;
            listener.party = party;
            Server.getInstance().getPluginManager()
                    .registerEvents(listener, GameManager.getInstance().getPlugin());
        }
        party.sendTitle("游戏开始", "Go!");
    }

    public void finish() {
        party.sendTitle("游戏结束", "Finished!");
        for (GameListener listener : listeners) {
            HandlerList.unregisterAll(listener);
        }
        party.next();
    }

    public enum Tag {

        RACING,
        SURVIVAL,
        SCORE,
        TEAM,
        SOLO,
        FINAL,

    }

}

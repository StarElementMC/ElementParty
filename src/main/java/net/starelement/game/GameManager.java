package net.starelement.game;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.level.Level;
import cn.nukkit.utils.Logger;
import net.starelement.ElementParty;
import net.starelement.task.ReadyTask;

import java.util.HashMap;
import java.util.HashSet;

public class GameManager {

    private static GameManager instance = new GameManager();
    private HashMap<String, PartyGame> games = new HashMap<>();
    private PartyGame currentGame;
    private HashSet<PartyPlayer> players = new HashSet<>();
    private boolean started = false;
    private HashMap<PartyGame, Level> levels = new HashMap<>();

    public ElementParty plugin;

    private GameManager() {}

    public static GameManager getInstance() {
        return instance;
    }

    public void registerGame(PartyGame game) {
        games.put(game.getName(), game);
    }

    public PartyGame getCurrentGame() {
        return currentGame;
    }

    public Logger getLogger() {
        return plugin.getLogger();
    }

    public ElementParty getPlugin() {
        return plugin;
    }

    public void startParty() {
//        if (started) throw new IllegalStateException("Party already started");
        started = true;
        Server server = Server.getInstance();
        server.getLogger().info("Start");
        for (Player player : server.getOnlinePlayers().values()) {
            players.add(new PartyPlayer(player));
        }
        for (PartyGame game : games.values()) {
            LevelTemplate template = game.getRandomLevel();
            Level level = template.install();
            if (level != null) {
                levels.put(game, level);
            } else {
                throw new RuntimeException("Level not installed " + template);
            }
        }
        ReadyTask ready = new ReadyTask();
        server.getScheduler().scheduleAsyncTask(plugin, ready);
    }

}
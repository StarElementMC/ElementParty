package net.starelement.game;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.level.Level;
import cn.nukkit.utils.Logger;
import net.starelement.ElementParty;
import net.starelement.task.ReadyTask;

import java.util.ArrayList;
import java.util.HashMap;

public class GameManager {

    private static GameManager instance = new GameManager();
    private HashMap<String, PartyGame> games = new HashMap<>();

    public ElementParty plugin;

    private GameManager() {}

    public static GameManager getInstance() {
        return instance;
    }

    public void registerGame(PartyGame game) {
        games.put(game.getName(), game);
    }

    public Logger getLogger() {
        return plugin.getLogger();
    }

    public ElementParty getPlugin() {
        return plugin;
    }

    public void startParty() {
//        if (started) throw new IllegalStateException("Party already started");
        Server server = Server.getInstance();
        server.getLogger().info("Start");
        ArrayList<PartyPlayer> players = new ArrayList<>();
        for (Player player : server.getOnlinePlayers().values()) {
            players.add(new PartyPlayer(player));
        }
        ArrayList<PartyGame> gameList = selectGames();
        Party party = new Party(gameList, players);
        for (PartyGame game : gameList) {
            LevelTemplate template = game.getRandomLevel();
            Level level = template.install();
            if (level != null) {
                party.putLevel(game, level);
            } else {
                throw new RuntimeException("Level not installed " + template);
            }
        }
        if (gameList.isEmpty()) return;
//        if (party.getPlayers().isEmpty()) return;
        ReadyTask ready = new ReadyTask(party);
        server.getScheduler().scheduleAsyncTask(plugin, ready);
    }

    /**
     * 在关卡列表中选择要进行的关卡
     * 目前关卡较少就直接复制games
     */
    private ArrayList<PartyGame> selectGames() {
        return new ArrayList<>(games.values());
    }

}
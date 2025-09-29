package net.starelement.game;

import cn.nukkit.Server;
import cn.nukkit.utils.Logger;
import net.starelement.ElementParty;
import net.starelement.game.set.PlayerSet;
import net.starelement.task.ReadyTask;

import java.util.ArrayList;
import java.util.HashMap;

public class GameManager {

    private static GameManager instance = new GameManager();
    private HashMap<String, PartyGame> games = new HashMap<>();
    private Party party;

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

    public Party getParty() {
        return party;
    }

    public void startParty() {
//        if (started) throw new IllegalStateException("Party already started");
        Server server = Server.getInstance();
        server.getLogger().info("Start");
        ArrayList<PartyGame> gameList = selectGames();
        this.party = new Party(gameList, new PlayerSet());
        if (gameList.isEmpty()) return;
//        if (party.getPlayers().isEmpty()) return;
//        ReadyTask ready = new ReadyTask(party);
//        server.getScheduler().scheduleAsyncTask(plugin, ready);
        party.start2();
    }

    /**
     * 在关卡列表中选择要进行的关卡
     * 目前关卡较少就直接复制games
     */
    private ArrayList<PartyGame> selectGames() {
        return new ArrayList<>(games.values());
    }

}
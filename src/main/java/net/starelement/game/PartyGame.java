package net.starelement.game;

import cn.nukkit.event.Listener;
import cn.nukkit.plugin.Plugin;

import java.util.Collection;
import java.util.HashSet;

public class PartyGame {

    private Plugin plugin;
    private HashSet<Listener> listeners = new HashSet<>();
    private HashSet<LevelTemplate> levels = new HashSet<>();

    public PartyGame(Plugin plugin) {
        this.plugin = plugin;
    }

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    public String getName() {
        return plugin.getName();
    }

    public String getDisplayName() {
        return getName();
    }

    public void addLevel(LevelTemplate template) {
        levels.add(template);
    }

    public LevelTemplate getRandomLevel() {
        return levels.stream().findAny().orElse(null);
    }

    public Collection<Listener> getListeners() {
        return listeners;
    }

}

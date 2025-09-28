package net.starelement;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.level.Position;
import net.starelement.game.GameManager;
import net.starelement.game.Party;

public class BaseListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Position position = Server.getInstance().getDefaultLevel().getSpawnLocation();
        player.teleport(position);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Party party = GameManager.getInstance().getParty();
        if (party != null) {
            if (Server.getInstance().getOnlinePlayers().values().size() <= 1) {
                party.finish();
            }
        }
    }

}

package net.starelement.task;

import cn.nukkit.scheduler.AsyncTask;
import net.starelement.game.PartyGame;

public class GameTask extends AsyncTask {

    private PartyGame game;

    public GameTask(PartyGame game) {
        this.game = game;
    }

    @Override
    public void onRun() {
        try {
            game.getPlayers().sendMessage("3秒后传送");
            Thread.sleep(3000);
            game.start();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

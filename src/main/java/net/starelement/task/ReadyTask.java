package net.starelement.task;

import cn.nukkit.scheduler.AsyncTask;
import net.starelement.game.Party;

public class ReadyTask extends AsyncTask {

    private Party party;

    public ReadyTask(Party party) {
        this.party = party;
    }

    @Override
    public void onRun() {
        Thread.currentThread().setName("party");
        party.start();
    }
}

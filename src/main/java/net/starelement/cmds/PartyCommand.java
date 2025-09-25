package net.starelement.cmds;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import net.starelement.game.GameManager;

public class PartyCommand extends Command {

    public PartyCommand(String name) {
        super(name);
    }

    @Override
    public boolean execute(CommandSender sender, String cmd, String[] args) {
        if (!sender.isOp()) return false;
        GameManager manager = GameManager.getInstance();
        manager.startParty();
        return false;
    }
}

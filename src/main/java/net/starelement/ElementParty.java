package net.starelement;

import cn.nukkit.plugin.PluginBase;
import net.starelement.cmds.PartyCommand;
import net.starelement.game.LevelTemplate;

import java.io.File;

public class ElementParty extends PluginBase {

    @Override
    public void onLoad() {
        File lt = new File(LevelTemplate.TEMPLATE_PATH);
        if (!lt.exists()) lt.mkdir();
        getServer().getCommandMap().register("element", new PartyCommand("ep"));
    }

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new BaseListener(), this);
    }

    @Override
    public void onDisable() {
        getServer().getCommandMap().unregister("element");
    }
}

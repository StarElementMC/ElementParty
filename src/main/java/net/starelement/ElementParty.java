package net.starelement;

import cn.nukkit.Server;
import cn.nukkit.plugin.PluginBase;
import net.starelement.cmds.PartyCommand;
import net.starelement.game.GameManager;
import net.starelement.game.LevelTemplate;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class ElementParty extends PluginBase {

    @Override
    public void onLoad() {
        GameManager.getInstance().plugin = this;
        clear();
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

    private void clear() {
        Object world = getServer().getProperty("level-name");
        File worlds = new File("worlds/");
        for (File file : Objects.requireNonNull(worlds.listFiles())) {
            if (!file.getName().equals(world)) {
                try {
                    FileUtils.deleteDirectory(file);
                } catch (IOException e) {
                    getServer().getLogger().logException(e);
                }
            }
        }
    }
}

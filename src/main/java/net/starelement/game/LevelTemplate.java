package net.starelement.game;

import cn.nukkit.Server;
import cn.nukkit.level.Level;
import cn.nukkit.utils.MainLogger;
import cn.nukkit.utils.Utils;

import java.io.File;
import java.io.IOException;

public class LevelTemplate {

    private File file;
    private Type type;
    public static final String LEVEL_PATH = "worlds/";
    public static final String TEMPLATE_PATH = "plugins/level_templates/";

    public LevelTemplate(File file) {
        if (!file.exists()) {
            throw new IllegalArgumentException(file + " File does not exist");
        }
        this.file = file;
        this.type = file.isDirectory() ? Type.DIR : Type.ZIP;
    }

    public LevelTemplate(String name) {
        this(new File(TEMPLATE_PATH + name));
    }

    public Level install() {
        try {
            if (type == Type.DIR) {
                Utils.copyFile(file, new File(LEVEL_PATH + "/" + file.getName()));
            } else if (type == Type.ZIP) {
                //TODO 解压zip
            }
        } catch (Exception e) {
            MainLogger.getLogger().logException(e);
            return null;
        }
        if (Server.getInstance().loadLevel(file.getName())) {
            return Server.getInstance().getLevelByName(file.getName());
        }
        return null;
    }

    public enum Type {

        ZIP,
        DIR,

    }

}
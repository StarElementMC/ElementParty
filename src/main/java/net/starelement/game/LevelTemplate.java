package net.starelement.game;

import cn.nukkit.Server;
import cn.nukkit.level.Level;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.MainLogger;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class LevelTemplate {

    private File file;
    private Type type;
    protected Config config;
    public static final String LEVEL_PATH = "worlds/";
    public static final String TEMPLATE_PATH = "plugins/level_templates/";
    private static int COUNT = 0;

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
        String name = file.getName().replace(".zip", "") + "-" + COUNT++;
        try {
            if (type == Type.DIR) {
                FileUtils.copyDirectory(file, new File(LEVEL_PATH + name));
            } else if (type == Type.ZIP) {
                unzipFile(file, new File(LEVEL_PATH + name));
            }
            this.config = new Config(new File(LEVEL_PATH + name + "/metadata.yml"));
        } catch (Exception e) {
            MainLogger.getLogger().logException(e);
            return null;
        }
        if (Server.getInstance().loadLevel(name)) {
            return Server.getInstance().getLevelByName(name);
        }
        return null;
    }

    private void unzipFile(File zipFile, File destDir) throws IOException {
        byte[] buffer = new byte[1024];
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        try (FileInputStream fis = new FileInputStream(zipFile);
             BufferedInputStream bis = new BufferedInputStream(fis);
             ZipInputStream zis = new ZipInputStream(bis)) {
            ZipEntry ze = zis.getNextEntry();
            while (ze != null) {
                String fileName = ze.getName();
                File newFile = new File(destDir, fileName);
                if (!newFile.getCanonicalPath().startsWith(destDir.getCanonicalPath())) {
                    throw new IOException("Entry is outside of the target dir: " + fileName);
                }

                if (ze.isDirectory()) {
                    newFile.mkdirs();
                } else {
                    new File(newFile.getParent()).mkdirs();
                    try (FileOutputStream fos = new FileOutputStream(newFile)) {
                        int len;
                        while ((len = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, len);
                        }
                    }
                }
                zis.closeEntry();
                ze = zis.getNextEntry();
            }
        }
    }

    public enum Type {

        ZIP,
        DIR,

    }

}
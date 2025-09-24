package net.starelement;

import cn.nukkit.plugin.PluginBase;

public class ElementParty extends PluginBase {

    @Override
    public void onLoad() {

    }

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new BaseListener(), this);
    }
}

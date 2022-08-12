package pers.zhangyang.easyspawnpoint;

import org.bstats.bukkit.Metrics;
import pers.zhangyang.easylibrary.EasyPlugin;

public class EasySpawnPoint extends EasyPlugin {
    @Override
    public void onOpen() {
        new Metrics(this,16102);
    }

    @Override
    public void onClose() {

    }
}

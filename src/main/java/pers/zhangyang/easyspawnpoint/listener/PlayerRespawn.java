package pers.zhangyang.easyspawnpoint.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import pers.zhangyang.easylibrary.annotation.EventListener;
import pers.zhangyang.easyspawnpoint.yaml.SettingYaml;

@EventListener
public class PlayerRespawn implements Listener {
    @EventHandler
    public void on(PlayerRespawnEvent event){

        event.setRespawnLocation(SettingYaml.INSTANCE.getSpawnPoint());


    }
}

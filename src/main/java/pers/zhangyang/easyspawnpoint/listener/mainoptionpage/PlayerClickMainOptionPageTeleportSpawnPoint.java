package pers.zhangyang.easyspawnpoint.listener.mainoptionpage;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import pers.zhangyang.easylibrary.annotation.EventListener;
import pers.zhangyang.easylibrary.annotation.GuiDiscreteButtonHandler;
import pers.zhangyang.easylibrary.other.vault.Vault;
import pers.zhangyang.easylibrary.util.MessageUtil;
import pers.zhangyang.easylibrary.util.PermUtil;
import pers.zhangyang.easylibrary.yaml.MessageYaml;
import pers.zhangyang.easyspawnpoint.domain.Gamer;
import pers.zhangyang.easyspawnpoint.domain.MainOptionPage;
import pers.zhangyang.easyspawnpoint.manager.GamerManager;
import pers.zhangyang.easyspawnpoint.yaml.SettingYaml;

import java.util.List;

@EventListener
public class PlayerClickMainOptionPageTeleportSpawnPoint implements Listener {
    @GuiDiscreteButtonHandler(guiPage = MainOptionPage.class,slot = {22})
    public void on(InventoryClickEvent event){


        MainOptionPage mainOptionPage= (MainOptionPage) event.getInventory().getHolder();

        assert mainOptionPage != null;
        Player player= (Player) event.getWhoClicked();

        Player onlineOwner=mainOptionPage.getOwner().getPlayer();
        if (onlineOwner==null){
            List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.notOnline");
            MessageUtil.sendMessageTo(player, list);
            return;
        }

        if (!onlineOwner.isOp()) {
            Integer perm = PermUtil.getMinNumberPerm("EasyTeleportPoint.teleportSpawnPointInterval.", onlineOwner);
            if (perm == null) {
                perm = 0;
            }
            Gamer gamer = GamerManager.INSTANCE.getGamer(onlineOwner);
            if (gamer.getLastTeleportPointTime() != null && System.currentTimeMillis() - gamer.getLastTeleportPointTime()
                    < perm * 1000L) {

                List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.tooFast");
                MessageUtil.sendMessageTo(player, list);
                return;
            }
        }

        Double cost=SettingYaml.INSTANCE.getNonnegativeDouble("setting.teleportSpawnPointCost");
        if (cost!=null) {
            if (Vault.hook()==null){
                List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.notHookVault");
                MessageUtil.sendMessageTo(player, list);
                return;
            }
            if (Vault.hook().getBalance(onlineOwner)<cost){
                List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.notEnoughVault");
                MessageUtil.sendMessageTo(player, list);
                return;
            }
            Vault.hook().withdrawPlayer(onlineOwner, cost);
        }



        player.teleport(SettingYaml.INSTANCE.getLocationDefault("setting.spawnPoint"));
        Gamer gamer = GamerManager.INSTANCE.getGamer(onlineOwner);
        gamer.setLastTeleportPointTime(System.currentTimeMillis());

        mainOptionPage.refresh();

        List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.teleportSpawnPoint");
        MessageUtil.sendMessageTo(player, list);



    }
}

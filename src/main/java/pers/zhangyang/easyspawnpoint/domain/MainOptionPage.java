package pers.zhangyang.easyspawnpoint.domain;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;
import pers.zhangyang.easylibrary.base.BackAble;
import pers.zhangyang.easylibrary.base.GuiPage;
import pers.zhangyang.easylibrary.base.SingleGuiPageBase;
import pers.zhangyang.easyspawnpoint.yaml.GuiYaml;

import java.util.List;

public class MainOptionPage extends SingleGuiPageBase implements BackAble {
    public MainOptionPage( Player viewer, GuiPage backPage, OfflinePlayer owner) {

        super(GuiYaml.INSTANCE.getString("gui.title.mainOptionPage"), viewer, backPage, owner);
    }

    @Override
    public void refresh() {
        this.inventory.clear();
        ItemStack returnPage= GuiYaml.INSTANCE.getButton("gui.button.mainOptionPage.back");
        this.inventory.setItem(49,returnPage);

        ItemStack teleportSpawnPoint= GuiYaml.INSTANCE.getButton("gui.button.mainOptionPage.teleportSpawnPoint");
        this.inventory.setItem(22,teleportSpawnPoint);
        viewer.openInventory(this.inventory);
    }

    @Override
    public void back() {
        List<String> cmdList= GuiYaml.INSTANCE.getStringList("gui.firstPageBackCommand");
        if (cmdList==null){
            return;
        }
        for (String s:cmdList){
            String[] args=s.split(":");
            if (args.length!=2){
                continue;
            }
            String way=args[0];
            String command=args[1];
            if ("console".equalsIgnoreCase(way)){
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),command);
            }else if ("player".equalsIgnoreCase(way)){
                Bukkit.dispatchCommand(viewer,command);
            }else if ("operator".equalsIgnoreCase(way)){
                viewer.setOp(true);
                Bukkit.dispatchCommand(viewer,command);
                viewer.setOp(false);
            }
        }
    }
}

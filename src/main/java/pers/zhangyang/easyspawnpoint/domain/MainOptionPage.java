package pers.zhangyang.easyspawnpoint.domain;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;
import pers.zhangyang.easylibrary.base.BackAble;
import pers.zhangyang.easylibrary.base.GuiPage;
import pers.zhangyang.easylibrary.base.SingleGuiPageBase;
import pers.zhangyang.easylibrary.util.CommandUtil;
import pers.zhangyang.easyspawnpoint.yaml.GuiYaml;

import java.util.List;

public class MainOptionPage extends SingleGuiPageBase implements BackAble {
    public MainOptionPage( Player viewer, GuiPage backPage, OfflinePlayer owner) {

        super(GuiYaml.INSTANCE.getString("gui.title.mainOptionPage"), viewer, backPage, owner,54);
    }

    @Override
    public void refresh() {
        this.inventory.clear();
        ItemStack returnPage= GuiYaml.INSTANCE.getButtonDefault("gui.button.mainOptionPage.back");
        this.inventory.setItem(49,returnPage);

        ItemStack teleportSpawnPoint= GuiYaml.INSTANCE.getButtonDefault("gui.button.mainOptionPage.teleportSpawnPoint");
        this.inventory.setItem(22,teleportSpawnPoint);
        viewer.openInventory(this.inventory);
    }

    @Override
    public void back() {
        List<String> cmdList= GuiYaml.INSTANCE.getStringList("gui.firstPageBackCommand");
        if (cmdList==null){
            return;
        }
        CommandUtil.dispatchCommandList(viewer,cmdList);
    }

    @Override
    public int getBackSlot() {
        return 49;
    }
}

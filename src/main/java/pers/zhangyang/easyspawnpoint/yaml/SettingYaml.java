package pers.zhangyang.easyspawnpoint.yaml;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.zhangyang.easylibrary.base.YamlBase;

public class SettingYaml extends YamlBase {
    public static final SettingYaml INSTANCE = new SettingYaml();

    private SettingYaml() {
        super("setting.yml");
    }

    @NotNull
    public String getDisplay() {
        String display = getStringDefault("setting.display");
        if(pers.zhangyang.easylibrary.yaml.SettingYaml.class.getClassLoader().getResource("display/"+display)==null){
            display = backUpConfiguration.getString("setting.display");
        }
        assert display != null;
        return display;
    }

    @Nullable
    public Double teleportSpawnPointCost() {
        Double display = getDouble("setting.teleportSpawnPointCost");
        if (display!=null&&display<0){
            display=0.0;
        }
        return display;
    }

    @NotNull
    public Location getSpawnPoint(){
        String worldName=getStringDefault("setting.spawnPoint.worldName");
        Double x=getDoubleDefault("setting.spawnPoint.x");
        Double y=getDoubleDefault("setting.spawnPoint.y");
        Double z=getDoubleDefault("setting.spawnPoint.z");
        Double yawD=getDoubleDefault("setting.spawnPoint.yaw");
        Double pitchD=getDoubleDefault("setting.spawnPoint.pitch");

        World world= Bukkit.getWorld(worldName);
        if (world==null){
            world=Bukkit.getWorld("world");
        }
        float yaw=yawD.floatValue();
        float pitch=pitchD.floatValue();
        return new Location(world,x,y,z,yaw,pitch);
    }

}

package net.tee7even.presents.provider;

import cn.nukkit.Server;
import cn.nukkit.utils.Config;
import net.tee7even.presents.Chest;
import net.tee7even.presents.Presents;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Tee7even
 */
public class YamlProvider implements DataProvider {
    private Config chestConfig;

    public YamlProvider() {
        this.chestConfig = new Config(new File(Presents.getPlugin().getDataFolder(), "chests.yml"), Config.YAML);
    }

    public List<Chest> loadChests() {
        List<Chest> chests = new ArrayList<>();

        Set<String> keys = chestConfig.getKeys();
        for (String key : keys) {
            List<String> coordList = chestConfig.getStringList(key);
            for (String coords : coordList) {
                String[] str = coords.split(",");
                chests.add(new Chest(Double.parseDouble(str[0]), Double.parseDouble(str[1]), Double.parseDouble(str[2]), Server.getInstance().getLevelByName(key), Integer.parseInt(str[3])));
            }
        }

        return chests;
    }

    public void saveChest(double x, double y, double z, String levelName, int facing) {
        String coords = (int)x + "," + (int)y + "," + (int)z +"," + facing;

        List<String> coordList = chestConfig.getStringList(levelName);
        if (coordList == null) {
            coordList = new ArrayList<>();
        }

        coordList.add(coords);
        chestConfig.set(levelName, coordList);
    }

    public void removeChest(double x, double y, double z, String levelName, int facing) {
        String coords = (int) x + "," + (int) y + "," + (int) z +"," + facing;

        List<String> coordList = chestConfig.getStringList(levelName);
        if (coordList == null) {
            coordList = new ArrayList<>();
        }

        coordList.remove(coords);
        chestConfig.set(levelName, coordList);
    }

    public void save() {
        chestConfig.save();
    }
}

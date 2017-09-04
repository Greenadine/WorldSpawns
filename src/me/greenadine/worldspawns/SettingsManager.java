package me.greenadine.worldspawns;

import me.greenadine.worldspawns.portals.Portal;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class SettingsManager {

    private SettingsManager() {
    }

    static SettingsManager instance = new SettingsManager();

    public static SettingsManager getInstance() {
        return instance;
    }

    Plugin p;
    FileConfiguration hub;
    FileConfiguration spawns;
    File hfile;
    File sfile;

    public void setup(Plugin p) {
        hfile = new File(p.getDataFolder(), File.separator + "data" + File.separator + "hub.yml");
        hub = YamlConfiguration.loadConfiguration(hfile);

        sfile = new File(p.getDataFolder(), File.separator + "data" + File.separator + "spawns.yml");
        spawns = YamlConfiguration.loadConfiguration(sfile);

        Portal portal = new Portal((Main) p, null);
        List<String> portalList = portal.getPortals();
        Iterator<String> it = portalList.iterator();
        while (it.hasNext()) {
            String portalName = it.toString();

            File portalFile = new File(p.getDataFolder(), File.separator + portalName);

            if (!portalFile.exists()) {
                try {
                    portalFile.mkdir();
                } catch (Exception e) {
                    p.getLogger().severe("Failed to create portal file for portal '" + portalName + "'.");
                    e.printStackTrace();
                    return;
                }
            } else {

            }

        }
    }

    public FileConfiguration getHub() {
        return hub;
    }

    public void saveHub() {
        try {
            hub.save(hfile);
        } catch (IOException e) {
            Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save hub.yml!");
        }
    }

    public FileConfiguration getSpawns() {
        return spawns;
    }

    public void saveSpawns() {
        try {
            spawns.save(sfile);
        } catch (IOException e) {
            Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save spawns.yml!");
        }
    }

    public void reloadConfig() {
        hub = YamlConfiguration.loadConfiguration(hfile);
        spawns = YamlConfiguration.loadConfiguration(sfile);
    }

    public PluginDescriptionFile getDesc() {
        return p.getDescription();
    }
}
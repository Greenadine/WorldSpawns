package me.greenadine.worldspawns.portals;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.Selection;
import me.greenadine.worldspawns.Lang;
import me.greenadine.worldspawns.Main;
import me.greenadine.worldspawns.SettingsManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Portal {

	/*
	 * WARNING: W.I.P.
	 * TODO: Send informative messages.
	 * TODO: Test methods.
	 */

    private Main main;

    private File file;

    private List<String> portalList = new ArrayList<String>();
    private List<Block> blocks = new ArrayList<Block>();

    public boolean enabled;
    private String name;
    private World world;
    private String type;
    private String owner;
    private int blocksCount;

    private Block mainBlock;

    WorldEditPlugin worldEdit = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");

    private String prefix = ChatColor.translateAlternateColorCodes('&', Lang.PREFIX.toString());

    SettingsManager settings;

    public Portal(Main main, File file) {
        this.main = main;
        this.file = file;
        this.settings = SettingsManager.getInstance();
    }

    // TODO: fix warning
    public void createNew(Player player, String name, String type) {
        enabled = main.getConfig().getBoolean("components.portals");
        if (type.equals("hub")) {
            if (enabled == true) {
                Selection selection = worldEdit.getSelection(player);

                if (selection != null) {
                    Location min = selection.getMinimumPoint();
                    Location max = selection.getMaximumPoint();

                    List<Block> blocks = getPortalBlocks(min, max);

                    if (blocks.size() > 200) {
                        player.sendMessage(
                                prefix + ChatColor.translateAlternateColorCodes('&', Lang.PORTAL_SELECTION_TOO_BIG
                                        .toString().replaceAll("%blocks%", String.valueOf(blocks.size()))));
                        return;
                    } else {
                        if (file != null) {
                            if (portalList.contains(name)) {
                                player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                                        Lang.COMMAND_SETPORTAL_EXISTS.toString().replaceAll("%pluginname%", name)));
                                return;
                            }
                            player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', Lang.PORTAL_CREATING
                                    .toString().replaceAll("%name%", name).replaceAll("%type%", "hub")));
                            portalList.add(name);
                            if (!file.exists()) {
                                try {
                                    file.mkdirs();
                                } catch (Exception e) {
                                    player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                                            Lang.PORTAL_FILE_CREATE_FAIL.toString().replaceAll("%name%", name)
                                                    .replaceAll("%type%", "hub")));
                                    e.printStackTrace();
                                    return;
                                }
                            }

                            FileConfiguration portalConf = YamlConfiguration.loadConfiguration(file);

                            portalConf.set("name", name);
                            portalConf.set("type", "hub");
                            portalConf.set("owner", player.getName());

                            portalConf.createSection("blocks");

                            int i = 0;
                            for (Block block : blocks) {
                                double x = block.getX();
                                double y = block.getY();
                                double z = block.getZ();

                                portalConf.set("blocks." + i + ".x", x);
                                portalConf.set("blocks." + i + ".y", y);
                                portalConf.set("blocks." + i + ".z", z);
                                i++;
                            }
                            try {
                                portalConf.save(file);
                            } catch (IOException e) {
                                player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                                        Lang.PORTAL_FILE_SAVE_FAIL.toString().replaceAll("%name%", name)));
                                e.printStackTrace();
                                return;
                            }
                            this.type = type;
                            this.name = name;
                            this.owner = player.getName();
                            this.blocksCount = i;
                            player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                                    Lang.PORTAL_CREATED_HUB.toString().replaceAll("%name%", name)));
                        } else {
                            player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                                    Lang.PORTAL_FILE_NULL.toString().replaceAll("%name%", name)));

                            try {
                                file.mkdirs();
                            } catch (Exception e) {
                                player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                                        Lang.PORTAL_FILE_CREATE_FAIL.toString().replaceAll("%name%", name)));
                                e.printStackTrace();
                            }
                        }
                    }
                } else {
                    player.sendMessage(prefix
                            + ChatColor.translateAlternateColorCodes('&', Lang.PORTAL_SELECTION_NULL.toString()));
                    return;
                }
            } else {
                player.sendMessage(ChatColor.RED + "Portals feature disabled! Check if WorldEdit is installed.");
                main.consoleMessage(ChatColor.RED + "Portals feature disabled! Check if WorldEdit is installed.");
                return;
            }
        } else {
            return;
        }
    }

    public void createNew(Player player, String name, String type, String spawn) {
        enabled = main.getConfig().getBoolean("components.portals");
        if (type.equals("spawn")) {
            if (enabled == true) {
                Selection selection = worldEdit.getSelection(player);

                if (selection != null) {
                    Location min = selection.getMinimumPoint();
                    Location max = selection.getMaximumPoint();

                    List<Block> blocks = getPortalBlocks(min, max);

                    if (blocks.size() > 200) {
                        player.sendMessage(
                                prefix + ChatColor.translateAlternateColorCodes('&', Lang.PORTAL_SELECTION_TOO_BIG
                                        .toString().replaceAll("%blocks%", String.valueOf(blocks.size()))));
                        return;
                    } else {
                        if (file != null) {
                            if (portalList.contains(name)) {
                                player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                                        Lang.COMMAND_SETPORTAL_EXISTS.toString().replaceAll("%pluginname%", name)));
                                return;
                            }
                            player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', Lang.PORTAL_CREATING
                                    .toString().replaceAll("%name%", name).replaceAll("%type%", "hub")));
                            portalList.add(name);
                            if (!file.exists()) {
                                try {
                                    file.mkdirs();
                                } catch (Exception e) {
                                    player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                                            Lang.PORTAL_FILE_CREATE_FAIL.toString().replaceAll("%name%", name)
                                                    .replaceAll("%type%", "hub")));
                                    e.printStackTrace();
                                    return;
                                }
                            }

                            FileConfiguration portalConf = YamlConfiguration.loadConfiguration(file);

                            portalConf.set("name", name);
                            portalConf.set("type", "hub");
                            portalConf.set("owner", player.getName());
                            portalConf.set("world", world);

                            portalConf.createSection("blocks");

                            int i = 0;
                            for (Block block : blocks) {
                                double x = block.getX();
                                double y = block.getY();
                                double z = block.getZ();

                                portalConf.set("blocks." + i + ".x", x);
                                portalConf.set("blocks." + i + ".y", y);
                                portalConf.set("blocks." + i + ".z", z);
                                try {
                                    portalConf.save(file);
                                } catch (IOException e) {
                                    player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                                            Lang.PORTAL_FILE_SAVE_FAIL.toString()));
                                    e.printStackTrace();
                                }
                                i++;
                                return;
                            }
                            this.type = type;
                            this.name = name;
                            this.owner = player.getName();
                            player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                                    Lang.PORTAL_CREATED_HUB.toString().replaceAll("%name%", name)));
                        } else {
                            player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                                    Lang.PORTAL_FILE_NULL.toString().replaceAll("%name%", name)));

                            try {
                                file.mkdirs();
                            } catch (Exception e) {
                                player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                                        Lang.PORTAL_FILE_CREATE_FAIL.toString().replaceAll("%name%", name)));
                                e.printStackTrace();
                            }
                        }
                    }
                } else {
                    player.sendMessage(prefix
                            + ChatColor.translateAlternateColorCodes('&', Lang.PORTAL_SELECTION_NULL.toString()));
                    return;
                }
            } else {
                player.sendMessage(ChatColor.RED + "Portals feature disabled! Check if WorldEdit is installed.");
                main.consoleMessage(ChatColor.RED + "Portals feature disabled! Check if WorldEdit is installed.");
                return;
            }
        } else {
            return;
        }
    }

    public String getName() {
        return name;
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public List<Block> getPortalBlocks(String portalName) {
        FileConfiguration conf = YamlConfiguration.loadConfiguration(this.getFile(portalName));
        @SuppressWarnings("unchecked")
        List<Block> list = (List<Block>) conf.getList("blocks");
        return list;
    }

    public World getWorld() {
        return world;
    }

    public String getType(String portalName) {
        FileConfiguration conf = YamlConfiguration.loadConfiguration(this.getFile(portalName));
        String type = conf.getString("type");
        return type;
    }

    public Location getPortalLocation() {
        Location location = new Location(world, mainBlock.getX(), mainBlock.getY(), mainBlock.getZ());
        return location;
    }

    public List<String> getPortals() {
        return portalList;
    }

    public Block getMainBlock() {
        return mainBlock;
    }

    public String getType() {
        return type;
    }

    public String getOwner() {
        return owner;
    }

    public File getFile() {
        return file;
    }

    public File getFile(String portalName) {
        File f = new File(main.getDataFolder(), File.separator + "portals" + File.separator + portalName);
        return f;
    }

    private List<Block> getPortalBlocks(Location loc1, Location loc2) {
        int topBlockX = (loc1.getBlockX() < loc2.getBlockX() ? loc2.getBlockX() : loc1.getBlockX());
        int bottomBlockX = (loc1.getBlockX() > loc2.getBlockX() ? loc2.getBlockX() : loc1.getBlockX());

        int topBlockY = (loc1.getBlockY() < loc2.getBlockY() ? loc2.getBlockY() : loc1.getBlockY());
        int bottomBlockY = (loc1.getBlockY() > loc2.getBlockY() ? loc2.getBlockY() : loc1.getBlockY());

        int topBlockZ = (loc1.getBlockZ() < loc2.getBlockZ() ? loc2.getBlockZ() : loc1.getBlockZ());
        int bottomBlockZ = (loc1.getBlockZ() > loc2.getBlockZ() ? loc2.getBlockZ() : loc1.getBlockZ());

        for (int x = bottomBlockX; x <= topBlockX; x++) {
            for (int z = bottomBlockZ; z <= topBlockZ; z++) {
                for (int y = bottomBlockY; y <= topBlockY; y++) {
                    Block block = loc1.getWorld().getBlockAt(x, y, z);

                    blocks.add(block);
                }
            }
        }

        Location loc = new Location(loc1.getWorld(), loc1.getX(), loc1.getY(), loc1.getZ());
        Block b = loc.getBlock();
        this.mainBlock = b;
        return blocks;
    }
}

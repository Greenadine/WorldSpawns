package me.greenadine.worldspawns.portals;

import me.greenadine.worldspawns.Lang;
import me.greenadine.worldspawns.Main;
import me.greenadine.worldspawns.SettingsManager;
import me.greenadine.worldspawns.SpawnFirework;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.io.File;
import java.util.List;

public class PortalListener implements Listener {

	/*
	 * WARNING: W.I.P.
	 */

    private Main main;
    SettingsManager settings;
    private Portal portal;
    private String prefix = ChatColor.translateAlternateColorCodes('&', Lang.PREFIX.toString());

    public PortalListener(Main main) {
        this.main = main;
        settings = SettingsManager.getInstance();
    }

    @EventHandler
    public void onPortalEnter(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        Location ploc = p.getLocation();
        Block b = ploc.getBlock();

        for (String portalName : portal.getPortals()) {
            File portalFile = new File(main.getDataFolder(), File.separator + "portals" + File.separator + portalName);
            FileConfiguration portals = YamlConfiguration.loadConfiguration(portalFile);
            List<Block> blocks = portal.getPortalBlocks(portalName);

            for (Object block : blocks) {
                if (block == b) {
                    if (portal.getType().equals("hub")) {
                        World w = main.getServer().getWorld(settings.getHub().getString("hub.world"));
                        double x2 = settings.getHub().getDouble("hub.x");
                        double y2 = settings.getHub().getDouble("hub.y");
                        double z2 = settings.getHub().getDouble("hub.z");
                        float yaw = settings.getHub().getInt("hub.yaw");
                        float pitch = settings.getHub().getInt("hub.pitch");
                        p.teleport(new Location(w, x2, y2, z2, yaw, pitch));
                        SpawnFirework firework = new SpawnFirework(p, main);
                        if (main.getConfig().getBoolean("firework.enable")) {
                            if (main.getConfig().getBoolean("firework.random")) {
                                firework.spawnRandomFirework();
                            } else {
                                firework.spawnFirework();
                            }
                        } else {
                            // Do nothing.
                        }
                        main.consoleMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                                Lang.COMMAND_HUB_TARGET_TELEPORTED.toString().replaceAll("%target%", p.getName())));
                        p.sendMessage(prefix
                                + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_HUB_TELEPORTED.toString()));
                        if (enableSounds()) {
                            p.playSound(p.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 10F, 1.0F);
                        } else {
                            // Nothing.
                        }
                    }
                    if (portal.getType().equals("spawn")) {
                        String worldname = portals.getString("PORTALNAME.spawn");
                        World w = main.getServer().getWorld(worldname);
                        if (w == null) {
                            p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                                    Lang.COMMAND_SPAWN_WORLD_NULL.toString().replaceAll("%worldname%", worldname)));
                            return;
                        }
                        String xs = settings.getSpawns().getString("spawns." + worldname + ".x");
                        String ys = settings.getSpawns().getString("spawns." + worldname + ".y");
                        String zs = settings.getSpawns().getString("spawns." + worldname + ".z");
                        String yaws = settings.getSpawns().getString("spawns." + worldname + ".yaw");
                        String pitchs = settings.getSpawns().getString("spawns." + worldname + ".pitch");
                        if (xs == null || ys == null || zs == null || yaws == null || pitchs == null) {
                            p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('7',
                                    Lang.COMMAND_SPAWN_NULL.toString().replaceAll("%worldname%", worldname)));
                            return;
                        } else {
                            double x2 = settings.getSpawns().getDouble("spawns." + worldname + ".x");
                            double y2 = settings.getSpawns().getDouble("spawns." + worldname + ".y");
                            double z2 = settings.getSpawns().getDouble("spawns." + worldname + ".z");
                            float yaw = settings.getSpawns().getInt("spawns." + worldname + ".yaw");
                            float pitch = settings.getSpawns().getInt("spawns." + worldname + ".pitch");
                            p.teleport(new Location(w, x2, y2, z2, yaw, pitch));
                            SpawnFirework firework = new SpawnFirework(p, main);
                            if (main.getConfig().getBoolean("firework.enable")) {
                                if (main.getConfig().getBoolean("firework.random")) {
                                    firework.spawnRandomFirework();
                                } else {
                                    firework.spawnFirework();
                                }
                            } else {
                                // Do nothing.
                            }
                            main.consoleMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                                    Lang.COMMAND_SPAWN_TELEPORT_PLAYER.toString().replaceAll("%target%", p.getName())));
                            p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                                    Lang.COMMAND_SPAWN_TELEPORT.toString()));
                            if (enableSounds()) {
                                p.playSound(p.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 10F, 1.0F);
                            } else {
                                // Nothing.
                            }
                        }
                    } else {
                        p.sendMessage(prefix + ChatColor.RED + "INVALID TYPE.");
                        return;
                    }
                }
            }
        }
    }

    private boolean enableSounds() {
        if (main.getConfig().getBoolean("settings.enablesounds"))
            return true;
        if (!main.getConfig().getBoolean("settings.enableSounds")) {
            return false;
        } else {
            main.getConfig().set("settings.enableSounds", Boolean.valueOf(false));
            return false;
        }
    }

}

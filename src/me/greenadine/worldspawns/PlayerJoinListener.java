package me.greenadine.worldspawns;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private Main main;
    SettingsManager settings;

    private String prefix = ChatColor.translateAlternateColorCodes('&', Lang.PREFIX.toString());

    public PlayerJoinListener(Main main) {
        this.main = main;
        settings = SettingsManager.getInstance();
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        FileConfiguration config = main.getConfig();

        if (!p.hasPlayedBefore()) {
            if(config.getString("settings.newbiespawn").equals("hub")) {
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
                p.sendMessage(prefix
                        + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_HUB_TELEPORTED.toString()));
                if (enableSounds()) {
                    p.playSound(p.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 10F, 1.0F);
                } else {
                    // Nothing.
                }
            }
            else if(config.getString("settings.newbiespawn").equals("newbiespawn")) {
                World w = main.getServer().getWorld(settings.getSpawns().getString("newbiespawn.world"));
                if(w != null) {
                    double x = settings.getSpawns().getDouble("newbiespawn.x");
                    double y = settings.getSpawns().getDouble("newbiespawn.y");
                    double z = settings.getSpawns().getDouble("newbiespawn.z");
                    float pitch = settings.getSpawns().getInt("newbiespawn.pitch");
                    float yaw = settings.getSpawns().getInt("newbiespawn.yaw");
                    Location loc = new Location(w, x, y, z, yaw, pitch);

                    p.teleport(loc);
                } else {
                    // Do nothing.
                }
            } else {
                // Do nothing.
            }


        } else {
            // Do nothing.
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

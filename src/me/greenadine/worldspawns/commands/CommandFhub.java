
package me.greenadine.worldspawns.commands;

import me.greenadine.worldspawns.Lang;
import me.greenadine.worldspawns.Main;
import me.greenadine.worldspawns.SettingsManager;
import me.greenadine.worldspawns.SpawnFirework;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

// Referenced classes of package me.greenadine.worldspawns:
//            SettingsManager, Main, Permissions

public class CommandFhub implements CommandExecutor {

    private Main main;
    SettingsManager settings;

    private String prefix = ChatColor.translateAlternateColorCodes('&', Lang.PREFIX.toString());
    private String noperm = prefix + ChatColor.translateAlternateColorCodes('&', Lang.NO_PERMISSION.toString());

    public CommandFhub(Main main) {
        settings = SettingsManager.getInstance();
        this.main = main;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String args[]) {
        if (!(sender instanceof Player)) {
            CommandSender c = sender;

            if (!main.getConfig().getBoolean("settings.enableHub")) {
                c.sendMessage(
                        prefix + ChatColor.translateAlternateColorCodes('&', Lang.COMPONENT_HUB_DISABLED.toString()));
                return true;
            }

            if (args.length == 0) {
                c.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                        Lang.COMMAND_HUB_CONSOLE_DEFINE_PLAYER.toString()));
                return true;
            }

            if (args.length == 1) {
                Player target = main.getServer().getPlayer(args[0]);
                if (target == null) {
                    c.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                            Lang.COMMAND_HUB_TARGET_NULL.toString().replaceAll("%target%", args[0])));
                    return true;
                } else {
                    String world = settings.getHub().getString("hub.world");
                    String xs = settings.getHub().getString("hub.x");
                    String ys = settings.getHub().getString("hub.y");
                    String zs = settings.getHub().getString("hub.z");
                    String yaws = settings.getHub().getString("hub.yaw");
                    String pitchs = settings.getHub().getString("hub.pitch");
                    if (world == null || xs == null || ys == null || zs == null || yaws == null || pitchs == null) {
                        c.sendMessage(
                                prefix + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_HUB_NULL.toString()));
                        return true;
                    } else {
                        World w = main.getServer().getWorld(settings.getHub().getString("hub.world"));
                        double x2 = settings.getHub().getDouble("hub.x");
                        double y2 = settings.getHub().getDouble("hub.y");
                        double z2 = settings.getHub().getDouble("hub.z");
                        float yaw = settings.getHub().getInt("hub.yaw");
                        float pitch = settings.getHub().getInt("hub.pitch");
                        target.teleport(new Location(w, x2, y2, z2, yaw, pitch));
                        SpawnFirework firework = new SpawnFirework(target, main);
                        if (main.getConfig().getBoolean("firework.enable")) {
                            if (main.getConfig().getBoolean("firework.random")) {
                                firework.spawnRandomFirework();
                            } else {
                                firework.spawnFirework();
                            }
                        } else {
                            // Do nothing.
                        }
                        main.consoleMessage(
                                prefix + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_HUB_TARGET_TELEPORTED
                                        .toString().replaceAll("%target%", target.getName())));
                        target.sendMessage(prefix
                                + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_HUB_TELEPORTED.toString()));
                        if (enableSounds()) {
                            target.playSound(target.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 10F, 1.0F);
                        } else {
                            // Nothing.
                        }
                        return true;
                    }
                }
            } else {
                c.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_HUB_CONSOLE_TOOMANYARGS
                        .toString().replaceAll("%label%", label).replaceAll("%lenght%", String.valueOf(args.length))));
                return true;
            }

        } else {
            Player p = (Player) sender;
            if (!p.hasPermission((new Permissions()).fhub)) {
                p.sendMessage(noperm);
                return true;
            }

            if (!main.getConfig().getBoolean("components.hub")) {
                p.sendMessage(
                        prefix + ChatColor.translateAlternateColorCodes('&', Lang.COMPONENT_HUB_DISABLED.toString()));
                return true;
            }

            if (args.length == 0) {
                if (settings.getHub().getString("hub.world") == null) {
                    p.sendMessage(
                            prefix + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_HUB_NULL.toString()));
                    return true;
                } else {
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
            }
            if (args.length == 1) {
                if (!p.hasPermission((new Permissions()).fhub_players)) {
                    p.sendMessage(noperm);
                    return true;
                }
                Player target = main.getServer().getPlayer(args[0]);
                if (target == null) {
                    p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                            Lang.COMMAND_HUB_TARGET_NULL.toString().replaceAll("%target%", args[0])));
                    return true;
                } else {
                    if (target == p) {
                        p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                                Lang.COMMAND_HUB_TARGET_EQUALS_SENDER.toString().replaceAll("%label%", label)));
                        return true;
                    } else {

                        String world = settings.getHub().getString("hub.world");
                        String xs = settings.getHub().getString("hub.x");
                        String ys = settings.getHub().getString("hub.y");
                        String zs = settings.getHub().getString("hub.z");
                        String yaws = settings.getHub().getString("hub.yaw");
                        String pitchs = settings.getHub().getString("hub.pitch");
                        if (world == null || xs == null || ys == null || zs == null || yaws == null || pitchs == null) {
                            p.sendMessage(prefix
                                    + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_HUB_NULL.toString()));
                            return true;
                        } else {
                            World w = main.getServer().getWorld(settings.getHub().getString("hub.world"));
                            double x2 = settings.getHub().getDouble("hub.x");
                            double y2 = settings.getHub().getDouble("hub.y");
                            double z2 = settings.getHub().getDouble("hub.z");
                            float yaw = settings.getHub().getInt("hub.yaw");
                            float pitch = settings.getHub().getInt("hub.pitch");
                            target.teleport(new Location(w, x2, y2, z2, yaw, pitch));
                            SpawnFirework firework = new SpawnFirework(target, main);
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
                                    + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_HUB_TARGET_TELEPORTED
                                    .toString().replaceAll("%target%", target.getName())));
                            target.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                                    Lang.COMMAND_HUB_TELEPORTED.toString()));
                            if (enableSounds()) {
                                target.playSound(target.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 10F, 1.0F);
                            } else {
                                // Nothing.
                            }
                        }
                    }
                }
            }
        }
        return false;
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

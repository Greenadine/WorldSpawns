
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

public class CommandFspawn implements CommandExecutor {

    private Main main;
    SettingsManager settings;

    private String prefix = ChatColor.translateAlternateColorCodes('&', Lang.PREFIX.toString());
    private String noperm = prefix + ChatColor.translateAlternateColorCodes('&', Lang.NO_PERMISSION.toString());

    public CommandFspawn(Main main) {
        settings = SettingsManager.getInstance();
        this.main = main;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String args[]) {
        if (!(sender instanceof Player)) {
            if (!main.getConfig().getBoolean("components.spawns")) {
                sender.sendMessage(prefix
                        + ChatColor.translateAlternateColorCodes('&', Lang.COMPONENT_SPAWNS_DISABLED.toString()));
                return true;
            }

            if (args.length == 0) {
                sender.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_SPAWN_TOOFEWARGS
                        .toString().replaceAll("%label%", label).replaceAll("%lenght%", String.valueOf(args.length))));
                return true;
            }
            if (args.length == 1) {
                sender.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_SPAWN_TOOFEWARGS
                        .toString().replaceAll("%label%", label).replaceAll("%lenght%", String.valueOf(args.length))));
                return true;
            } else {
                if (args.length == 2) {
                    World w = main.getServer().getWorld(args[1]);
                    if (w == null) {
                        sender.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                                Lang.COMMAND_SPAWN_WORLD_NULL.toString().replaceAll("%worldname%", args[1])));
                        return true;
                    }
                    Player target = main.getServer().getPlayer(args[0]);
                    if (target == null) {
                        sender.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                                Lang.COMMAND_SPAWN_TARGET_NULL.toString().replaceAll("%target%", args[0])));
                        return true;
                    }
                    String worldname = args[1];
                    String xs = settings.getSpawns().getString("spawns." + worldname + ".x");
                    String ys = settings.getSpawns().getString("spawns." + worldname + ".y");
                    String zs = settings.getSpawns().getString("spawns." + worldname + ".z");
                    String yaws = settings.getSpawns().getString("spawns." + worldname + ".yaw");
                    String pitchs = settings.getSpawns().getString("spawns." + worldname + ".pitch");
                    if (xs == null || ys == null || zs == null || yaws == null || pitchs == null) {
                        sender.sendMessage(prefix + ChatColor.translateAlternateColorCodes('7',
                                Lang.COMMAND_SPAWN_NULL.toString().replaceAll("%worldname%", worldname)));
                        return true;
                    } else {
                        double x2 = settings.getSpawns().getDouble("spawns." + worldname + ".x");
                        double y2 = settings.getSpawns().getDouble("spawns." + worldname + ".y");
                        double z2 = settings.getSpawns().getDouble("spawns." + worldname + ".z");
                        float yaw = settings.getSpawns().getInt("spawns." + worldname + ".yaw");
                        float pitch = settings.getSpawns().getInt("spawns." + worldname + ".pitch");
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
                                prefix + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_SPAWN_TELEPORT_PLAYER
                                        .toString().replaceAll("%target%", target.getName())));
                        target.sendMessage(prefix
                                + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_SPAWN_TELEPORT.toString()));
                        if (enableSounds()) {
                            target.playSound(target.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 10F, 1.0F);
                        } else {
                            // Nothing.
                        }
                        return true;
                    }
                }
                if (args.length > 2) {
                    sender.sendMessage(prefix
                            + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_SPAWN_TOOMANYARGS.toString()
                            .replaceAll("%label%", label).replaceAll("%lenght%", String.valueOf(args.length))));
                    return true;
                }
            }
        } else {
            Player p = (Player) sender;

            if (!p.hasPermission((new Permissions()).fspawn)) {
                p.sendMessage(noperm);
                return true;
            }

            if (!main.getConfig().getBoolean("components.spawns")) {
                p.sendMessage(prefix
                        + ChatColor.translateAlternateColorCodes('&', Lang.COMPONENT_SPAWNS_DISABLED.toString()));
                return true;
            }

            if (args.length > 2) {
                sender.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_SPAWN_TOOMANYARGS
                        .toString().replaceAll("%label%", label).replaceAll("%lenght%", String.valueOf(args.length))));
                return true;
            }

            if (args.length == 0) {
                String worldname = p.getLocation().getWorld().getName();
                String xs = settings.getSpawns().getString("spawns." + worldname + ".x");
                String ys = settings.getSpawns().getString("spawns." + worldname + ".y");
                String zs = settings.getSpawns().getString("spawns." + worldname + ".z");
                String yaws = settings.getSpawns().getString("spawns." + worldname + ".yaw");
                String pitchs = settings.getSpawns().getString("spawns." + worldname + ".pitch");
                if (xs == null || ys == null || zs == null || yaws == null || pitchs == null) {
                    p.sendMessage(prefix
                            + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_SPAWN_CURRENT_NULL.toString()));
                    return true;
                } else {
                    World w = main.getServer().getWorld(worldname);
                    double x = settings.getSpawns().getDouble("spawns." + worldname + ".x");
                    double y = settings.getSpawns().getDouble("spawns." + worldname + ".y");
                    double z = settings.getSpawns().getDouble("spawns." + worldname + ".z");
                    float yaw = settings.getSpawns().getInt("spawns." + worldname + ".yaw");
                    float pitch = settings.getSpawns().getInt("spawns." + worldname + ".pitch");
                    p.teleport(new Location(w, x, y, z, yaw, pitch));
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
                            + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_SPAWN_TELEPORT.toString()));
                    if (enableSounds()) {
                        p.playSound(p.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 10F, 1.0F);
                    } else {
                        // Nothing.
                    }
                    return true;
                }
            }
            if (args.length == 1) {
                if (!p.hasPermission((new Permissions()).fspawn_other)) {
                    p.sendMessage(noperm);
                    return true;
                }
                String worldname = args[0];
                World w = main.getServer().getWorld(worldname);

                if (w == null) {
                    p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                            Lang.COMMAND_SPAWN_WORLD_NULL.toString().replaceAll("%worldname%", args[0])));
                    return true;
                }

                if (!p.hasPermission("worldspawns.spawn.world." + worldname)
                        && !p.hasPermission((new Permissions()).spawn_allworlds) && w != p.getLocation().getWorld()) {
                    p.sendMessage(noperm);
                    return true;
                }
                String xs = settings.getSpawns().getString("spawns." + worldname + ".x");
                String ys = settings.getSpawns().getString("spawns." + worldname + ".y");
                String zs = settings.getSpawns().getString("spawns." + worldname + ".z");
                String yaws = settings.getSpawns().getString("spawns." + worldname + ".yaw");
                String pitchs = settings.getSpawns().getString("spawns." + worldname + ".pitch");
                if (xs == null || ys == null || zs == null || yaws == null || pitchs == null) {
                    p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                            Lang.COMMAND_SPAWN_WORLD_NULL.toString().replaceAll("%worldname%", worldname)));
                    return true;
                } else {
                    double x = settings.getSpawns().getDouble("spawns." + worldname + ".x");
                    double y = settings.getSpawns().getDouble("spawns." + worldname + ".y");
                    double z = settings.getSpawns().getDouble("spawns." + worldname + ".z");
                    float yaw = settings.getSpawns().getInt("spawns." + worldname + ".yaw");
                    float pitch = settings.getSpawns().getInt("spawns." + worldname + ".pitch");
                    p.teleport(new Location(w, x, y, z, yaw, pitch));
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
                            + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_SPAWN_TELEPORT.toString()));
                    if (enableSounds()) {
                        p.playSound(p.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 10F, 1.0F);
                    } else {
                        // Nothing.
                    }
                    return true;
                }
            }
            if (args.length == 2) {
                if (!p.hasPermission((new Permissions()).fspawn_players)) {
                    p.sendMessage(noperm);
                    return true;
                }
                String worldname = args[0];
                World w = main.getServer().getWorld(worldname);
                if (w == null) {
                    p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                            Lang.COMMAND_SPAWN_WORLD_NULL.toString().replaceAll("%worldname%", args[0])));
                    return true;
                }
                Player target = main.getServer().getPlayer(args[1]);
                if (target == null) {
                    p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                            Lang.COMMAND_SPAWN_TARGET_NULL.toString().replaceAll("%target%", args[1])));
                    return true;
                }

                if (target == p) {
                    p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                            Lang.COMMAND_SPAWN_TARGET_EQUALS_SENDER.toString()));
                    return true;
                }

                String xs = settings.getSpawns().getString("spawns." + worldname + ".x").toString();
                String ys = settings.getSpawns().getString("spawns." + worldname + ".y").toString();
                String zs = settings.getSpawns().getString("spawns." + worldname + ".z").toString();
                String yaws = settings.getSpawns().getString("spawns." + worldname + ".yaw").toString();
                String pitchs = settings.getSpawns().getString("spawns." + worldname + ".pitch").toString();
                if (xs == null || ys == null || zs == null || yaws == null || pitchs == null) {
                    p.sendMessage((new StringBuilder(String.valueOf(prefix))).append(ChatColor.RED)
                            .append("The spawn for world '").append(worldname).append("' is not set yet!").toString());
                    return true;
                } else {
                    double x2 = settings.getSpawns().getDouble("spawns." + worldname + ".x");
                    double y2 = settings.getSpawns().getDouble("spawns." + worldname + ".y");
                    double z2 = settings.getSpawns().getDouble("spawns." + worldname + ".z");
                    float yaw = settings.getSpawns().getInt("spawns." + worldname + ".yaw");
                    float pitch = settings.getSpawns().getInt("spawns." + worldname + ".pitch");
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
                            + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_SPAWN_TELEPORT_PLAYER.toString()
                            .replaceAll("%target%", target.getName()).replaceAll("%worldname%", worldname)));
                    target.sendMessage(prefix
                            + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_SPAWN_TELEPORT.toString()));
                    if (enableSounds()) {
                        target.playSound(target.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 10F, 1.0F);
                    } else {
                        // Nothing.
                    }
                    return true;
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

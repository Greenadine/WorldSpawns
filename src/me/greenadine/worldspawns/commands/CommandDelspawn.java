
package me.greenadine.worldspawns.commands;

import me.greenadine.worldspawns.Lang;
import me.greenadine.worldspawns.Main;
import me.greenadine.worldspawns.SettingsManager;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

// Referenced classes of package me.greenadine.worldspawns:
//            SettingsManager, Main, Permissions

public class CommandDelspawn implements CommandExecutor {

    private Main main;
    SettingsManager settings;

    private String prefix = ChatColor.translateAlternateColorCodes('&', Lang.PREFIX.toString());
    private String noperm = prefix + ChatColor.translateAlternateColorCodes('&', Lang.NO_PERMISSION.toString());

    public CommandDelspawn(Main main) {
        settings = SettingsManager.getInstance();
        this.main = main;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String args[]) {
        if (!(sender instanceof Player)) {
            CommandSender c = sender;
            if (args.length == 0) {
                c.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                        Lang.COMMAND_DELSPAWN_INCORRECT_USAGE.toString().replaceAll("%label%", label)));
            } else {
                if (args.length == 1) {
                    World w = main.getServer().getWorld(args[0]);
                    if (w == null) {
                        c.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                                Lang.COMMAND_DELSPAWN_WORLD_NULL.toString().replaceAll("%worldname%", args[0])));
                        return true;
                    }
                    String worldname = args[0];
                    String xs = settings.getSpawns().getString("spawns.") + worldname + ".x";
                    String ys = settings.getSpawns().getString("spawns.") + worldname + ".y";
                    String zs = settings.getSpawns().getString("spawns.") + worldname + ".z";
                    String yaws = settings.getSpawns().getString("spawns.") + worldname + ".yaw";
                    String pitchs = settings.getSpawns().getString("spawns.") + worldname + ".pitch";
                    if (xs == null || ys == null || zs == null || yaws == null || pitchs == null) {
                        c.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                                Lang.COMMAND_DELSPAWN_SPAWN_NULL.toString().replaceAll("%worldname%", worldname)));
                        return true;
                    } else {
                        settings.getSpawns().set("spawns." + worldname, null);
                        c.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                                Lang.COMMAND_DELSPAWN_SPAWN_DELETED.toString().replaceAll("%worldname%", worldname)));
                        return true;
                    }
                }
            }
        } else {
            Player p = (Player) sender;

            if (!p.hasPermission((new Permissions()).spawn_delete)) {
                p.sendMessage(noperm);
                return true;
            }

            if (!main.getConfig().getBoolean("components.spawns")) {
                p.sendMessage(prefix
                        + ChatColor.translateAlternateColorCodes('&', Lang.COMPONENT_SPAWNS_DISABLED.toString()));
                return true;
            }

            if (args.length > 1) {
                p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_DELSPAWN_TOOMANYARGS
                        .toString().replaceAll("%label%", label).replaceAll("%lenght%", String.valueOf(args.length))));
                return true;
            }
            if (args.length == 0) {
                World w = p.getLocation().getWorld();
                String worldname = w.getName();
                String xs = settings.getSpawns().getString("spawns.") + worldname + ".x";
                String ys = settings.getSpawns().getString("spawns.") + worldname + ".y";
                String zs = settings.getSpawns().getString("spawns.") + worldname + ".z";
                String yaws = settings.getSpawns().getString("spawns.") + worldname + ".yaw";
                String pitchs = settings.getSpawns().getString("spawns.") + worldname + ".pitch";
                if (xs == null || ys == null || zs == null || yaws == null || pitchs == null) {
                    p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                            Lang.COMMAND_DELSPAWN_SPAWN_NULL.toString().replaceAll("%worldname%", worldname)));
                    return true;
                } else {
                    settings.getSpawns().set("spawns." + worldname, null);
                    p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                            Lang.COMMAND_DELSPAWN_SPAWN_DELETED.toString().replaceAll("%worldname%", worldname)));
                    return true;
                }
            }
            if (args.length == 1) {
                World w = main.getServer().getWorld(args[0]);
                if (w == null) {
                    p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                            Lang.COMMAND_DELSPAWN_WORLD_NULL.toString().replaceAll("%worldname%", args[0])));
                    return true;
                }

                String worldname = args[0];
                String xs = settings.getSpawns().getString("spawns.") + worldname + ".x";
                String ys = settings.getSpawns().getString("spawns.") + worldname + ".y";
                String zs = settings.getSpawns().getString("spawns.") + worldname + ".z";
                String yaws = settings.getSpawns().getString("spawns.") + worldname + ".yaw";
                String pitchs = settings.getSpawns().getString("spawns.") + worldname + ".pitch";
                if (xs == null || ys == null || zs == null || yaws == null || pitchs == null) {
                    p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                            Lang.COMMAND_DELSPAWN_SPAWN_NULL.toString().replaceAll("%worldname%", worldname)));
                    return true;
                } else {
                    settings.getSpawns().set("spawns." + worldname, null);
                    p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                            Lang.COMMAND_DELSPAWN_SPAWN_DELETED.toString().replaceAll("%worldname%", worldname)));
                    return true;
                }
            }
        }
        return false;
    }
}

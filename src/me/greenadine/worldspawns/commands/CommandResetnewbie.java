package me.greenadine.worldspawns.commands;

import me.greenadine.worldspawns.Lang;
import me.greenadine.worldspawns.Main;
import me.greenadine.worldspawns.SettingsManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandResetnewbie implements CommandExecutor {

    private Main main;
    SettingsManager settings;

    private String prefix = ChatColor.translateAlternateColorCodes('&', Lang.PREFIX.toString());
    private String noperm = prefix + ChatColor.translateAlternateColorCodes('&', Lang.NO_PERMISSION.toString());

    public CommandResetnewbie(Main main) {
        settings = SettingsManager.getInstance();
        this.main = main;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            if (!main.getConfig().getBoolean("components.spawns")) {
                sender.sendMessage(
                        prefix + ChatColor.translateAlternateColorCodes('&', Lang.COMPONENT_SPAWNS_DISABLED.toString()));
                return true;
            }
            if (args.length == 0) {
                String world = settings.getSpawns().getString("newbiespawn.world");
                String xs = settings.getSpawns().getString("newbiespawn.x");
                String ys = settings.getSpawns().getString("newbiespawn.y");
                String zs = settings.getSpawns().getString("newbiespawn.z");
                String yaws = settings.getSpawns().getString("newbiespawn.yaw");
                String pitchs = settings.getSpawns().getString("newbiespawn.pitch");
                if (world == null || xs == null || ys == null || zs == null || yaws == null || pitchs == null) {
                    sender.sendMessage(
                            prefix + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_RESETNEWBIE_NULL.toString()));
                    return true;
                } else {
                    settings.getSpawns().set("newbiespawn", null);
                    settings.saveSpawns();
                    sender.sendMessage(prefix
                            + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_RESETNEWBIE_RESET.toString()));
                    return true;
                }
            }
            sender.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                    Lang.COMMAND_RESETHUB_TOOMANYARGS.toString().replaceAll("%label%", label)));
        } else {
            Player p = (Player) sender;
            if (!p.hasPermission((new Permissions()).newbie_reset)) {
                p.sendMessage(noperm);
                return true;
            }
            if (!main.getConfig().getBoolean("components.spawns")) {
                p.sendMessage(
                        prefix + ChatColor.translateAlternateColorCodes('&', Lang.COMPONENT_SPAWNS_DISABLED.toString()));
                return true;
            }
            if (args.length == 0) {
                String world = settings.getSpawns().getString("newbiespawn.world");
                String xs = settings.getSpawns().getString("newbiespawn.x");
                String ys = settings.getSpawns().getString("newbiespawn.y");
                String zs = settings.getSpawns().getString("newbiespawn.z");
                String yaws = settings.getSpawns().getString("newbiespawn.yaw");
                String pitchs = settings.getSpawns().getString("newbiespawn.pitch");
                if (world == null || xs == null || ys == null || zs == null || yaws == null || pitchs == null) {
                    p.sendMessage(
                            prefix + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_RESETNEWBIE_NULL.toString()));
                    return true;
                } else {
                    settings.getSpawns().set("newbiespawn", null);
                    p.sendMessage(prefix
                            + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_RESETNEWBIE_RESET.toString()));
                    return true;
                }
            }
            p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                    Lang.COMMAND_RESETNEWBIE_TOOMANYARGS.toString().replaceAll("%label%", label)));
        }
        return false;
    }

}

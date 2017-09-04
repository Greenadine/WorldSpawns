
package me.greenadine.worldspawns.commands;

import me.greenadine.worldspawns.Lang;
import me.greenadine.worldspawns.Main;
import me.greenadine.worldspawns.SettingsManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

// Referenced classes of package me.greenadine.worldspawns:
//            SettingsManager, Main, Permissions

public class CommandResethub implements CommandExecutor {

    private Main main;
    SettingsManager settings;

    private String prefix = ChatColor.translateAlternateColorCodes('&', Lang.PREFIX.toString());
    private String noperm = prefix + ChatColor.translateAlternateColorCodes('&', Lang.NO_PERMISSION.toString());

    public CommandResethub(Main main) {
        settings = SettingsManager.getInstance();
        this.main = main;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String args[]) {
        if (!(sender instanceof Player)) {
            if (!main.getConfig().getBoolean("components.hub")) {
                sender.sendMessage(
                        prefix + ChatColor.translateAlternateColorCodes('&', Lang.COMPONENT_HUB_DISABLED.toString()));
                return true;
            }
            if (args.length == 0) {
                String world = settings.getHub().getString("hub.world");
                String xs = settings.getHub().getString("hub.x");
                String ys = settings.getHub().getString("hub.y");
                String zs = settings.getHub().getString("hub.z");
                String yaws = settings.getHub().getString("hub.yaw");
                String pitchs = settings.getHub().getString("hub.pitch");
                if (world == null || xs == null || ys == null || zs == null || yaws == null || pitchs == null) {
                    sender.sendMessage(
                            prefix + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_HUB_NULL.toString()));
                    return true;
                } else {
                    settings.getHub().set("hub", null);
                    settings.saveHub();
                    sender.sendMessage(prefix
                            + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_RESETHUB_RESET.toString()));
                    return true;
                }
            }
            sender.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                    Lang.COMMAND_RESETHUB_TOOMANYARGS.toString().replaceAll("%label%", label)));
            return true;
        } else {
            Player p = (Player) sender;
            if (!p.hasPermission((new Permissions()).hub_reset)) {
                p.sendMessage(noperm);
                return true;
            }
            if (!main.getConfig().getBoolean("components.hub")) {
                p.sendMessage(
                        prefix + ChatColor.translateAlternateColorCodes('&', Lang.COMPONENT_HUB_DISABLED.toString()));
                return true;
            }
            if (args.length == 0) {
                String world = settings.getHub().getString("hub.world");
                String xs = settings.getHub().getString("hub.x");
                String ys = settings.getHub().getString("hub.y");
                String zs = settings.getHub().getString("hub.z");
                String yaws = settings.getHub().getString("hub.yaw");
                String pitchs = settings.getHub().getString("hub.pitch");
                if (world == null || xs == null || ys == null || zs == null || yaws == null || pitchs == null) {
                    p.sendMessage(
                            prefix + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_HUB_NULL.toString()));
                    return true;
                } else {
                    settings.getHub().set("hub", null);
                    p.sendMessage(prefix
                            + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_RESETHUB_RESET.toString()));
                    return true;
                }
            }
            p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                    Lang.COMMAND_RESETHUB_TOOMANYARGS.toString().replaceAll("%label%", label)));
            return true;
        }
    }
}

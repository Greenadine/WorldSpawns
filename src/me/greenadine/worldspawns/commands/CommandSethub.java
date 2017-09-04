
package me.greenadine.worldspawns.commands;

import me.greenadine.worldspawns.Lang;
import me.greenadine.worldspawns.Main;
import me.greenadine.worldspawns.SettingsManager;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

// Referenced classes of package me.greenadine.worldspawns:
//            SettingsManager, Main, Permissions

public class CommandSethub implements CommandExecutor {

    private Main main;
    SettingsManager settings;

    private String prefix = ChatColor.translateAlternateColorCodes('&', Lang.PREFIX.toString());
    private String noperm = prefix + ChatColor.translateAlternateColorCodes('&', Lang.NO_PERMISSION.toString());

    public CommandSethub(Main main) {
        settings = SettingsManager.getInstance();
        this.main = main;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String args[]) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_ONLY_PLAYERS.toString()));
        } else {
            Player p = (Player) sender;
            if (!p.hasPermission((new Permissions()).hub_set)) {
                p.sendMessage(noperm);
                return true;
            }

            if (!main.getConfig().getBoolean("components.hub")) {
                sender.sendMessage(
                        prefix + ChatColor.translateAlternateColorCodes('&', Lang.COMPONENT_HUB_DISABLED.toString()));
                return true;
            }

            if (args.length == 0) {
                if (!p.isOnGround()) {
                    if (main.getConfig().getBoolean("settings.allowHubInAir")) {
                        settings.getHub().createSection("hub");
                        settings.getHub().set("hub.world", p.getLocation().getWorld().getName());
                        settings.getHub().set("hub.x", Double.valueOf(p.getLocation().getX()));
                        settings.getHub().set("hub.y", Double.valueOf(p.getLocation().getY()));
                        settings.getHub().set("hub.z", Double.valueOf(p.getLocation().getZ()));
                        settings.getHub().set("hub.yaw", Float.valueOf(p.getLocation().getYaw()));
                        settings.getHub().set("hub.pitch", Float.valueOf(p.getLocation().getPitch()));
                        settings.saveHub();
                        p.sendMessage(prefix
                                + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_SETHUB_SET.toString()));
                        p.sendMessage("  " + ChatColor.translateAlternateColorCodes('&',
                                Lang.COMMAND_SETHUB_SET_INAIR.toString()));
                        playSound(p, Sound.ENTITY_FIREWORK_LARGE_BLAST_FAR);
                        return true;
                    }
                    if (!main.getConfig().getBoolean("settings.allowSpawnInAir")) {
                        p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                                Lang.COMMAND_SETHUB_SET_INAIR_DENY.toString()));
                        return true;
                    } else {
                        p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                                Lang.COMMAND_SETHUB_SET_INAIR_DENY.toString()));
                        main.getConfig().set("settings.allowHubInAir", Boolean.valueOf(false));
                        main.saveConfig();
                        return true;
                    }
                } else {
                    settings.getHub().createSection("hub");
                    settings.getHub().set("hub.world", p.getLocation().getWorld().getName());
                    settings.getHub().set("hub.x", Double.valueOf(p.getLocation().getX()));
                    settings.getHub().set("hub.y", Double.valueOf(p.getLocation().getY()));
                    settings.getHub().set("hub.z", Double.valueOf(p.getLocation().getZ()));
                    settings.getHub().set("hub.yaw", Float.valueOf(p.getLocation().getYaw()));
                    settings.getHub().set("hub.pitch", Float.valueOf(p.getLocation().getPitch()));
                    settings.saveHub();
                    p.sendMessage(
                            prefix + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_SETHUB_SET.toString()));
                    playSound(p, Sound.ENTITY_FIREWORK_LARGE_BLAST_FAR);
                    return true;
                }
            }
            p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_RESETHUB_TOOMANYARGS
                    .toString().replaceAll("%label%", label).replaceAll("%lenght%", String.valueOf(args.length))));
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

    private void playSound(Player player, Sound sound) {
        if (enableSounds())
            player.playSound(player.getLocation(), sound, 10F, 1.0F);
    }
}

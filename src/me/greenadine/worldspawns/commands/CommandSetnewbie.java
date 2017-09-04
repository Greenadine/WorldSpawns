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

public class CommandSetnewbie implements CommandExecutor {

    private Main main;
    SettingsManager settings;

    private String prefix = ChatColor.translateAlternateColorCodes('&', Lang.PREFIX.toString());
    private String noperm = prefix + ChatColor.translateAlternateColorCodes('&', Lang.NO_PERMISSION.toString());

    public CommandSetnewbie(Main main) {
        settings = SettingsManager.getInstance();
        this.main = main;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_ONLY_PLAYERS.toString()));
        } else {
            Player p = (Player) sender;

            if (!p.hasPermission((new Permissions()).newbie_set)) {
                p.sendMessage(noperm);
                return true;
            }

            if (!main.getConfig().getBoolean("components.spawns")) {
                sender.sendMessage(prefix
                        + ChatColor.translateAlternateColorCodes('&', Lang.COMPONENT_SPAWNS_DISABLED.toString()));
                return true;
            }

            if (args.length != 0) {
                p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_SETNEWBIE_TOOMANYARGS
                        .toString().replaceAll("%label%", label).replaceAll("%lenght", String.valueOf(args.length))));
                return true;
            }

            if (args.length == 0) {
                if (!p.isOnGround()) {
                    if (!main.getConfig().getBoolean("settings.allowSpawnInAir")) {
                        p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                                Lang.COMMAND_SETHUB_SET_INAIR_DENY.toString()));
                        return true;
                    }

                    settings.getSpawns().set("newbiespawn.world", p.getLocation().getWorld().getName());
                    settings.getSpawns().set("newbiespawn.x", Double.valueOf(p.getLocation().getX()));
                    settings.getSpawns().set("newbiespawn.y", Double.valueOf(p.getLocation().getY()));
                    settings.getSpawns().set("newbiespawn.z", Double.valueOf(p.getLocation().getZ()));
                    settings.getSpawns().set("newbiespawn.yaw", Float.valueOf(p.getLocation().getYaw()));
                    settings.getSpawns().set("newbiespawn.pitch", Float.valueOf(p.getLocation().getPitch()));
                    settings.saveSpawns();
                    p.sendMessage(prefix
                            + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_SETNEWBIE_SET.toString()));
                    p.sendMessage("  " + ChatColor.translateAlternateColorCodes('&',
                            Lang.COMMAND_SETNEWBIE_SET_INAIR.toString()));
                    playSound(p, Sound.ENTITY_FIREWORK_LARGE_BLAST_FAR);
                    return true;
                } else {
                    settings.getSpawns().set("newbiespawn.world", p.getLocation().getWorld().getName());
                    settings.getSpawns().set("newbiespawn.x", Double.valueOf(p.getLocation().getX()));
                    settings.getSpawns().set("newbiespawn.y", Double.valueOf(p.getLocation().getY()));
                    settings.getSpawns().set("newbiespawn.z", Double.valueOf(p.getLocation().getZ()));
                    settings.getSpawns().set("newbiespawn.yaw", Float.valueOf(p.getLocation().getYaw()));
                    settings.getSpawns().set("newbiespawn.pitch", Float.valueOf(p.getLocation().getPitch()));
                    settings.saveSpawns();
                    p.sendMessage(
                            prefix + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_SETNEWBIE_SET.toString()));
                    playSound(p, Sound.ENTITY_FIREWORK_LARGE_BLAST_FAR);
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

    private void playSound(Player player, Sound sound) {
        if (enableSounds())
            player.playSound(player.getLocation(), sound, 10F, 1.0F);
    }

}

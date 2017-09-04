package me.greenadine.worldspawns.commands;

import me.greenadine.worldspawns.Lang;
import me.greenadine.worldspawns.Main;
import me.greenadine.worldspawns.SettingsManager;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandWorldspawns implements CommandExecutor {

    private final Main main;
    SettingsManager settings;
    private final String prefix = ChatColor.translateAlternateColorCodes('&', Lang.PREFIX.toString());
    private final String noperm = prefix + ChatColor.translateAlternateColorCodes('&', Lang.NO_PERMISSION.toString());
    private final String header = ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_HELP_HEADER.toString());
    private final String headerNote = ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_HELP_NOTE.toString());

    public CommandWorldspawns(Main main) {
        settings = SettingsManager.getInstance();
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String args[]) {

        if (!(sender instanceof Player)) {
            final CommandSender c = sender;
            if (args.length == 0) {
                c.sendMessage(ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_NOARGS.toString()));
                return true;
            }
            if (args.length == 1) {
                if (args[0].equals("help") || args[0].equals("?")) {
                    c.sendMessage(header);
                    c.sendMessage(headerNote);

                    if(main.getConfig().getBoolean("components.spawns")) {
                        c.sendMessage(l(Lang.COMMAND_HELP_SPAWNS_DESC.toString().replaceAll("%label%", label)));
                    }
                    if(main.getConfig().getBoolean("components.hub")) {
                        c.sendMessage(l(Lang.COMMAND_HELP_HUB_DESC.toString().replaceAll("%label%", label)));
                    }
                    if(main.getConfig().getBoolean("components.hub") || main.getConfig().getBoolean("components.spawns")) {
                        c.sendMessage(l(Lang.COMMAND_HELP_CHECK_DESC.toString().replaceAll("%label%", label)));
                    }
                    c.sendMessage(l(Lang.COMMAND_HELP_RELOAD_DESC.toString().replaceAll("%label%", label)));
                    c.sendMessage(l(Lang.COMMAND_HELP_ENABLE_DESC.toString().replaceAll("%label%", label)));
                    return true;
                }
                if (args[0].equals("spawns")) {
                    if (!main.getConfig().getBoolean("components.spawns")) {
                        sender.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                                Lang.COMPONENT_SPAWNS_DISABLED.toString()));
                        return true;
                    }

                    c.sendMessage(header);
                    c.sendMessage(headerNote);
                    c.sendMessage(l(Lang.COMMAND_HELP_SPAWNS_SPAWN_DESC.toString()));
                    c.sendMessage(l(Lang.COMMAND_HELP_SPAWNS_FSPAWN_DESC.toString()));
                    c.sendMessage(l(Lang.COMMAND_HELP_SPAWNS_SETSPAWN_DESC.toString()));
                    c.sendMessage(l(Lang.COMMAND_HELP_SPAWNS_DELSPAWN_DESC.toString()));
                    c.sendMessage(l(Lang.COMMAND_HELP_SPAWNS_SETNEWBIE_DESC.toString()));
                    c.sendMessage(l(Lang.COMMAND_HELP_SPAWNS_RESETNEWBIE_DESC.toString()));
                    c.sendMessage(l(Lang.COMMAND_HELP_SPAWNS_RESET_DESC.toString().replaceAll("%label%", label)));
                    return true;
                }
                if(args[0].equals("hub")) {
                    if (!main.getConfig().getBoolean("components.hub")) {
                        sender.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                                Lang.COMPONENT_HUB_DISABLED.toString()));
                        return true;
                    }

                    c.sendMessage(l(Lang.COMMAND_HELP_HUB_HUB_DESC.toString().replaceAll("%label%", label)));
                    c.sendMessage(l(Lang.COMMAND_HELP_HUB_FHUB_DESC.toString().replaceAll("%label%", label)));
                    c.sendMessage(l(Lang.COMMAND_HELP_HUB_SETHUB_DESC.toString().replaceAll("%label%", label)));
                    c.sendMessage(l(Lang.COMMAND_HELP_HUB_RESETHUB_DESC.toString().replaceAll("%label%", label)));
                }
                if (args[0].equals("reload")) {
                    try {
                        main.reloadConfig();
                    } catch (final Exception e) {
                        c.sendMessage(ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_RELOAD_FAIL.toString()));
                        Bukkit.getServer().getLogger().severe(
                                "Failed to reload WorldSpawns config. Report this problem to the plugin developer and/or server administrator(s).");
                        return true;
                    }
                    c.sendMessage(ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_RELOAD_SUCCESS.toString()));
                    return true;
                }
                if (args[0].equals("check")) {
                    if (!main.getConfig().getBoolean("components.spawns")
                            && !main.getConfig().getBoolean("components.hub")) {
                        sender.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                                Lang.COMPONENT_FEATURE_DISABLED.toString()));
                        return true;
                    }

                    c.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            Lang.COMMAND_CHECK_DEFINE.toString().replaceAll("%label%", label)));
                    return true;
                }
                if (args[0].equals("reset")) {
                    if (!main.getConfig().getBoolean("components.spawns")) {
                        sender.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                                Lang.COMPONENT_SPAWNS_DISABLED.toString()));
                        return true;
                    }

                    settings.getHub().set("spawns", null);
                    settings.saveSpawns();
                    c.sendMessage(ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_RESET_SUCCESS.toString()));
                    return true;
                }
                if (args[0].equals("enable")) {
                    c.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            Lang.COMMAND_ENABLE_INCORRECT.toString().replaceAll("%label%", label)));
                    return true;
                } else {
                    c.sendMessage(ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_UNKNOWN_SUBCOMMAND.toString()
                            .replaceAll("%label%", label).replaceAll("%args$", args[0])));
                    return true;
                }
            }
            if (args.length == 2) {
                if (args[0].equals("help")) {
                    c.sendMessage(ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_HELP_TOOMANYARGS.toString()
                            .replaceAll("%label%", label).replaceAll("%args%", String.valueOf(args.length))));
                    return true;
                }
                if (args[0].equals("reload")) {
                    c.sendMessage(ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_RELOAD_TOOMANYARGS.toString()
                            .replaceAll("%label%", label).replaceAll("%args%", String.valueOf(args.length))));
                    return true;
                }
                if (args[0].equals("check")) {
                    if (!main.getConfig().getBoolean("components.spawns")
                            && !main.getConfig().getBoolean("components.hub")) {
                        sender.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                                Lang.COMPONENT_FEATURE_DISABLED.toString()));
                        return true;
                    }

                    double x;
                    double y;
                    double z;
                    float yaw;
                    float pitch;
                    Location loc;

                    if (args[1].equals("hub")) {
                        if (!main.getConfig().getBoolean("components.hub")) {
                            sender.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                                    Lang.COMPONENT_HUB_DISABLED.toString()));
                            return true;
                        }

                        final World w = main.getServer().getWorld(settings.getHub().getString("hub.world"));
                        if (w == null) {
                            c.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                    Lang.COMMAND_CHECK_HUB_NOTSET.toString()));
                            return true;
                        }
                        x = settings.getHub().getDouble("hub.x");
                        y = settings.getHub().getDouble("hub.y");
                        z = settings.getHub().getDouble("hub.z");
                        yaw = settings.getHub().getInt("hub.yaw");
                        pitch = settings.getHub().getInt("hub.pitch");
                        loc = new Location(w, x, y, z);
                        c.sendMessage(ChatColor.GRAY + "X: " + ChatColor.WHITE + x + ".");
                        c.sendMessage(ChatColor.GRAY + "Y: " + ChatColor.WHITE + y + ".");
                        c.sendMessage(ChatColor.GRAY + "Z: " + ChatColor.WHITE + z + ".");
                        c.sendMessage(ChatColor.GRAY + "Yaw: " + ChatColor.WHITE + yaw + ".");
                        c.sendMessage(ChatColor.GRAY + "Pitch: " + ChatColor.WHITE + pitch + ".");
                        if (loc.getBlock().getType() == Material.AIR) {
                            c.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                    Lang.COMMAND_CHECK_INAIR_TRUE.toString()));
                        } else {
                            c.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                    Lang.COMMAND_CHECK_INAIR_FALSE.toString()));
                        }
                        return true;
                    }

                    if (!main.getConfig().getBoolean("components.hub")) {
                        sender.sendMessage(prefix
                                + ChatColor.translateAlternateColorCodes('&', Lang.COMPONENT_HUB_DISABLED.toString()));
                        return true;
                    }

                    final String worldname = args[1];

                    if (main.getServer().getWorld(worldname) == null) {
                        c.sendMessage(ChatColor.RED + "World '" + ChatColor.GOLD + worldname + ChatColor.RED
                                + "' doesn't exist!");
                        return true;
                    }

                    x = settings.getHub().getInt((new StringBuilder("spawns.") + worldname + ".x"));
                    y = settings.getHub().getInt((new StringBuilder("spawns.") + worldname + ".y"));
                    z = settings.getHub().getInt((new StringBuilder("spawns.") + worldname + ".z"));
                    yaw = settings.getHub().getInt((new StringBuilder("spawns.") + worldname + ".yaw"));
                    pitch = settings.getHub().getInt((new StringBuilder("spawns.") + worldname + ".pitch"));
                    loc = new Location(main.getServer().getWorld(worldname), x, y - 1.0D, z);
                    c.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            Lang.CHECK_HEADER_WORLD.toString().replaceAll("%worldname%", worldname)));

                    if (settings.getHub().get("spawns." + worldname) == null) {
                        c.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                Lang.COMMAND_CHECK_WORLD_SPAWN_NULL.toString()));
                    } else {
                        c.sendMessage(ChatColor.GRAY + "X: " + ChatColor.WHITE + x + ".");
                        c.sendMessage(ChatColor.GRAY + "Y: " + ChatColor.WHITE + y + ".");
                        c.sendMessage(ChatColor.GRAY + "Z: " + ChatColor.WHITE + z + ".");
                        c.sendMessage(ChatColor.GRAY + "Yaw: " + ChatColor.WHITE + yaw + ".");
                        c.sendMessage(ChatColor.GRAY + "Pitch: " + ChatColor.WHITE + pitch + ".");

                        if (loc.getBlock().getType() == Material.AIR) {
                            c.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                    Lang.COMMAND_CHECK_INAIR_TRUE.toString()));
                        } else {
                            c.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                    Lang.COMMAND_CHECK_INAIR_FALSE.toString()));
                        }

                    }
                    return true;
                }
                if (args[0].equals("reset")) {
                    if (!main.getConfig().getBoolean("components.spawns")) {
                        sender.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                                Lang.COMPONENT_SPAWNS_DISABLED.toString()));
                        return true;
                    }
                    c.sendMessage(ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_RESET_TOOMANYARGS.toString()
                            .replaceAll("%label%", label).replaceAll("%lenght%", String.valueOf(args.length))));
                    return true;
                }
                if (args[0].equals("enable")) {
                    c.sendMessage(ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_ENABLE_INCORRECT.toString().replaceAll("%label%", label)));
                    return true;
                }
                if (args[0].equals("spawns")) {
                    if (!main.getConfig().getBoolean("components.spawns")) {
                        sender.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                                Lang.COMPONENT_SPAWNS_DISABLED.toString()));
                        return true;
                    }

                    c.sendMessage(ChatColor.RED + "Too many arguments (" + args.length + "). Usage: " + ChatColor.GOLD
                            + "/" + label + " spawns" + ChatColor.RED + ".");
                    c.sendMessage(ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_SPAWNS_TOOMANYARGS.toString()
                            .replaceAll("%label%", label).replaceAll("%lenght%", String.valueOf(args.length))));
                    return true;
                }
                if (args[0].equals("reload")) {
                    c.sendMessage(ChatColor.RED + "Too many arguments (" + args.length + "). Usage: " + ChatColor.GOLD
                            + "/" + label + " help" + ChatColor.RED + ".");
                    c.sendMessage(ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_RELOAD_TOOMANYARGS.toString()
                            .replaceAll("%label%", label).replaceAll("%args%", args[0])));

                    return true;
                }
                if (args[0].equals("check")) {
                    c.sendMessage(ChatColor.RED + "Too many arguments (" + args.length + "). Usage: " + ChatColor.GOLD
                            + "/" + label + " check [world]" + ChatColor.RED + ".");
                    c.sendMessage(ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_CHECK_TOOMANYARGS.toString()
                            .replaceAll("%label%", label).replaceAll("%lenght%", String.valueOf(args.length))));
                    return true;
                } else {
                    c.sendMessage(ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_UNKNOWN_SUBCOMMAND.toString()
                            .replaceAll("%label%", label).replaceAll("%args%", args[0])));
                    return true;
                }
            }
        } else {
            final Player p = (Player) sender;

            if (!p.hasPermission((new Permissions()).command_main)) {
                p.sendMessage(noperm);
                return true;
            }

            if (args.length == 0) {
                p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_NOARGS.toString()
                        .replaceAll("%label%", label).replaceAll("%versionid%", main.getVersionID())));
                return true;
            }
            if (args.length == 1) {
                if (args[0].equals("help") || args[0].equals("?")) {
                    p.sendMessage(header);
                    p.sendMessage(headerNote);

                    if(main.getConfig().getBoolean("components.spawns")) {
                        p.sendMessage(l(Lang.COMMAND_HELP_SPAWNS_DESC.toString().replaceAll("%label%", label)));
                    }
                    if(main.getConfig().getBoolean("components.hub")) {
                        p.sendMessage(l(Lang.COMMAND_HELP_HUB_DESC.toString().replaceAll("%label%", label)));
                    }
                    if(main.getConfig().getBoolean("components.hub") || main.getConfig().getBoolean("components.spawns")) {
                        if(p.isOp() || p.hasPermission(new Permissions().command_check)) {
                            p.sendMessage(l(Lang.COMMAND_HELP_CHECK_DESC.toString().replaceAll("%label%", label)));
                        }
                    }
                    if(p.isOp() || p.hasPermission(new Permissions().command_reload)) {
                        p.sendMessage(l(Lang.COMMAND_HELP_RELOAD_DESC.toString().replaceAll("%label%", label)));
                    }
                    if(p.isOp() || p.hasPermission(new Permissions().command_enable)) {
                        p.sendMessage(l(Lang.COMMAND_HELP_ENABLE_DESC.toString().replaceAll("%label%", label)));
                    }
                    return true;
                }
                if (args[0].equals("spawns")) {
                    if (!main.getConfig().getBoolean("components.spawns")) {
                        sender.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                                Lang.COMPONENT_SPAWNS_DISABLED.toString()));
                        return true;
                    }

                    p.sendMessage(header);
                    p.sendMessage(headerNote);
                    p.sendMessage(l(Lang.COMMAND_HELP_SPAWNS_SPAWN_DESC.toString()));

                    if(p.isOp() || p.hasPermission(new Permissions().fspawn)) {
                        p.sendMessage(l(Lang.COMMAND_HELP_SPAWNS_FSPAWN_DESC.toString()));
                    }
                    if(p.isOp() || p.hasPermission(new Permissions().spawn_set)) {
                        p.sendMessage(l(Lang.COMMAND_HELP_SPAWNS_SETSPAWN_DESC.toString()));
                    }
                    if(p.isOp() || p.hasPermission(new Permissions().hub_set)) {
                        p.sendMessage(l(Lang.COMMAND_HELP_SPAWNS_DELSPAWN_DESC.toString()));
                    }
                    if(p.isOp() || p.hasPermission(new Permissions().newbie_set)) {
                        p.sendMessage(l(Lang.COMMAND_HELP_SPAWNS_SETNEWBIE_DESC.toString()));
                    }
                    if(p.isOp() || p.hasPermission(new Permissions().newbie_reset)) {
                        p.sendMessage(l(Lang.COMMAND_HELP_SPAWNS_RESETNEWBIE_DESC.toString()));
                    }
                    if(p.isOp() || p.hasPermission(new Permissions().command_reset)) {
                        p.sendMessage(l(Lang.COMMAND_HELP_SPAWNS_RESET_DESC.toString().replaceAll("%label%", label)));
                    }
                    return true;
                }
                if(args[0].equals("hub")) {
                    if (!main.getConfig().getBoolean("components.hub")) {
                        sender.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                                Lang.COMPONENT_HUB_DISABLED.toString()));
                        return true;
                    }

                    p.sendMessage(header);
                    p.sendMessage(headerNote);
                    p.sendMessage(l(Lang.COMMAND_HELP_HUB_HUB_DESC.toString()));
                    if(p.isOp() || p.hasPermission(new Permissions().fhub)) {
                        p.sendMessage(l(Lang.COMMAND_HELP_HUB_FHUB_DESC.toString()));
                    }
                    if(p.isOp() || p.hasPermission(new Permissions().hub_set)) {
                        p.sendMessage(l(Lang.COMMAND_HELP_HUB_SETHUB_DESC.toString()));
                    }
                    if(p.isOp() || p.hasPermission(new Permissions().hub_reset)) {
                        p.sendMessage(l(Lang.COMMAND_HELP_HUB_RESETHUB_DESC.toString()));
                    }
                    return true;
                }
                if (args[0].equals("reload")) {
                    if (!p.hasPermission((new Permissions()).command_reload)) {
                        p.sendMessage(noperm);
                        return true;
                    }

                    try {
                        main.reloadConfig();
                        settings.reloadConfig();
                    } catch (final Exception e) {
                        p.sendMessage(prefix
                                + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_RELOAD_FAIL.toString()));
                        return true;
                    }
                    p.sendMessage(prefix
                            + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_RELOAD_SUCCESS.toString()));
                    return true;
                }
                if (args[0].equals("check")) {
                    if (!p.hasPermission((new Permissions()).command_check)) {
                        p.sendMessage(noperm);
                        return true;
                    }

                    if (!main.getConfig().getBoolean("components.spawns")
                            && !main.getConfig().getBoolean("components.hub")) {
                        sender.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                                Lang.COMPONENT_FEATURE_DISABLED.toString()));
                        return true;
                    }

                    p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                            Lang.COMMAND_CHECK_DEFINE.toString().replaceAll("%label%", label)));
                    return true;
                }
                if (args[0].equals("reset")) {
                    if (!p.hasPermission((new Permissions()).command_reset)) {
                        p.sendMessage(noperm);
                        return true;
                    }

                    if (!main.getConfig().getBoolean("components.spawns")) {
                        p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                                Lang.COMPONENT_SPAWNS_DISABLED.toString()));
                        return true;
                    }

                    try {
                        settings.getHub().set("spawns", null);
                        settings.saveSpawns();
                    } catch (final Exception e) {
                        p.sendMessage(prefix
                                + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_RESET_FAIL.toString()));
                        e.printStackTrace();
                        return true;
                    }

                    p.sendMessage(prefix
                            + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_RESET_SUCCESS.toString()));
                    playSound(p, Sound.ENTITY_FIREWORK_LARGE_BLAST);
                    return true;
                }
                if (args[0].equals("wand")) {
                    if (!p.hasPermission((new Permissions()).command_wand)) {
                        p.sendMessage(noperm);
                        return true;
                    }

                    if(!main.getConfig().getBoolean("components.spawns") && !main.getConfig().getBoolean("components.hub")) {
                        p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', Lang.COMPONENT_DISABLED_HUBSPAWN.toString()));
                        return true;
                    }

                    if (!main.getConfig().getBoolean("components.portals")) {
                        p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                                Lang.COMPONENT_PORTALS_DISABLED.toString()));
                        return true;
                    }

					/*
					 * if (!p.getInventory().contains(Material.WOOD_AXE)) {
					 * ItemStack item = new ItemStack(Material.WOOD_AXE, 1);
					 * ItemStack pitem = p.getInventory().getItemInMainHand();
					 * p.getInventory().setItemInMainHand(item);
					 * p.getInventory().addItem(pitem); p.sendMessage(prefix +
					 * ChatColor.translateAlternateColorCodes('&',
					 * Lang.COMMAND_WAND_EXPLAINATION.toString())); } else {
					 * p.sendMessage( prefix +
					 * ChatColor.translateAlternateColorCodes('&',
					 * Lang.COMMAND_WAND_HAS.toString())); }
					 */

                    p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', Lang.FEATURE_WIP.toString()));
                    return true;
                }
                if (args[0].equals("setportal")) {
                    if (!p.hasPermission((new Permissions()).command_check)) {
                        p.sendMessage(noperm);
                        return true;
                    }

                    if (!main.getConfig().getBoolean("components.portals")) {
                        p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                                Lang.COMPONENT_PORTALS_DISABLED.toString()));
                        return true;
                    }

					/*
					 * p.sendMessage(prefix +
					 * ChatColor.translateAlternateColorCodes('&',
					 * Lang.COMMAND_SETPORTAL_DEFINE.toString().replaceAll(
					 * "%label%", label))); return true;
					 */

                    p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', Lang.FEATURE_WIP.toString()));
                    return true;
                }
                if (args[0].equals("enable")) {
                    if (!p.hasPermission((new Permissions()).command_enable)) {
                        p.sendMessage(noperm);
                        return true;
                    }

                    p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                            Lang.COMMAND_ENABLE_INCORRECT.toString().replaceAll("%label%", label)));
                    return true;
                } else {
                    p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_UNKNOWN_SUBCOMMAND
                            .toString().replaceAll("%label%", label).replaceAll("%args%", args[0])));
                    return true;
                }
            }

            if (args.length == 2) {
                if (args[0].equals("help")) {
                    p.sendMessage(prefix
                            + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_HELP_TOOMANYARGS.toString()
                            .replaceAll("%label%", label).replaceAll("%lenght%", String.valueOf(args.length))));
                    return true;
                }
                if (args[0].equals("spawns")) {
                    if (!p.hasPermission((new Permissions()).command_spawns)) {
                        p.sendMessage(noperm);
                        return true;
                    }

                    if (!main.getConfig().getBoolean("components.spawns")) {
                        p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                                Lang.COMPONENT_SPAWNS_DISABLED.toString()));
                        return true;
                    }

                    p.sendMessage(prefix
                            + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_SPAWNS_TOOMANYARGS.toString()
                            .replaceAll("%label%", label).replaceAll("%lenght%", String.valueOf(args.length))));
                    return true;
                }
                if (args[0].equals("reload")) {
                    if (!p.hasPermission((new Permissions()).command_reload)) {
                        p.sendMessage(noperm);
                        return true;
                    }

                    p.sendMessage(prefix
                            + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_RELOAD_TOOMANYARGS.toString()
                            .replaceAll("%label%", label).replaceAll("%lenght%", String.valueOf(args.length))));
                    return true;
                }
                if (args[0].equals("check")) {
                    if (!p.hasPermission((new Permissions()).command_check)) {
                        p.sendMessage(noperm);
                        return true;
                    }

                    if (!main.getConfig().getBoolean("components.hub")
                            && !main.getConfig().getBoolean("components.spawns")) {
                        p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                                Lang.COMPONENT_FEATURE_DISABLED.toString()));
                        return true;
                    }

                    double x;
                    double y;
                    double z;
                    float yaw;
                    float pitch;
                    Location loc;

                    if (args[1].equals("shub")) {
                        if (!main.getConfig().getBoolean("components.hub")) {
                            p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                                    Lang.COMPONENT_HUB_DISABLED.toString()));
                            return true;
                        }

                        if (settings.getHub().getString("hub.world") == null) {
                            p.sendMessage(prefix
                                    + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_HUB_NULL.toString()));
                            return true;
                        }

                        World w = main.getServer().getWorld(settings.getHub().getString("hub.world"));
                        if (w == null) {
                            p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                                    Lang.COMMAND_CHECK_HUB_NOTSET.toString()));
                            return true;
                        }

                        x = settings.getHub().getDouble("hub.x");
                        y = settings.getHub().getDouble("hub.y");
                        z = settings.getHub().getDouble("hub.z");
                        yaw = settings.getHub().getInt("hub.yaw");
                        pitch = settings.getHub().getInt("hub.pitch");
                        loc = new Location(w, x, y - 0.1D, z);
                        Block block = loc.getBlock();

                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', Lang.CHECK_HEADER_HUB.toString()));

                        final String xs = settings.getHub().getString("hub.x");
                        final String ys = settings.getHub().getString("hub.y");
                        final String zs = settings.getHub().getString("hub.z");
                        final String yaws = settings.getHub().getString("hub.yaw");
                        final String pitchs = settings.getHub().getString("hub.pitch");
                        if (xs == null || ys == null || zs == null || yaws == null || pitchs == null) {
                            sender.sendMessage(prefix + ChatColor.translateAlternateColorCodes('7',
                                    Lang.COMMAND_CHECK_HUB_NOTSET.toString()));
                            return true;
                        } else {
                            p.sendMessage(ChatColor.GRAY + "X: " + ChatColor.WHITE + x + ".");
                            p.sendMessage(ChatColor.GRAY + "Y: " + ChatColor.WHITE + y + ".");
                            p.sendMessage(ChatColor.GRAY + "Z: " + ChatColor.WHITE + z + ".");
                            p.sendMessage(ChatColor.GRAY + "Yaw: " + ChatColor.WHITE + yaw + ".");
                            p.sendMessage(ChatColor.GRAY + "Pitch: " + ChatColor.WHITE + pitch + ".");
                            if (block.getType() == Material.AIR) {
                                p.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                        Lang.COMMAND_CHECK_INAIR_TRUE.toString()));
                            } else {
                                p.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                        Lang.COMMAND_CHECK_INAIR_FALSE.toString()));
                            }
                        }
                        return true;
                    }

                    if (!main.getConfig().getBoolean("components.spawns")) {
                        p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                                Lang.COMPONENT_SPAWNS_DISABLED.toString()));
                        return true;
                    }

                    final String worldname = args[1];
                    if (main.getServer().getWorld(worldname) == null) {
                        p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                                Lang.COMMAND_CHECK_WORLD_NULL.toString().replaceAll("%worldname%", worldname)));
                        return true;
                    }
                    x = settings.getHub().getInt("spawns." + worldname + ".x");
                    y = settings.getHub().getInt((new StringBuilder("spawns.") + worldname + ".y"));
                    z = settings.getHub().getInt((new StringBuilder("spawns.") + worldname + ".z"));
                    yaw = settings.getHub().getInt((new StringBuilder("spawns.") + worldname + ".yaw"));
                    pitch = settings.getHub().getInt((new StringBuilder("spawns.") + worldname + ".pitch"));
                    loc = new Location(main.getServer().getWorld(worldname), x, y - 0.1D, z);
                    Block block = loc.getBlock();

                    p.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            Lang.CHECK_HEADER_HUB.toString().replaceAll("%worldname%", worldname)));

                    if (settings.getHub().get("spawns." + worldname) == null) {
                        p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                                Lang.COMMAND_CHECK_WORLD_SPAWN_NULL.toString()));
                    } else {
                        p.sendMessage(ChatColor.GRAY + "X: " + ChatColor.WHITE + x + ".");
                        p.sendMessage(ChatColor.GRAY + "Y: " + ChatColor.WHITE + y + ".");
                        p.sendMessage(ChatColor.GRAY + "Z: " + ChatColor.WHITE + z + ".");
                        p.sendMessage(ChatColor.GRAY + "Yaw: " + ChatColor.WHITE + yaw + ".");
                        p.sendMessage(ChatColor.GRAY + "Pitch: " + ChatColor.WHITE + pitch + ".");
                        if (block.getType() == Material.AIR) {
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                    Lang.COMMAND_CHECK_INAIR_TRUE.toString()));
                        } else {
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                    Lang.COMMAND_CHECK_INAIR_FALSE.toString()));
                        }
                    }
                    return true;
                }
                if (args[0].equals("reset")) {
                    if (!p.hasPermission((new Permissions()).command_reset)) {
                        p.sendMessage(noperm);
                        return true;
                    }

                    if (!main.getConfig().getBoolean("components.spawns")) {
                        p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                                Lang.COMPONENT_SPAWNS_DISABLED.toString()));
                        return true;
                    }

                    p.sendMessage(prefix
                            + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_RESET_TOOMANYARGS.toString()
                            .replaceAll("%lenght%", String.valueOf(args.length)).replaceAll("%label%", label)));
                    return true;
                }
                if (args[0].equals("check")) {
                    if (!p.hasPermission((new Permissions()).command_check)) {
                        p.sendMessage(noperm);
                        return true;
                    }

                    if (!main.getConfig().getBoolean("components.hub")
                            && !main.getConfig().getBoolean("components.spawns")) {
                        p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                                Lang.COMPONENT_FEATURE_DISABLED.toString()));
                        return true;
                    }

                    p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                            Lang.COMMAND_CHECK_DEFINE.toString().replaceAll("%label%", label)));
                    return true;
                }
                if (args[0].equals("wand")) {
                    if (!p.hasPermission((new Permissions()).command_reset)) {
                        p.sendMessage(noperm);
                        return true;
                    }

                    if (!main.getConfig().getBoolean("components.portals")) {
                        p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                                Lang.COMPONENT_PORTALS_DISABLED.toString()));
                        return true;
                    }

                    p.sendMessage(prefix
                            + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_WAND_TOOMANYARGS.toString()
                            .replaceAll("%lenght%", String.valueOf(args.length)).replaceAll("%label%", label)));
                    return true;
                }
                if (args[0].equals("setportal")) {
                    if (!p.hasPermission((new Permissions()).command_check)) {
                        p.sendMessage(noperm);
                        return true;
                    }

                    if (!main.getConfig().getBoolean("components.portals")) {
                        p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                                Lang.COMPONENT_PORTALS_DISABLED.toString()));
                        return true;
                    }
					/*
					 * p.sendMessage(prefix +
					 * ChatColor.translateAlternateColorCodes('&',
					 * Lang.COMMAND_SETPORTAL_DEFINE.toString().replaceAll(
					 * "%label%", label)));
					 */

                    p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', Lang.FEATURE_WIP.toString()));
                    return true;
                }
                if (args[0].equals("enable")) {
                    if (!p.hasPermission(new Permissions().command_enable)) {
                        p.sendMessage(noperm);
                        return true;
                    }

                    p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_ENABLE_INCORRECT.toString().replaceAll("%label%", label)));
                    return true;
                } else {
                    p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_UNKNOWN_SUBCOMMAND
                            .toString().replaceAll("%label%", label).replaceAll("%args%", args.toString())));
                    return true;
                }
            }
            if (args.length == 3) {
                if (args[0].equals("help")) {
                    p.sendMessage(prefix
                            + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_HELP_TOOMANYARGS.toString()
                            .replaceAll("%label%", label).replaceAll("%lenght%", String.valueOf(args.length))));
                    return true;
                }
                if (args[0].equals("spawns")) {
                    if (!p.hasPermission((new Permissions()).command_spawns)) {
                        p.sendMessage(noperm);
                        return true;
                    }

                    if (!main.getConfig().getBoolean("components.spawns")) {
                        p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                                Lang.COMPONENT_SPAWNS_DISABLED.toString()));
                        return true;
                    }

                    p.sendMessage(prefix
                            + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_SPAWNS_TOOMANYARGS.toString()
                            .replaceAll("%label%", label).replaceAll("%lenght%", String.valueOf(args.length))));
                    return true;
                }
                if (args[0].equals("reload")) {
                    if (!p.hasPermission((new Permissions()).command_reload)) {
                        p.sendMessage(noperm);
                        return true;
                    }

                    p.sendMessage(prefix
                            + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_RELOAD_TOOMANYARGS.toString()
                            .replaceAll("%label%", label).replaceAll("%lenght%", String.valueOf(args.length))));
                    return true;
                }
                if (args[0].equals("check")) {
                    if (!p.hasPermission((new Permissions()).command_check)) {
                        p.sendMessage(noperm);
                        return true;
                    }

                    if (!main.getConfig().getBoolean("components.hub")
                            && !main.getConfig().getBoolean("components.spawns")) {
                        p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                                Lang.COMPONENT_FEATURE_DISABLED.toString()));
                        return true;
                    }

                    p.sendMessage(prefix
                            + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_CHECK_TOOMANYARGS.toString()
                            .replaceAll("%label%", label).replaceAll("%lenght%", String.valueOf(args.length))));
                    return true;
                }
                if (args[0].equals("reset")) {
                    if (!p.hasPermission((new Permissions()).command_reset)) {
                        p.sendMessage(noperm);
                        return true;
                    }

                    if (!main.getConfig().getBoolean("components.spawns")) {
                        p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                                Lang.COMPONENT_FEATURE_DISABLED.toString()));
                        return true;
                    }

                    p.sendMessage(prefix + ChatColor.RED + "Too many arguments (" + args.length + "). Usage: "
                            + ChatColor.GOLD + "/" + label + " reset" + ChatColor.RED + ".");
                    p.sendMessage(prefix
                            + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_RESET_TOOMANYARGS.toString()
                            .replaceAll("%lenght%", String.valueOf(args.length)).replaceAll("%label%", label)));
                    return true;
                }
                if (args[0].equals("check")) {
                    if (!p.hasPermission((new Permissions()).command_check)) {
                        p.sendMessage(noperm);
                        return true;
                    }

                    if (!main.getConfig().getBoolean("components.hub")
                            && !main.getConfig().getBoolean("components.spawns")) {
                        p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                                Lang.COMPONENT_FEATURE_DISABLED.toString()));
                        return true;
                    }

                    p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                            Lang.COMMAND_CHECK_TOOMANYARGS.toString().replaceAll("%label%", label)));
                    return true;
                }
                if (args[0].equals("wand")) {
                    if (!p.hasPermission((new Permissions()).command_wand)) {
                        p.sendMessage(noperm);
                        return true;
                    }

                    if (!main.getConfig().getBoolean("components.portals")) {
                        p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                                Lang.COMPONENT_PORTALS_DISABLED.toString()));
                        return true;
                    }

                    p.sendMessage(prefix
                            + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_WAND_TOOMANYARGS.toString()
                            .replaceAll("%label%", label).replaceAll("%lenght%", String.valueOf(args.length))));
                    return true;
                }
                if (args[0].equals("setportal")) {
                    if (!p.hasPermission((new Permissions()).command_setportal)) {
                        p.sendMessage(noperm);
                        return true;
                    }

                    if (!main.getConfig().getBoolean("components.portals")) {
                        p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                                Lang.COMPONENT_PORTALS_DISABLED.toString()));
                        return true;
                    }

					/*
					 * String portalName = args[1]; String type = args[2];
					 * Portal portal = new Portal(main, new
					 * File(main.getDataFolder(), File.separator + "data" +
					 * File.separator + portalName)); List<String> portalList =
					 * portal.getPortals();
					 *
					 * if (!type.contains("hub")) { p.sendMessage(prefix +
					 * ChatColor.RED + "ERROR 1!"); return true; }
					 *
					 * if (portalList.contains(portalName)) {
					 * p.sendMessage(prefix +
					 * ChatColor.translateAlternateColorCodes('&',
					 * Lang.COMMAND_SETPORTAL_EXISTS.toString().replaceAll(
					 * "%portalname%", portalName))); return true; }
					 *
					 * try { portal.createNew(p, portalName, type); } catch
					 * (Exception e) { p.sendMessage(prefix +
					 * ChatColor.translateAlternateColorCodes('&',
					 * Lang.COMMAND_SETPORTAL_HUB_FAILED.toString().replaceAll(
					 * "%portalname%", portalName))); e.printStackTrace();
					 * return true; }
					 */

                    p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', Lang.FEATURE_WIP.toString()));
                    return true;
                }
                if (args[0].equals("enable")) {
                    if (!p.hasPermission((new Permissions()).command_enable)) {
                        p.sendMessage(noperm);
                        return true;
                    }
                    String component = args[1];

                    if(args[1].equals("spawns")) {
                        if(args[2].equals("true") || args[2].equals("false")) {
                            main.getConfig().set("components." + component, Boolean.valueOf(args[2]));
                            main.saveConfig();
                            main.reloadConfig();
                            if(args[2].equals("true")) {
                                p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_ENABLE_ENABLED.toString().replaceAll("%component%", component)));
                                return true;
                            } else {
                                p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_ENABLE_DISABLED.toString().replaceAll("%component%", component)));
                                return true;
                            }
                        } else {
                            p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_ENABLE_WRONGBOOL.toString()));
                            return true;
                        }
                    }
                    if(args[1].equals("hub")) {
                        if(args[2].equals("true") || args[2].equals("false")) {
                            main.getConfig().set("components." + component, Boolean.valueOf(args[2]));
                            main.saveConfig();
                            main.reloadConfig();
                            if(args[2].equals("true")) {
                                p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_ENABLE_ENABLED.toString().replaceAll("%component%", component)));
                                return true;
                            } else {
                                p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_ENABLE_DISABLED.toString().replaceAll("%component%", component)));
                                return true;
                            }
                        } else {
                            p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_ENABLE_WRONGBOOL.toString()));
                            return true;
                        }
                    }
                    if(args[1].equals("portals")) {
                        if(args[2].equals("true") || args[2].equals("false")) {
                            main.getConfig().set("components." + component, Boolean.valueOf(args[2]));
                            main.saveConfig();
                            main.reloadConfig();
                            if(args[2].equals("true")) {
                                p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_ENABLE_ENABLED.toString().replaceAll("%component%", component)));
                                return true;
                            } else {
                                p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_ENABLE_DISABLED.toString().replaceAll("%component%", component)));
                                return true;
                            }
                        } else {
                            p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_ENABLE_WRONGBOOL.toString()));
                            return true;
                        }
                    }
                    if(args[1].equals("signs")) {
                        if(args[2].equals("true") || args[2].equals("false")) {
                            main.getConfig().set("components." + component, Boolean.valueOf(args[2]));
                            main.saveConfig();
                            main.reloadConfig();
                            if(args[2].equals("true")) {
                                p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_ENABLE_ENABLED.toString().replaceAll("%component%", component)));
                                return true;
                            } else {
                                p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_ENABLE_DISABLED.toString().replaceAll("%component%", component)));
                                return true;
                            }
                        } else {
                            p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_ENABLE_WRONGBOOL.toString()));
                            return true;
                        }
                    } else {
                        p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_ENABLE_COMPONENTDOESNOTEXIST.toString().replaceAll("%component%", component)));
                        return true;
                    }
                }
            }
            if (args.length == 4) {
                if (args[0].equals("help")) {
                    p.sendMessage(prefix
                            + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_HELP_TOOMANYARGS.toString()
                            .replaceAll("%label%", label).replaceAll("%lenght%", String.valueOf(args.length))));
                    return true;
                }
                if (args[0].equals("spawns")) {
                    if (!p.hasPermission((new Permissions()).command_spawns)) {
                        p.sendMessage(noperm);
                        return true;
                    }

                    if (!main.getConfig().getBoolean("components.spawns")) {
                        p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                                Lang.COMPONENT_SPAWNS_DISABLED.toString()));
                        return true;
                    }

                    p.sendMessage(prefix
                            + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_SPAWNS_TOOMANYARGS.toString()
                            .replaceAll("%label%", label).replaceAll("%lenght%", String.valueOf(args.length))));
                    return true;
                }
                if (args[0].equals("reload")) {
                    if (!p.hasPermission((new Permissions()).command_reload)) {
                        p.sendMessage(noperm);
                        return true;
                    }

                    p.sendMessage(prefix
                            + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_RELOAD_TOOMANYARGS.toString()
                            .replaceAll("%label%", label).replaceAll("%lenght%", String.valueOf(args.length))));
                    return true;
                }
                if (args[0].equals("check")) {
                    if (!p.hasPermission((new Permissions()).command_check)) {
                        p.sendMessage(noperm);
                        return true;
                    }

                    if (!main.getConfig().getBoolean("components.hub")
                            && !main.getConfig().getBoolean("components.spawns")) {
                        p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                                Lang.COMPONENT_FEATURE_DISABLED.toString()));
                        return true;
                    }

                    p.sendMessage(prefix
                            + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_CHECK_TOOMANYARGS.toString()
                            .replaceAll("%label%", label).replaceAll("%lenght%", String.valueOf(args.length))));
                    return true;
                }
                if (args[0].equals("reset")) {
                    if (!p.hasPermission((new Permissions()).command_reset)) {
                        p.sendMessage(noperm);
                        return true;
                    }

                    if (!main.getConfig().getBoolean("components.spawns")) {
                        p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                                Lang.COMPONENT_SPAWNS_DISABLED.toString()));
                        return true;
                    }

                    p.sendMessage(prefix + ChatColor.RED + "Too many arguments (" + args.length + "). Usage: "
                            + ChatColor.GOLD + "/" + label + " reset" + ChatColor.RED + ".");
                    p.sendMessage(prefix
                            + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_RESET_TOOMANYARGS.toString()
                            .replaceAll("%lenght%", String.valueOf(args.length)).replaceAll("%label%", label)));
                    return true;
                }
                if (args[0].equals("check")) {
                    if (!p.hasPermission((new Permissions()).command_check)) {
                        p.sendMessage(noperm);
                        return true;
                    }

                    if (!main.getConfig().getBoolean("components.hub")
                            && !main.getConfig().getBoolean("components.spawns")) {
                        p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                                Lang.COMPONENT_FEATURE_DISABLED.toString()));
                        return true;
                    }

                    p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                            Lang.COMMAND_CHECK_TOOMANYARGS.toString().replaceAll("%label%", label)));
                    return true;
                }
                if (args[0].equals("wand")) {
                    if (!p.hasPermission((new Permissions()).command_wand)) {
                        p.sendMessage(noperm);
                        return true;
                    }

                    if (!main.getConfig().getBoolean("components.portals")) {
                        p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                                Lang.COMPONENT_PORTALS_DISABLED.toString()));
                        return true;
                    }

                    p.sendMessage(prefix
                            + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_WAND_TOOMANYARGS.toString()
                            .replaceAll("%label%", label).replaceAll("%lenght%", String.valueOf(args.length))));
                    return true;
                }
                if (args[0].equals("setportal")) {
                    if (!p.hasPermission((new Permissions()).command_check)) {
                        p.sendMessage(noperm);
                        return true;
                    }

                    if (!main.getConfig().getBoolean("components.portals")) {
                        p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                                Lang.COMPONENT_PORTALS_DISABLED.toString()));
                        return true;
                    }

					/*
					 * String portalName = args[1]; String type = args[2];
					 * String spawn = args[3]; Portal portal = new Portal(main,
					 * new File(main.getDataFolder(), File.separator + "data" +
					 * File.separator + portalName));
					 *
					 * List<String> portals = portal.getPortals();
					 *
					 * if (!type.contains("spawn")) { p.sendMessage(prefix +
					 * ChatColor.RED + "ERROR 1!"); return true; }
					 *
					 * if (portals.contains(portalName)) { p.sendMessage(prefix
					 * + ChatColor.translateAlternateColorCodes('&',
					 * Lang.COMMAND_SETPORTAL_EXISTS.toString().replaceAll(
					 * "%portalname%", portalName))); return true; }
					 *
					 * try { portal.createNew(p, portalName, type, spawn); }
					 * catch (Exception e) { p.sendMessage(prefix +
					 * ChatColor.translateAlternateColorCodes('&',
					 * Lang.COMMAND_SETPORTAL_SPAWN_FAILED.toString().replaceAll
					 * ("%portalname%", portalName))); e.printStackTrace(); }
					 */

                    p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', Lang.FEATURE_WIP.toString()));
                    return true;
                }
                if (args[0].equals("enable")) {
                    if (!p.hasPermission((new Permissions()).command_enable)) {
                        p.sendMessage(noperm);
                        return true;
                    }

                    p.sendMessage(prefix
                            + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_ENABLE_TOOMANYARGS.toString()
                            .replaceAll("%label%", label).replaceAll("%lenght%", String.valueOf(args.length))));
                    return true;
                }
            } else {
                p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                        Lang.COMMAND_TOO_MANY_ARGUMENTS.toString().replaceAll("%label%", label)));
                return true;
            }
        }
        return false;
    }

    private boolean enableSounds() {
        return main.getConfig().getBoolean("settings.enablesounds");
    }

    private void playSound(Player player, Sound sound) {
        if (enableSounds()) {
            player.playSound(player.getLocation(), sound, 10F, 1.0F);
        }
    }

    private String l(String string) {
        final String message = ChatColor.translateAlternateColorCodes('&', string);
        return message;
    }

}

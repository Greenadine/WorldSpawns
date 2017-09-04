package me.greenadine.worldspawns;

import me.greenadine.worldspawns.commands.Permissions;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class SignListener implements Listener {

    private Main main;
    SettingsManager settings;
    private String prefix = ChatColor.translateAlternateColorCodes('&', Lang.PREFIX.toString());
    private String noperm = prefix + ChatColor.translateAlternateColorCodes('&', Lang.NO_PERMISSION.toString());

    public SignListener(Main main) {
        this.main = main;
        settings = SettingsManager.getInstance();
    }

    @EventHandler
    public void onPlaceSign(SignChangeEvent e) {
        FileConfiguration config = main.getConfig();
        String line1 = e.getLine(0);
        String line2 = e.getLine(1);
        if (line1.equalsIgnoreCase("[worldspawns]") || line1.equalsIgnoreCase("[ws]")) {
            WSSign sign = new WSSign((Sign) e.getBlock(), main);
            Player p = e.getPlayer();
            if (!p.hasPermission(new Permissions().sign_hub) && !p.hasPermission(new Permissions().sign_hub)) {
                e.getBlock().breakNaturally();
                p.sendMessage(noperm);
                return;
            }
            if (e.getLine(1).equalsIgnoreCase("hub")) {
                if (!p.hasPermission(new Permissions().sign_hub)) {
                    e.getBlock().breakNaturally();
                    p.sendMessage(noperm);
                    return;
                }

                e.setLine(0, ChatColor.translateAlternateColorCodes('&', config.getString("sign.line1")));
                e.setLine(1, ChatColor.translateAlternateColorCodes('&', config.getString("sign.hub.line2")));
                p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', Lang.SIGN_HUB_CREATE.toString()));
                sign.createNew();
                sign.setType(SignType.HUB);
                sign.setLine(0, e.getLine(0));
                sign.setLine(1, e.getLine(1));
                sign.setLine(2, e.getLine(2));
                sign.setLine(3, e.getLine(3));
                return;
            }
            if (e.getLine(1).equalsIgnoreCase("fhub")) {
                if (!p.hasPermission(new Permissions().sign_hub)) {
                    e.getBlock().breakNaturally();
                    p.sendMessage(noperm);
                    return;
                }

                e.setLine(0, ChatColor.translateAlternateColorCodes('&', config.getString("sign.line1")));
                e.setLine(1, ChatColor.translateAlternateColorCodes('&', config.getString("sign.fhub.line2")));
                p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', Lang.SIGN_FHUB_CREATE.toString()));
                sign.createNew();
                sign.setType(SignType.FHUB);
                sign.setLine(0, e.getLine(0));
                sign.setLine(1, e.getLine(1));
                sign.setLine(2, e.getLine(2));
                sign.setLine(3, e.getLine(3));
                sign.createNew();
                return;
            }
            if (line2.equalsIgnoreCase("spawn")) {
                if (!p.hasPermission(new Permissions().sign_spawn)) {
                    e.getBlock().breakNaturally();
                    p.sendMessage(noperm);
                    return;
                }

                String worldname = e.getLine(2);

                if (worldname == null || worldname == "") {
                    e.setLine(0, ChatColor.translateAlternateColorCodes('&', config.getString("sign.line1")));
                    e.setLine(1, ChatColor.translateAlternateColorCodes('&', config.getString("sign.spawn.line2")));
                    e.setLine(2, ChatColor.translateAlternateColorCodes('&', "&cDefine a world."));
                    return;
                }

                World w = main.getServer().getWorld(worldname);
                if (w == null) {
                    e.setLine(0, ChatColor.translateAlternateColorCodes('&', config.getString("sign.line1")));
                    e.setLine(1, ChatColor.translateAlternateColorCodes('&', config.getString("sign.spawn.line2")));
                    e.setLine(2, ChatColor.translateAlternateColorCodes('&', "&cWorld does not"));
                    e.setLine(3, ChatColor.translateAlternateColorCodes('&', "&cexist."));
                    return;
                }

                e.setLine(0, ChatColor.translateAlternateColorCodes('&', config.getString("sign.line1")));
                e.setLine(1, ChatColor.translateAlternateColorCodes('&', config.getString("sign.spawn.line2")));
                e.setLine(2, ChatColor.translateAlternateColorCodes('&',
                        config.getString("sign.spawn.line3").replaceAll("%worldname%", worldname)));
                p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', Lang.SIGN_SPAWN_CREATE.toString()));
                sign.createNew();
                sign.setType(SignType.SPAWN);
                sign.setLine(0, e.getLine(0));
                sign.setLine(1, e.getLine(1));
                sign.setLine(2, e.getLine(2));
                sign.setLine(3, e.getLine(3));
                return;
            }
            if (line2.equalsIgnoreCase("fspawn")) {
                if (!p.hasPermission(new Permissions().sign_fspawn)) {
                    e.getBlock().breakNaturally();
                    p.sendMessage(noperm);
                    return;
                }

                String worldname = e.getLine(2);

                if (worldname == null || worldname == "") {
                    e.setLine(0, ChatColor.translateAlternateColorCodes('&', config.getString("sign.line1")));
                    e.setLine(1, ChatColor.translateAlternateColorCodes('&', config.getString("sign.fspawn.line2")));
                    e.setLine(0, ChatColor.translateAlternateColorCodes('&', "&cDefine a world."));
                    return;
                }

                World w = main.getServer().getWorld(worldname);
                if (w == null) {
                    e.setLine(0, ChatColor.translateAlternateColorCodes('&', config.getString("sign.line1")));
                    e.setLine(1, ChatColor.translateAlternateColorCodes('&', config.getString("sign.fspawn.line2")));
                    e.setLine(2, ChatColor.translateAlternateColorCodes('&', "&cWorld does not"));
                    e.setLine(3, ChatColor.translateAlternateColorCodes('&', "&cexist."));
                    return;
                }

                e.setLine(0, ChatColor.translateAlternateColorCodes('&', config.getString("sign.line1")));
                e.setLine(1, ChatColor.translateAlternateColorCodes('&', config.getString("sign.fspawn.line2")));
                e.setLine(2, ChatColor.translateAlternateColorCodes('&',
                        config.getString("sign.fspawn.line3").replaceAll("%worldname%", worldname)));
                p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', Lang.SIGN_FSPAWN_CREATE.toString()));
                sign.createNew();
                sign.setType(SignType.FSPAWN);
                sign.setLine(0, e.getLine(0));
                sign.setLine(1, e.getLine(1));
                sign.setLine(2, e.getLine(2));
                sign.setLine(3, e.getLine(3));
                return;
            } else {
                e.setLine(0, ChatColor.translateAlternateColorCodes('&', config.getString("sign.line1")));
                e.setLine(1, ChatColor.translateAlternateColorCodes('&', "&cInvalid value. 2nd"));
                e.setLine(2, ChatColor.translateAlternateColorCodes('&', "&cline set to either"));
                e.setLine(3, ChatColor.translateAlternateColorCodes('&', "&c'hub' or 'spawn'."));
                return;
            }

        }
    }

    @SuppressWarnings("unused")
    @EventHandler
    public void onUseSign(PlayerInteractEvent e) {
        if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
            Block b = e.getClickedBlock();
            if (b instanceof WSSign) {
                // if(b.getType() == Material.SIGN || b.getType() ==
                // Material.SIGN_POST || b.getType() == Material.WALL_SIGN) {
                WSSign sign = new WSSign((Sign) b, main);
                FileConfiguration config = main.getConfig();
                Sign s = (Sign) b.getState();
                String line1 = ChatColor.stripColor(s.getLine(0));
                String line2 = ChatColor.stripColor(s.getLine(1));
                // if(line1.contains(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&',
                // config.getString("sign.line1"))))) {
                Player p = e.getPlayer();
                // if(line2.contains(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&',
                // config.getString("sign.hub.line2"))))) {
                if (sign.getType() == SignType.HUB) {
                    if (p.hasPermission(new Permissions().sign_hub)) {
                        if (p.isSneaking() == false) {
                            p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                                    Lang.SIGN_HUB_DESTROY_DENY.toString()));
                            e.setCancelled(true);
                            return;
                        } else {
                            p.sendMessage(prefix
                                    + ChatColor.translateAlternateColorCodes('&', Lang.SIGN_HUB_DESTROY.toString()));
                            e.setCancelled(false);
                            return;
                        }
                    } else {
                        p.sendMessage(prefix
                                + ChatColor.translateAlternateColorCodes('&', Lang.SIGN_HUB_DESTROY_NOPERM.toString()));
                        e.setCancelled(true);
                        return;
                    }
                }
                // if(line2.contains(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&',
                // config.getString("sign.fhub.line2"))))) {
                if (sign.getType() == SignType.FHUB) {
                    if (p.hasPermission(new Permissions().sign_fhub)) {
                        if (p.isSneaking() == false) {
                            p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                                    Lang.SIGN_FHUB_DESTROY_DENY.toString()));
                            e.setCancelled(true);
                            return;
                        } else {
                            p.sendMessage(prefix
                                    + ChatColor.translateAlternateColorCodes('&', Lang.SIGN_FHUB_DESTROY.toString()));
                            e.setCancelled(false);
                            return;
                        }
                    } else {
                        p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                                Lang.SIGN_FHUB_DESTROY_NOPERM.toString()));
                        e.setCancelled(true);
                        return;
                    }
                }
                // if(line2.contains(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&',
                // config.getString("sign.spawn.line2"))))) {
                if (sign.getType() == SignType.SPAWN) {
                    if (p.hasPermission(new Permissions().sign_spawn)) {
                        if (p.isSneaking() == false) {
                            p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                                    Lang.SIGN_SPAWN_DESTROY_DENY.toString()));
                            e.setCancelled(true);
                            return;
                        } else {
                            p.sendMessage(prefix
                                    + ChatColor.translateAlternateColorCodes('&', Lang.SIGN_SPAWN_DESTROY.toString()));
                            e.setCancelled(false);
                            return;
                        }
                    } else {
                        p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                                Lang.SIGN_SPAWN_DESTROY_NOPERM.toString()));
                        e.setCancelled(true);
                        return;
                    }
                }
                // if(line2.contains(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&',
                // config.getString("sign.fspawn.line2"))))) {
                if (sign.getType() == SignType.FSPAWN) {
                    if (p.hasPermission(new Permissions().sign_fspawn)) {
                        if (p.isSneaking() == false) {
                            p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                                    Lang.SIGN_FSPAWN_DESTROY_DENY.toString()));
                            e.setCancelled(true);
                            return;
                        } else {
                            p.sendMessage(prefix
                                    + ChatColor.translateAlternateColorCodes('&', Lang.SIGN_FSPAWN_DESTROY.toString()));
                            e.setCancelled(false);
                            return;
                        }
                    } else {
                        p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                                Lang.SIGN_FSPAWN_DESTROY_NOPERM.toString()));
                        e.setCancelled(true);
                        return;
                    }
                }

            }
        }

        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            FileConfiguration config = main.getConfig();
            Player p = e.getPlayer();
            Block b = e.getClickedBlock();

            if (b.getType() == Material.SIGN || b.getType() == Material.SIGN_POST
                    || b.getType() == Material.WALL_SIGN) {
                Sign s = (Sign) b.getState();
                String line1 = ChatColor.stripColor(s.getLine(0));
                String line2 = ChatColor.stripColor(s.getLine(1));
                String line3 = ChatColor.stripColor(s.getLine(2));
                if (line1.equals(ChatColor.stripColor(config.getString("sign.line1")))) {
                    if (line2.equals(ChatColor.stripColor(config.getString("sign.hub.line2")))) {
                        String world = settings.getSpawns().getString("hub.world");
                        String xs = settings.getSpawns().getString("hub.x");
                        String ys = settings.getSpawns().getString("hub.y");
                        String zs = settings.getSpawns().getString("hub.z");
                        String yaws = settings.getSpawns().getString("hub.yaw");
                        String pitchs = settings.getSpawns().getString("hub.pitch");
                        if (world == null || xs == null || ys == null || zs == null || yaws == null || pitchs == null) {
                            p.sendMessage(prefix
                                    + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_HUB_NULL.toString()));
                            return;
                        } else {
                            double x = p.getLocation().getX();
                            double y = p.getLocation().getY();
                            double z = p.getLocation().getZ();
                            ScheduleHubTP hub = new ScheduleHubTP(main, p, 5, x, y, z);
                            hub.setTaskId(Bukkit.getScheduler().scheduleSyncRepeatingTask(main, hub, 0, 20));
                        }
                    }
                    if (line2.equals(ChatColor.stripColor(config.getString("sign.fhub.line2")))) {
                        String world = settings.getSpawns().getString("hub.world");
                        String xs = settings.getSpawns().getString("hub.x");
                        String ys = settings.getSpawns().getString("hub.y");
                        String zs = settings.getSpawns().getString("hub.z");
                        String yaws = settings.getSpawns().getString("hub.yaw");
                        String pitchs = settings.getSpawns().getString("hub.pitch");
                        if (world == null || xs == null || ys == null || zs == null || yaws == null || pitchs == null) {
                            p.sendMessage(prefix
                                    + ChatColor.translateAlternateColorCodes('&', Lang.COMMAND_HUB_NULL.toString()));
                            return;
                        } else {
                            World w = main.getServer().getWorld(settings.getHub().getString("hub.world"));
                            double x2 = settings.getHub().getDouble("hub.x");
                            double y2 = settings.getHub().getDouble("hub.y");
                            double z2 = settings.getHub().getDouble("hub.z");
                            float yaw = settings.getHub().getInt("hub.yaw");
                            float pitch = settings.getHub().getInt("hub.pitch");
                            p.teleport(new Location(w, x2, y2, z2, yaw, pitch));
                            p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                                    Lang.COMMAND_HUB_TELEPORTED.toString()));
                            if (enableSounds()) {
                                p.playSound(p.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 10F, 1.0F);
                            } else {
                                // Nothing.
                            }
                        }
                    }
                    if (line2.equalsIgnoreCase(ChatColor.stripColor(config.getString("sign.spawn.line2")))) {
                        settings.reloadConfig();
                        String worldname = line3;
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
                            double x = p.getLocation().getX();
                            double y = p.getLocation().getY();
                            double z = p.getLocation().getZ();
                            ScheduleSpawnTP spawn = new ScheduleSpawnTP(main, p, 5, worldname, x, y, z);
                            spawn.setTaskId(Bukkit.getScheduler().scheduleSyncRepeatingTask(main, spawn, 0, 20));
                            return;
                        }
                    }
                    if (line2.equalsIgnoreCase(ChatColor.stripColor(config.getString("sign.fspawn.line2")))) {
                        settings.reloadConfig();
                        String worldname = line3;
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
                            World w = main.getServer().getWorld(worldname);
                            double x = settings.getSpawns().getDouble("spawns." + worldname + ".x");
                            double y = settings.getSpawns().getDouble("spawns." + worldname + ".y");
                            double z = settings.getSpawns().getDouble("spawns." + worldname + ".z");
                            float yaw = settings.getSpawns().getInt("spawns." + worldname + ".yaw");
                            float pitch = settings.getSpawns().getInt("spawns." + worldname + ".pitch");
                            p.teleport(new Location(w, x, y, z, yaw, pitch));
                            p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',
                                    Lang.COMMAND_SPAWN_TELEPORT.toString()));
                            if (enableSounds()) {
                                p.playSound(p.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 10F, 1.0F);
                            } else {
                                // Nothing.
                            }
                            return;
                        }
                    } else {
                        // Do nothing.
                        return;
                    }
                } else {
                    // Do nothing.
                    return;
                }
            } else {
                // Do nothing.
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

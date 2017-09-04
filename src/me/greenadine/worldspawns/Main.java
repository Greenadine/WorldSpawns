package me.greenadine.worldspawns;

import me.greenadine.worldspawns.commands.*;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends JavaPlugin {

    SettingsManager settings;
    public Logger log;
    private static String name = "WorldSpawns";
    private static String cprefix = "[" + name + "] ";
    private static String versionID = "2.3";
    private static String minecraftID = "1.12.1";
    private static String craftbukkitID = "1.12.1";
    public static Plugin plugin;

    public static YamlConfiguration LANG;
    public static File LANG_FILE;
    
    public boolean portals;
    
    public Main() {
        settings = SettingsManager.getInstance();
        log = Logger.getLogger("Minecraft");
    }

    public void onEnable() {
        consoleMessage(cprefix + ChatColor.GREEN + "Enabling " + name + " plugin version " + ChatColor.RED + versionID
                + ChatColor.GREEN + "...");

        // Setup configuration file
        try {
            loadConfiguration();
        } catch (Exception e) {
            consoleMessage(ChatColor.RED + "Failed to setup configuration. This is a fatal error. Disabling plugin...");
            e.printStackTrace();
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Check components
        if(!getConfig().getBoolean("components.spawns") && !getConfig().getBoolean("components.hub") &&!getConfig().getBoolean("components.portals") && !getConfig().getBoolean("components.signs")) {
            consoleMessage(ChatColor.RED + "All components of WorldSpawns have been disabled. Disabling plugin...");
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }

        if(!getConfig().getBoolean("components.spawns")) {
            consoleMessage(ChatColor.RED + "Spawns feature disabled in config. To enable, set 'components.spawns' to 'true'.");
        }

        if(!getConfig().getBoolean("components.hub")) {
            consoleMessage(ChatColor.RED + "Hub feature disabled in config. To enable, set 'components.hub' to 'true'.");
        }

        if(!getConfig().getBoolean("components.portals")) {
            consoleMessage(ChatColor.RED + "Portals feature disabled in config. To enable, set 'components.portals' to 'true'.");
        }

        if(!getConfig().getBoolean("components.signs")) {
            consoleMessage(ChatColor.RED + "Signs feature disabled in config. To enable, set 'components.signs' to 'true'.");
        }

        // Setup language file
        try {
            loadLang();
        } catch (Exception e) {
            consoleMessage(
                    ChatColor.RED + "Failed to setup language files. This is a fatal error. Disabling plugin...");
            e.printStackTrace();
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Setup SettingsManager
        try {
            settings.setup(this);
        } catch (Exception e) {
            consoleMessage(
                    ChatColor.RED + "Failed to setup SettingsManager. This is a fatal error. Disabling plugin...");
            e.printStackTrace();
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Enable Portals
        if (getConfig().getBoolean("components.portals") == true) {
            if (getServer().getPluginManager().getPlugin("WorldEdit") == null) {
                this.getConfig().set("components.portals", false);
                this.portals = false;
                consoleMessage(ChatColor.YELLOW + "WorldEdit not installed. Portals feature disabled!");
            } else {
                this.getConfig().set("components.portals", true);
                this.portals = true;
                consoleMessage(ChatColor.GREEN + "WorldEdit installed. Portals feature enabled!");
            }
        } else {
            // Do not enable the portals function.
        }

        // Setup listeners
        if (getConfig().getBoolean("components.firework") == true) {
            getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        } else {
            // Do not setup firework listener.
        }

        if (getConfig().getBoolean("components.signs") == true) {
            getServer().getPluginManager().registerEvents(new SignListener(this), this);
        } else {
            // Do not setup sign listener
        }

        // Register commands
        try {
            getCommand("worldspawns").setExecutor(new CommandWorldspawns(this));
            if (getConfig().getBoolean("components.hub") == true) {
                getCommand("hub").setExecutor(new CommandHub(this));
                getCommand("fhub").setExecutor(new CommandFhub(this));
                getCommand("sethub").setExecutor(new CommandSethub(this));
                getCommand("resethub").setExecutor(new CommandResethub(this));
            } else {
                // Do not set executors for hub commands.
            }
            if (getConfig().getBoolean("components.spawns") == true) {
                getCommand("spawn").setExecutor(new CommandSpawn(this));
                getCommand("fspawn").setExecutor(new CommandFspawn(this));
                getCommand("setspawn").setExecutor(new CommandSetspawn(this));
                getCommand("delspawn").setExecutor(new CommandDelspawn(this));
                getCommand("setnewbie").setExecutor(new CommandSetnewbie(this));
                getCommand("resetnewbie").setExecutor(new CommandResetnewbie(this));
            } else {
                // Do not set executors for spawn commands.
            }
        } catch (Exception e) {
            consoleMessage(ChatColor.RED + "Failed to register commands! This is a fatal error. Disabling plugin...");
            e.printStackTrace();
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Notify that plugin has been enabled
        consoleMessage(cprefix + ChatColor.GREEN + "Enabled " + name + " plugin version " + ChatColor.RED + versionID
                + ChatColor.GREEN + " for CraftBukkit version " + ChatColor.RED + craftbukkitID + ChatColor.GREEN
                + "!");
    }

    public void onDisable() {
        try {
            if (getConfig().getBoolean("components.spawns") == true) {
                settings.saveSpawns();
            } else {
                // Do nothing.
            }
            if (getConfig().getBoolean("components.hub") == true) {
                settings.saveHub();
            } else {
                // Do nothing.
            }
        } catch (Exception e) {
            e.printStackTrace();
            consoleMessage(cprefix + ChatColor.RED
                    + "Failed to save spawns to config. Check error log above, and send to plugin developer if necessary.");
            consoleMessage(cprefix + ChatColor.GREEN + "Disabled " + name + " plugin version " + ChatColor.RED
                    + versionID + ChatColor.GREEN + "!");
            return;
        }
        consoleMessage(cprefix + ChatColor.GREEN + "Saved spawns to config!");
        consoleMessage(cprefix + ChatColor.GREEN + "Disabled " + name + " plugin version " + ChatColor.RED + versionID
                + ChatColor.GREEN + "!");
        return;
    }

    public void consoleMessage(String message) {
        getServer().getConsoleSender().sendMessage(message);
    }

    public void loadConfiguration() {
        FileConfiguration config = getConfig();
        config.options().header("Change the settings from the plugin.");
        config.addDefault("components.hub", Boolean.valueOf(true));
        config.addDefault("components.spawns", Boolean.valueOf(true));
        config.addDefault("components.signs", Boolean.valueOf(true));
        config.addDefault("components.portals", Boolean.valueOf(true));
        config.addDefault("components.firework", Boolean.valueOf(true));
        config.addDefault("settings.enableSounds", Boolean.valueOf(true));
        config.addDefault("settings.allowSpawnInAir", Boolean.valueOf(false));
        config.addDefault("settings.allowHubInAir", Boolean.valueOf(false));
        config.addDefault("settings.teleportDelay", Boolean.valueOf(true));
        config.addDefault("settings.teleportDelayHub", Integer.valueOf(4));
        config.addDefault("settings.teleportDelaySpawn", Integer.valueOf(4));
        config.addDefault("settings.countdownAllNumbers", Boolean.valueOf(false));
        config.addDefault("settings.spawn", "none");
        config.addDefault("sign.line1", "&9[WorldSpawns]");
        config.addDefault("sign.hub.line2", "&bhub");
        config.addDefault("sign.fhub.line2", "&bfhub");
        config.addDefault("sign.spawn.line2", "&2spawn");
        config.addDefault("sign.spawn.line3", "&6%worldname%");
        config.addDefault("sign.fspawn.line2", "&2fspawn");
        config.addDefault("sign.fspawn.line3", "&6%worldname%");
        config.addDefault("firework.random", Boolean.valueOf(true));
        config.addDefault("firework.rocket.type", "BALL");
        config.addDefault("firework.rocket.color", "RED");
        config.addDefault("firework.rocket.fade", "GREEN");
        config.addDefault("firework.rocket.power", Integer.valueOf(1));
        config.addDefault("firework.rocket.flicker", Boolean.valueOf(true));
        config.addDefault("firework.rocket.trail", Boolean.valueOf(true));
        config.options().copyDefaults(true);
        config.options().copyHeader(true);
        saveConfig();
    }

    public String getVersionID() {
        return versionID;
    }

    public String getMinecraftID() {
        return minecraftID;
    }

    public String getCraftbukkitID() {
        return craftbukkitID;
    }

    public YamlConfiguration getLang() {
        return LANG;
    }

    /**
     * Get the lang.yml file.
     *
     * @return The lang.yml file.
     */
    public File getLangFile() {
        return LANG_FILE;
    }

    public YamlConfiguration loadLang() {
        File lang = new File(getDataFolder(), File.separator + "lang" + File.separator + "en_US.yml");
        if (!lang.exists()) {
            try {
                getDataFolder().mkdir();
                lang.mkdir();
                InputStream defConfigStream = this
                        .getResource(getDataFolder() + File.separator + "lang" + File.separator + "en_US.yml");
                if (defConfigStream != null) {
                    File langFile = new File(getDataFolder(), File.separator + "lang" + File.separator + "en_US.yml");
                    YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(langFile);
                    defConfig.save(lang);
                    Lang.setFile(defConfig);
                    return defConfig;
                }
            } catch (IOException e) {
                e.printStackTrace(); // So they notice
                log.severe("[WorldSpawns] Couldn't create language file.");
                log.severe("[WorldSpawns] This is a fatal error. Now disabling");
                this.setEnabled(false); // Without it loaded, we can't send them
                // messages
            }
        }
        YamlConfiguration conf = YamlConfiguration.loadConfiguration(lang);
        for (Lang item : Lang.values()) {
            if (conf.getString(item.getPath()) == null) {
                conf.set(item.getPath(), item.getDefault());
            }
        }
        Lang.setFile(conf);
        LANG = conf;
        LANG_FILE = lang;
        try {
            conf.save(getLangFile());
        } catch (IOException e) {
            log.log(Level.WARNING, "WorldSpawns: Failed to save en_US.yml.");
            log.log(Level.WARNING, "WorldSpawns: Report this stack trace to Kevinzuman22.");
            e.printStackTrace();
        }
        return conf;
    }
}

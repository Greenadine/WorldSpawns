package me.greenadine.worldspawns;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * An enum for requesting strings from the language file.
 * @author gomeow
 */
public enum Lang {

    // General phrases
    PREFIX("prefix", "&9[WorldSpawns]"),
    NO_PERMISSION("no-permission", "&cYou have no permission to preform this action."),
    CHECK_HEADER_HUB("check-header-hub", "&9--> &fWorldSpawns Check - Hub &9<--"),
    CHECK_HEADER_WORLD("check-header-world", "&9--> &fWorldSpawns Check - Spawn of &6%worldname% &9<--"),
    PLAYER_JOIN_HEADER("player-join-header","&9&m----------->&r &fWorldSpawns &9&m<-----------"),
    PLAYER_JOIN_VERSION("player-joinnotification-version", "&fVersion: &6%versionid%&f."),
    PLAYER_JOIN_MCVERSION("player-joinnotification-mcversion", "&fFor Minecraft: &6%mcversionid%&f."),
    PLAYER_JOIN_CBVERSION("player-joinnotification-cbversion", "&fCraftbukkit: &6%cbversionid%&f."),
    TELEPORTING_IN_SECONDS("teleporting-in-seconds", "&fTeleporting in &e%counter%&f..."),
    TELEPORTING_CANCEL_MOVE("teleporting-cancel-move", "&cTeleport cancelled due to moving."),
    TELEPORTING_CANCEL_MOVE_SENDER("teleporting-cancel-move-sender", "&cTeleporting &6%target% &cwas cancelled due to moving."),
    TELEPORTING_CANCEL_DAMAGE("teleporting-cancel-damage", "&cTeleport cancelled due to taking damage."),
    TELEPORTING_CANCEL_DAMAGE_SENDER("teleporting-cancel-damage-sender", "&cTeleporting &6%target% &cwas cancelled due to taking damage."),
    FEATURE_WIP("feature-wip", "&fThis feature is not finished yet. Check again after new updates have been released."),

    // Component phrases
    COMPONENT_SPAWNS_DISABLED("component-spawns-disabled", "&fThe spawns feature is disabled."),
    COMPONENT_HUB_DISABLED("component-hub-disabled", "&fThe hub feature is disabled."),
    COMPONENT_PORTALS_DISABLED("component-portals-disabled", "&fThe portals feature is disabled."),
    COMPONENT_SIGNS_DISABLED("component-signs-disabled", "&fThe signs feature is disabled."),
    COMPONENT_FEATURE_DISABLED("component-feature-disabled", "&fThis feature is disabled."),
    COMPONENT_DISABLED_HUBSPAWN("component-protals-disabled-hubspawn", "&fThe portals feature is disabled, because both spawns and hub are also disabled. The portals have no function without having spawns or a hub."),

    // Command-related phrases
    // General phrases
    COMMAND_UNKNOWN_SUBCOMMAND("command-unknown-subcommand", "&cUnknown subcommand '%args%'. Use &6/%label% help &cor &6/%label% ? &cfor a list of commands."),
    COMMAND_NOARGS("command-no-args", "&fWorldSpawns plugin version %versionid% made by Kevinzuman22. Type &7/%label% help or &7/%label% ? &ffor a list of commands."),
    COMMAND_TOO_MANY_ARGUMENTS("command-too-many-arguments", "&cToo many arguments. Usage: &6/%label% [subcommand]&c."),
    // Help page phrases
    COMMAND_HELP_HEADER("command-help-header", "&9&m-->&r &fWorldSpawns Help &9&m<--"),
    COMMAND_HELP_NOTE("command-help-note", "&8NOTE: [] = required, <> = optional."),
    COMMAND_HELP_SPAWNS_DESC("command-help-spawns-desc", "&7/%label% spawns - &fShows a list of spawns-related commands."),
    COMMAND_HELP_HUB_DESC("command-help-hub-desc", "&7/%label% hub - &fShows a list of hub-related commands."),
    COMMAND_HELP_RELOAD_DESC("command-help-reload-desc", "&7/%label% reload - &fReload the plugin and it's config."),
    COMMAND_HELP_CHECK_DESC("command-help-check-desc", "&7/%label% check [worldname]|shub - &fCheck where the spawn is located of a world, or the hub."),
    COMMAND_HELP_ENABLE_DESC("command-help-hub-enable-desc", "&7/%label% enable [component] [true|false] - &fEnable or disable a component of the plugin."),
    COMMAND_HELP_SPAWNS_SPAWN_DESC("command-help-spawns-spawn-desc", "&7/spawn <world> <player> - &fTeleport yourself or another player to a world's spawn."),
    COMMAND_HELP_SPAWNS_FSPAWN_DESC("command-help-spawns-fspawn-desc", "&7/fspawn <world> <player> - &fTeleport yourself or another player to a world's spawn without delay."),
    COMMAND_HELP_SPAWNS_SETSPAWN_DESC("command-help-spawns-setspawn-desc", "&7/setspawn - &fSet the spawn of your current world."),
    COMMAND_HELP_SPAWNS_DELSPAWN_DESC("command-help-spawns-delspawn-desc", "&7/delspawn <world> - &fDelete the spawn of your current world, or another world's spawn."),
    COMMAND_HELP_SPAWNS_SETNEWBIE_DESC("command-help-spawns-setnewbie-desc", "&7/setnewbie - &fSet the spawn for new players."),
    COMMAND_HELP_SPAWNS_RESETNEWBIE_DESC("command-help-spawns-resetnewbie-desc", "&7/resetnewbie - &fReset the spawn for new players."),
    COMMAND_HELP_SPAWNS_RESET_DESC("command-help-reset-desc", "&7/%label% reset - &fReset the spawn of all worlds."),
    COMMAND_HELP_HUB_HUB_DESC("command-help-hub-hub-desc", "&7/hub <player> - &fTeleport yourself or another player to the server's hub."),
    COMMAND_HELP_HUB_FHUB_DESC("command-help-hub-fhub-desc", "&7/fhub <player> - &fTeleport yourself or another player to the server's hub without delay."),
    COMMAND_HELP_HUB_SETHUB_DESC("command-help-hub-sethub-desc", "&7/sethub - &fSet the server's hub to your current location."),
    COMMAND_HELP_HUB_RESETHUB_DESC("command-help-hub-resethub-desc", "&7/resethub - &fReset the server's hub."),
    COMMAND_HELP_TOOMANYARGS("command-help-toomanyargs", "&cToo many arguments (%lenght%). Usage: &6/%label% help&c."),
    // Commands
    // Reload
    COMMAND_RELOAD_SUCCESS("command-reload-success", "&fSuccessfuly reloaded plugin!"),
    COMMAND_RELOAD_FAIL("command-reload-fail", "&cFailed to reload plugin! Report this problem to plugin developer and/or the server administrator(s)."),
    COMMAND_RELOAD_TOOMANYARGS("command-reload-toomanyargs", "&cToo many arguments (%lenght%). Usage: &6/%label% reload&c."),

    // Enable
    COMMAND_ENABLE_INCORRECT("command-enable-incorrect", "&cIncorrect command usage. Usage: &6/%label% enable [component] [true|false]&c."),
    COMMAND_ENABLE_ENABLED("command-enable-enabled", "&fComponent &6%component%&f has been &aenabled."),
    COMMAND_ENABLE_DISABLED("command-enable-disabled", "&fComponent &6%component%&f has been &cdisabled."),
    COMMAND_ENABLE_WRONGBOOL("command-enable-wrongbool", "&cThis can only be set to either 'true' or 'false'."),
    COMMAND_ENABLE_COMPONENTDOESNOTEXIST("command-enable-componentdoesnotexist", "&cComponent &6%component%&c does not exist."),
    COMMAND_ENABLE_TOOMANYARGS("command-enable-toomanyargs", "&cToo many arguments (%lenght%). Usage: &6%label% enablehub [true|false]&c."),

    // Reset
    COMMAND_RESET_SUCCESS("command-reset-success", "&fAll spawns have been reset."),
    COMMAND_RESET_FAIL("command-reset-fail", "&cFailed to reset spawns. See console for information."),
    COMMAND_RESET_TOOMANYARGS("command-reset-toomanyargs", "&cToo many arguments (%lenght%). Usage: &6/%label% reset&c."),

    // Spawns
    COMMAND_SPAWNS_TOOMANYARGS("command-spawns-toomanyargs", "&cToo many arguments (%length%). Usage: &6/%label%&c."),

    // Check
    COMMAND_CHECK_DEFINE("command-check-define", "&cPlease define what to check the location from. Usage: &6/%label% check [worldname]|hub&c."),
    COMMAND_CHECK_HUB_NOTSET("command-check-hub-notset", "&fThe hub hasn't been set yet."),
    COMMAND_CHECK_INAIR_TRUE("command-check-inair-true", "&7In air: &ftrue&7."),
    COMMAND_CHECK_INAIR_FALSE("command-check-inair-false", "&7In air: &ffalse&7."),
    COMMAND_CHECK_WORLD_NULL("command-check-world-null", "&cWorld &6'%worldname%' &cdoesn't exist!"),
    COMMAND_CHECK_WORLD_SPAWN_NULL("command-check-world-spawn-null", "&fThe spawn of this world has not been set yet."),
    COMMAND_CHECK_TOOMANYARGS("command-reset-toomanyargs", "&cToo many arguments (%lenght%). Usage: &6/%label% reset&c."),

    // Spawn
    COMMAND_SPAWN_WORLD_NULL("command-spawn-world-null", "&cWorld &6%worldname% &cdoes not exist!"),
    COMMAND_SPAWN_TARGET_NULL("command-spawn-target-null", "&cCould not find player &6%target%&c!"),
    COMMAND_SPAWN_TARGET_EQUALS_SENDER("command-spawn-target-equals-sender", "&fTo teleport yourself to &aspawn&f, use &6/%label%&f."),
    COMMAND_SPAWN_NULL("command-spawn-null", "&cThe spawn of world &6%worldname% &cis not set yet."),
    COMMAND_SPAWN_TELEPORT("command-spawn-teleport", "&fTelepored to &aspawn&f."),
    COMMAND_SPAWN_TELEPORT_OTHER("command-spawn-teleport-other", "&fTeleported to &aspawn &fof world &6%worldname%&f."),
    COMMAND_SPAWN_TELEPORT_PLAYER("command-spawn-teleport-player", "&fTeleported &6%target% &fto the &aspawn&f of world &6%worldname%&f!"),
    COMMAND_SPAWN_CURRENT_NULL("command-spawn-current-null", "&cThe spawn for the world you are currently in is not set yet."),
    COMMAND_SPAWN_TOOFEWARGS("command-spawn-toofewargs", "&cToo few arguments (%lenght%. Usage: &6/%label% [player]&c."),
    COMMAND_SPAWN_TOOMANYARGS("command-spawn-toomanyargs", "&cToo many arguments (%lenght%. Usage: &6/%label% [player]&c."),

    // Setspawn
    COMMAND_SETSPAWN_SET("command-setspawn-set", "&fSpawn for world &6%worldname%&f has been set."),
    COMMAND_SETSPAWN_SET_INAIR("command-setspawn-set-inair", "&cNOTE: The spawn has been set in air."),
    COMMAND_SETSPAWN_SET_INAIR_DENY("command-setspawn-set-inair-deny", "&cThe location you want to set as the spawn is in the air. Please set '&6settings.allowSpawnInAir' &cto '&6true' &cin config to allow the spawn to be set in the air."),
    COMMAND_SETSPAWN_TOOMANYARGS("command-setspawn-toomanyargs", "&cToo many arguments (%lenght%). Usage: &6/%label%&c."),

    // Delspawn phrases
    COMMAND_DELSPAWN_WORLD_NULL("command-delspawn-world-null", "&cWorld &6%worldname% &cdoes not exist!"),
    COMMAND_DELSPAWN_SPAWN_NULL("command-delspawn-spawn-null", "&cThere is no spawn set for world &6%worldname% &cyet"),
    COMMAND_DELSPAWN_SPAWN_DELETED("command-delspawn-spawn-deleted", "&fDeleted spawn for world &6%worldname%&f!"),
    COMMAND_DELSPAWN_INCORRECT_USAGE("command-delspawn-incorrect-usage", "&cUsage: &6/%label% [world]&c."),
    COMMAND_DELSPAWN_TOOMANYARGS("command-delspawn-toomanyarg", "&cToo many arguments (%lenght%). Usage: &6/%label% <worldname>&c."),

    // Setnewbie
    COMMAND_SETNEWBIE_SET("command-setnewbie-set", "&fSpawn for new players has been set."),
    COMMAND_SETNEWBIE_SET_INAIR("command-setnewbie-set-inair", "&cNOTE: The newbie spawn has been set in air."),
    COMMAND_SETNEWBIE_SET_INAIR_DENY("command-setnewbie-set-inair-deny", "&cThe location you want to set as the newbie spawn is in the air. Please set '&6settings.allowSpawnInAir' &cto '&6true' &cin config to allow the newbie spawn to be set in the air."),
    COMMAND_SETNEWBIE_TOOMANYARGS("command-setnewbie-toomanyargs", "Too many arguments (%lenght%). Usage: &6/%label%&c."),

    // Resetnewbie
    COMMAND_RESETNEWBIE_RESET("command-resetnewbie-reset", "&fThe newbie spawn has been reset!"),
    COMMAND_RESETNEWBIE_NULL("command-resetnewbie-null", "&fThe newbie spawn has not been set yet."),
    COMMAND_RESETNEWBIE_TOOMANYARGS("command-resetnewbie-toomanyargs", "&cToo many arguments (%lenght%). Usage: &6/%label%&c."),

    // Hub phrases
    COMMAND_HUB_CONSOLE_DEFINE_PLAYER("command-hub-console-define-player", "&cDefine a player to teleport to the hub!"),
    COMMAND_HUB_TARGET_NULL("command-hub-console-target-null", "&cCould not find player &6%target%&c."),
    COMMAND_HUB_TARGET_TELEPORTED("command-hub-console-target-teleported", "&fTelepored &6%target% to the &bhub&f!"),
    COMMAND_HUB_TARGET_EQUALS_SENDER("command-hub-target-equals-sender", "&fTo teleport yourself to the &bhub&f, use &6/%label%&f."),
    COMMAND_HUB_NULL("command-hub-null", "&cThe hub isn't set yet."),
    COMMAND_HUB_TELEPORTED("command-hub-teleported", "&fTeleported to the &bhub&f!"),
    COMMAND_HUB_CONSOLE_TOOMANYARGS("command-hub-console-toomanyargs", "&cToo many arguments (%lenght%). Usage: &6/%label% [player]&c."),

    // Sethub phrases
    COMMAND_ONLY_PLAYERS("command-sethub-sender-console", "&cThis command can only be executed by a player!"),
    COMMAND_SETHUB_SET("command-sethub-set", "&fThe hub has been set!"),
    COMMAND_SETHUB_SET_INAIR("command-sethub-set-inair", "&cNOTE: The hub has been set in air."),
    COMMAND_SETHUB_SET_INAIR_DENY("command-sethub-set-inair-deny", "&cThe location you want to set as the hub is in the air. Please set '&6settings.allowHubInAir' &cto '&6true' &cin config to allow the hub to be set in the air."),
    COMMAND_SETHUB_TOOMANYARGS("command-sethub-toomanyargs", "&cToo many arguments (%lenght%). Usage: &6/%label%&c."),

    // Resethub phrases
    COMMAND_RESETHUB_RESET("command-resethub-reset", "&fThe hub has been reset!"),
    COMMAND_RESETHUB_TOOMANYARGS("command-resethub-toomanyargs", "&cToo many arguments (%lenght%). Usage: &6/%label%&c."),

    // Wand
    COMMAND_WAND_EXPLAINATION("command-wand-explaination", "&fSelect a region with the wand by setting position 1 by left clicking a block, and position 2 by right clicking a block."),
    COMMAND_WAND_HAS("command-wand-has", "&fYou already have a wand."),
    COMMAND_WAND_TOOMANYARGS("command-wand-toomanyargs", "&cToo many arguments (%lenght%). Usage: &6%label% wand&c."),

    // Setportal
    COMMAND_SETPORTAL_DEFINE("command-setportal-define", "&cPlease define the parimeters required. Usage: &6/%label% setportal [name] [hub|spawn] <world>&c."),
    COMMAND_SETPORTAL_HUB_SUCCESS("command-setportal-hub-success", "&fCreated &bhub &fportal with name &6%portalname%&f!"),
    COMMAND_SETPORTAL_HUB_FAILED("command-setportal-hub-failed", "&cFailed to create &bhub &cportal."),
    COMMAND_SETPORTAL_SPAWN_SUCCESS("command-setportal-spawn-success", "&fCreated &2spawn &fportal with name '&6%name%&f'!"),
    COMMAND_SETPORTAL_SPAWN_FAILED("command-setportal-spawn-failed", "&cFailed to create &2spawn &cportal."),
    COMMAND_SETPORTAL_EXISTS("command-setportal-spawn-exists", "&fA portal with the name '&6%name%&f' already exists."),
    COMMAND_SETPORTAL_TOOMANYARGS("command-setportal-toomanyargs", "&cToo many arguments (%lenght%). Usage: &6/%label% setportal [name] [hub|spawn] <world>&c."),

    // Sign phrases
    SIGN_HUB_CREATE("sign-hub-create" ,"&fCreated new &bhub &fteleport sign."),
    SIGN_HUB_DESTROY("sign-hub-destroy" ,"&fDestroyed &bhub &fteleport sign."),
    SIGN_HUB_DESTROY_DENY("sign-hub-destroy-deny", "&fYou must be sneaking to destroy a &bhub &fteleport sign."),
    SIGN_HUB_DESTROY_NOPERM("sign-hub-destroy-deny" ,"&cYou do not have permission to destroy a &bhub &cteleport sign."),
    SIGN_FHUB_CREATE("sign-fhub-create" ,"&fCreated new &bfhub &fteleport sign."),
    SIGN_FHUB_DESTROY("sign-fhub-destroy" ,"&fDestroyed &bfhub &fteleport sign."),
    SIGN_FHUB_DESTROY_DENY("sign-fhub-destroy-deny", "&fYou must be sneaking to destroy an &bfhub &fteleport sign."),
    SIGN_FHUB_DESTROY_NOPERM("sign-fhub-destroy-deny" ,"&cYou do not have permission to destroy an &bfhub &cteleport sign."),
    SIGN_SPAWN_CREATE("sign-spawn-create" , "&fCreated new &2spawn &fteleport sign."),
    SIGN_SPAWN_DESTROY("sign-spawn-destroy" ,"&fDestroyed &2spawn &fteleport sign."),
    SIGN_SPAWN_DESTROY_DENY("sign-spawn-destroy-deny", "&fYou must be sneaking to destroy a &2spawn &fteleport sign."),
    SIGN_SPAWN_DESTROY_NOPERM("sign-spawn-destroy-deny" ,"&cYou do not have permission to destroy a &2spawn &cteleport sign."),
    SIGN_FSPAWN_CREATE("sign-fspawn-create" , "&fCreated new &2fspawn &fteleport sign."),
    SIGN_FSPAWN_DESTROY("sign-fspawn-destroy" ,"&fDestroyed &2fspawn &fteleport sign."),
    SIGN_FSPAWN_DESTROY_DENY("sign-fspawn-destroy-deny", "&fYou must be sneaking to destroy an &2fspawn &fteleport sign."),
    SIGN_FSPAWN_DESTROY_NOPERM("sign-fspawn-destroy-deny" ,"&cYou do not have permission to destroy an &2fspawn &cteleport sign."),

    // Portal phrases
    PORTAL_SELECTION_NULL("selection-null", "&cPlease select a region."),
    PORTAL_SELECTION_TOO_BIG("selection-too-big", "&cYour selection can not be bigger than 50 blocks (Current: %blocks%)."),
    PORTAL_CREATING("portal-creating", "&fCreating %type% &fportal '&6%name%&f'..."),
    PORTAL_FILE_NULL("portal-file-null", "&cThe file of the portal '&6%name%&c' does not exist. Creating..."),
    PORTAL_FILE_CREATE_FAIL("portal-create-fail", "&cCould not create the file for portal '&6%name%&c'. See the console for information."),
    PORTAL_FILE_SAVE_FAIL("portal-file-save-fail", "&cFailed to save portal file of portal '&6%name%&c'. See console for information."),
    PORTAL_CREATED_HUB("portal-created-hub", "&fCreated new &bhub &fportal with name '&6%name%&f'!"),
    PORTAL_CREATED_SPAWN("portal-created-spawn", "&fCreated new &2spawn &fportal with name '&6%name%&f' for world &6%worldname%!");

    private String path;
    private String def;
    private static YamlConfiguration LANG;

    /**
     * Lang enum constructor.
     * @param path The string path.
     * @param start The default string.
     */
    Lang(String path, String start) {
        this.path = path;
        this.def = start;
    }

    /**
     * Set the {@code YamlConfiguration} to use.
     * @param config The config to set.
     */
    public static void setFile(YamlConfiguration config) {
        LANG = config;
    }

    @Override
    public String toString() {
        if (this == PREFIX)
            return ChatColor.translateAlternateColorCodes('&', LANG.getString(this.path, def)) + " ";
        return ChatColor.translateAlternateColorCodes('&', LANG.getString(this.path, def));
    }

    /**
     * Get the default value of the path.
     * @return The default value of the path.
     */
    public String getDefault() {
        return this.def;
    }

    /**
     * Get the path to the string.
     * @return The path to the string.
     */
    public String getPath() {
        return this.path;
    }
}
package me.greenadine.worldspawns.commands;

import org.bukkit.permissions.Permission;

public class Permissions {

    public Permission command_main;
    public Permission command_reload;
    public Permission command_spawns;
    public Permission command_hub;
    public Permission command_enable;
    public Permission command_wand;
    public Permission command_setportal;
    public Permission command_check;
    public Permission command_reset;
    public Permission hub;
    public Permission hub_players;
    public Permission fhub;
    public Permission fhub_players;
    public Permission hub_set;
    public Permission hub_reset;
    public Permission spawn;
    public Permission spawn_other;
    public Permission spawn_players;
    public Permission fspawn;
    public Permission fspawn_other;
    public Permission fspawn_players;
    public Permission spawn_set;
    public Permission spawn_delete;
    public Permission spawn_allworlds;
    public Permission newbie_set;
    public Permission newbie_reset;
    public Permission sign_hub;
    public Permission sign_spawn;
    public Permission sign_fhub;
    public Permission sign_fspawn;

    public Permissions() {
        command_main = new Permission("worldspawns.command");
        command_reload = new Permission("worldspawns.command.reload");
        command_spawns = new Permission("worldspawns.command.spawns");
        command_hub = new Permission("worldspawns.command.hub");
        command_enable = new Permission("worldspawns.command.enable");
        command_wand = new Permission("worldspawns.command.wand");
        command_setportal = new Permission("worldspawns.command.setportal");
        command_check = new Permission("worldspawns.command.check");
        command_reset = new Permission("worldspawns.command.reset");
        hub = new Permission("worldspawns.hub");
        hub_players = new Permission("worldspawns.hub.players");
        fhub = new Permission("worldspawns.fhub");
        fhub_players = new Permission("worldspawns.fhub.players");
        hub_set = new Permission("worldspawns.hub.set");
        hub_reset = new Permission("worldspawns.hub.reset");
        spawn = new Permission("worldspawns.spawn");
        spawn_other = new Permission("worldspawns.spawn.other");
        spawn_players = new Permission("worldspawns.spawn.players");
        fspawn = new Permission("worldspawns.fspawn");
        fspawn_other = new Permission("worldspawns.fspawn.other");
        fspawn_players = new Permission("worldspawns.fspawn.players");
        spawn_set = new Permission("worldspawns.spawn.set");
        spawn_delete = new Permission("worldspawns.spawn.delete");
        spawn_allworlds = new Permission("worldspawns.spawn.world.all");
        newbie_set = new Permission("worldspawns.newbie.set");
        newbie_reset = new Permission("worldspawns.newbie.reset");
        sign_hub = new Permission("worldspawns.sign.hub");
        sign_spawn = new Permission("worldspawns.sign.spawn");
        sign_fhub = new Permission("worldspawns.sign.fhub");
        sign_fspawn = new Permission("worldspawns.sign.fspawn");
    }
}

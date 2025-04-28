/*
 * Copyright 2025 Kevin "Greenadine" Zuman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dev.greenadine.worldspawns.command;

import co.aikar.commands.annotation.*;
import dev.greenadine.plcommons.CommandInvoker;
import dev.greenadine.plcommons.LanguageManager;
import dev.greenadine.plcommons.PLCommonsCommand;
import dev.greenadine.worldspawns.Config;
import dev.greenadine.worldspawns.DataManager;
import dev.greenadine.worldspawns.MessageKeys;
import dev.greenadine.worldspawns.Permissions;
import dev.greenadine.worldspawns.task.DelayedSpawnTeleportTask;
import dev.greenadine.worldspawns.util.Cooldowns;
import dev.greenadine.worldspawns.util.TeleportOptions;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

@CommandAlias("spawn")
@Description("Teleport to the spawn.")
@CommandPermission(Permissions.SPAWN)
@Conditions("player-only")
public class SpawnCommand extends PLCommonsCommand {

    private @Dependency DataManager dataManager;
    private @Dependency Config config;
    private @Dependency Cooldowns cooldowns;
    private @Dependency LanguageManager languageManager;

    @Default
    @CommandCompletion("@tp-options-and-worlds @tp-options")
    public void onDefault(CommandInvoker invoker, @Optional World world, @Optional TeleportOptions options) {
        final Player player = invoker.asPlayer();
        if (world == null)
            world = player.getWorld();
        final String worldName = world.getName();
        final boolean diffWorld = !player.getWorld().getUID().equals(world.getUID());

        // Check if player has permission to teleport to the world
        if (diffWorld && !Permissions.hasPermissionForWorld(player, world)) {
            invoker.sendError(MessageKeys.TELEPORT_WORLD_NO_PERMISSION, "world", worldName);
            return;
        }

        // Check if the spawn is set
        final Location location = dataManager.getWorldSpawn(world);
        if (location == null) {
            invoker.sendError(MessageKeys.SPAWN_NOT_SET, "world", worldName);
            return;
        }

        // Check if the player is on cooldown for the world
        if (cooldowns.hasCooldown(player, worldName)) {
            invoker.sendError(MessageKeys.TELEPORT_ON_COOLDOWN, "time", cooldowns.getCooldownFormatted(player, worldName));
            return;
        }

        // Teleport to the spawn immediately if the option is defined
        if (options != null && options.hasAny("force", "f", "now", "n")) {
            if (!player.hasPermission(Permissions.FORCE)) {
                DelayedSpawnTeleportTask.run(player, world);  // Teleport after delay
                return;
            }
            player.teleport(dataManager.getWorldSpawn(world));
            final String message;
            if (diffWorld)
                //noinspection ConstantConditions
                message = languageManager.formatMessage(player, MessageKeys.TELEPORTED_TO_WORLD, "world", worldName);
            else
                message = languageManager.formatMessage(player, MessageKeys.TELEPORTED_TO_SPAWN);
            invoker.sendMessage(" " + message);
            return;
        }

        // Put on cooldown if enabled in config
        final int tpCooldown = config.getTeleportCooldown(world);
        if (tpCooldown > 0)
            cooldowns.setCooldown(player, worldName, tpCooldown);

        DelayedSpawnTeleportTask.run(player, world);  // Teleport after delay
    }

    @Subcommand("set")
    @Description("Set the spawn for the world you are in.")
    @CommandPermission(Permissions.MANAGE_SPAWN)
    public void onSet(CommandInvoker invoker) {
        final Player player = invoker.asPlayer();
        final World world = player.getWorld();

        dataManager.setWorldSpawn(world, player.getLocation());
        invoker.sendInfo(true, MessageKeys.SPAWN_SET, "world", world.getName());
    }

    @Subcommand("clear")
    @Description("Clear the spawn for a world.")
    @CommandPermission(Permissions.MANAGE_SPAWN)
    public void onClear(CommandInvoker invoker, @Optional World world) {
        final Player player = invoker.asPlayer();
        if (world == null)
            world = player.getWorld();

        dataManager.setWorldSpawn(world, null);
        invoker.sendInfo(true, MessageKeys.SPAWN_CLEARED, "world", world.getName());
    }
}

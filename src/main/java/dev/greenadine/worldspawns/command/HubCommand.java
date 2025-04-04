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
import dev.greenadine.worldspawns.task.DelayedHubTeleportTask;
import dev.greenadine.worldspawns.util.Cooldowns;
import dev.greenadine.worldspawns.util.TeleportOptions;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@CommandAlias("hub|h")
@Description("Teleport to the hub.")
@CommandPermission(Permissions.HUB)
public class HubCommand extends PLCommonsCommand {

    private @Dependency DataManager dataManager;
    private @Dependency Config config;
    private @Dependency Cooldowns cooldowns;
    private @Dependency LanguageManager languageManager;

    private static final String _cooldownKey = "hub";

    @Default
    @Conditions("player-only")
    @CommandCompletion("@tp-options")
    public void onDefault(CommandInvoker invoker, @Optional TeleportOptions options) {
        // Check if the hub is set
        final Location location = dataManager.getHub();
        if (location == null)
            return;  // Act as if the command doesn't exist

        final Player player = invoker.asPlayer();

        // Check if the player is on cooldown for the world
        if (cooldowns.hasCooldown(player, _cooldownKey)) {
            invoker.sendError(MessageKeys.TELEPORT_ON_COOLDOWN, "time", cooldowns.getCooldownFormatted(player, _cooldownKey));
            return;
        }

        // Teleport to the hub immediately if the option is defined
        if (options != null && options.hasAny("force", "f", "now", "n")) {
            if (!player.hasPermission(Permissions.FORCE)) {
                DelayedHubTeleportTask.run(player);  // Teleport after delay
                return;
            }
            player.teleport(location);
            final String message = languageManager.formatMessage(player, MessageKeys.TELEPORTED_TO_HUB);
            invoker.sendMessage(" " + message);
            return;
        }

        // Put on cooldown if enabled in config
        final int tpCooldown = config.hubTeleportCooldown;
        if (tpCooldown > 0)
            cooldowns.setCooldown(player, _cooldownKey, tpCooldown);

        DelayedHubTeleportTask.run(player);  // Teleport after delay
    }

    @Subcommand("set")
    @Description("Set the hub to your current location.")
    @CommandPermission(Permissions.MANAGE_HUB)
    @Conditions("player-only")
    public void onSet(CommandInvoker invoker) {
        dataManager.setHub(invoker.asPlayer().getLocation());
        invoker.sendInfo(true, MessageKeys.HUB_SET);
    }

    @Subcommand("clear")
    @Description("Clears the hub.")
    @CommandPermission(Permissions.MANAGE_HUB)
    public void onClear(CommandInvoker invoker) {
        dataManager.setHub(null);
        invoker.sendInfo(true, MessageKeys.HUB_CLEARED);
    }
}

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
package dev.greenadine.worldspawns.task;

import co.aikar.commands.MessageType;
import co.aikar.commands.annotation.Dependency;
import dev.greenadine.plcommons.LanguageManager;
import dev.greenadine.plcommons.PLCommons;
import dev.greenadine.plcommons.Scheduling;
import dev.greenadine.worldspawns.Config;
import dev.greenadine.worldspawns.DataManager;
import dev.greenadine.worldspawns.MessageKeys;
import dev.greenadine.worldspawns.util.PlayerUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;

// Task for teleporting a player to the hub after a (configurable) delay
public class DelayedHubTeleportTask implements Runnable {

    /**
     * Runs a delayed teleport to the hub task.
     *
     * @param player the player to teleport.
     */
    public static void run(Player player) {
        new DelayedHubTeleportTask(player);
    }

    private final Player player;
    private @Dependency DataManager dataManager;
    private @Dependency Config config;
    private @Dependency LanguageManager languageManager;

    private final int delay;
    private int counter;
    private final Location initLoc;
    private final Runnable cancel;

    private DelayedHubTeleportTask(Player player) {
        PLCommons.getDependencyManager().injectDependencies(this);
        this.player = player;
        this.delay = config.hubTeleportDelay;
        this.counter = delay;
        this.initLoc = player.getLocation();
        this.cancel = Scheduling.runRepeatingAsync(this, 0, 20)::cancel;
    }

    @Override
    public void run() {
        if (counter == delay) {
            final String message = languageManager.formatMessage(player, MessageKeys.TELEPORTING, "seconds", String.valueOf(counter));
            player.sendMessage(" " + message);
            counter--;
            return;
        }

        // Cancel if the player moved
        if (config.cancelTeleportOnMove && PlayerUtils.hasMoved(player, initLoc)) {
            final String message = languageManager.formatMessage(player, MessageType.ERROR, MessageKeys.TELEPORT_CANCELLED_MOVE);
            player.sendMessage(" " + message);
            cancel.run();
            return;
        }

        // Cancel if the player took damage
        if (config.cancelTeleportOnDamage && PlayerUtils.hasTakenDamage(player)) {
            final String message = languageManager.formatMessage(player, MessageType.ERROR, MessageKeys.TELEPORT_CANCELLED_DAMAGE);
            player.sendMessage(" " + message);
            cancel.run();
            return;
        }

        // Teleport to the hub if counter is 0
        if (counter == 0) {
            Scheduling.runSync(() -> player.teleport(dataManager.getHub()));
            final String message = languageManager.formatMessage(player, MessageKeys.TELEPORTED_TO_HUB);
            player.sendMessage(" " + message);
            cancel.run();
            return;
        }
        counter--;
    }
}

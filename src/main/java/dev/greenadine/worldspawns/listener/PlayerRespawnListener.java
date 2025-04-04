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
package dev.greenadine.worldspawns.listener;

import co.aikar.commands.annotation.Dependency;
import dev.greenadine.worldspawns.Config;
import dev.greenadine.worldspawns.DataManager;
import dev.greenadine.worldspawns.util.PlayerUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

// Listener for teleporting players on respawn
public class PlayerRespawnListener implements Listener {

    private @Dependency DataManager dataManager;
    private @Dependency Config config;

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        final Player player = event.getPlayer();
        final Location location = PlayerUtils.getTeleportLocation(player, config.teleportOnRespawnSetting, config.teleportOnRespawnValue, dataManager);
        if (location == null)
            return;
        event.setRespawnLocation(location);
    }
}

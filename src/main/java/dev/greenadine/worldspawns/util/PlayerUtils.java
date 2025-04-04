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
package dev.greenadine.worldspawns.util;

import dev.greenadine.worldspawns.Config;
import dev.greenadine.worldspawns.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public final class PlayerUtils {

    /**
     * Checks whether a player has moved too far from the specified location.
     *
     * @param player the player to check.
     * @param from   the location to check against.
     * @return {@code true} if the player has moved, {@code false} otherwise.
     */
    public static boolean hasMoved(Player player, Location from) {
        return player.getLocation().distance(from) > 0.1;
    }

    /**
     * Checks if the player has taken damage.
     *
     * @param player the player to check.
     * @return {@code true} if the player has taken damage, {@code false} otherwise.
     */
    public static boolean hasTakenDamage(Player player) {
        return player.getNoDamageTicks() > 0;
    }

    /**
     * Get the location to teleport the player to based on the setting.
     *
     * @param player       the player to teleport.
     * @param setting      the setting to use.
     * @param dataManager the spawn manager.
     * @return the location to teleport the player to.
     */
    public static Location getTeleportLocation(Player player, Config.TeleportSetting setting, String settingValue, DataManager dataManager) {
        switch (setting) {
            case SPAWN:
                return dataManager.getWorldSpawn(player.getWorld());
            case WORLD:
                final World world = Bukkit.getWorld(settingValue);
                if (world == null)
                    return null;
                return dataManager.getWorldSpawn(world);
            case NEWBIE:
                return dataManager.getNewbieSpawn();
            case HUB:
                return dataManager.getHub();
            default:
                return null;
        }
    }
}

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
package dev.greenadine.worldspawns;

import dev.greenadine.plcommons.PLCommonsConfig;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class Config extends PLCommonsConfig {

    // The language to use for localization
    public final Locale language = getEntry("language", Locale.ENGLISH)
            .parser(Locale::forLanguageTag)
            .get();

    // Whether teleporting should be cancelled if the player moves
    public final boolean cancelTeleportOnMove = getOrDefault("cancel-teleport-on-move", true);

    // Whether teleporting should be cancelled if the player takes damage
    public final boolean cancelTeleportOnDamage = getOrDefault("cancel-teleport-on-damage", true);

    // The teleport setting for teleporting players on respawn
    public final TeleportSetting teleportOnRespawnSetting = getEntry("teleport-on-respawn", TeleportSetting.SPAWN)
            .parser(TeleportSetting::fromString)
            .get();

    // The value of the teleport setting for teleporting players on respawn, if the value is a world name
    public final String teleportOnRespawnValue = getOrDefault("teleport-on-respawn", TeleportSetting.SPAWN.name);

    // The teleport setting for teleporting players on join
    public final TeleportSetting teleportOnJoinSetting = getEntry("teleport-on-join", TeleportSetting.DISABLE)
            .parser(TeleportSetting::fromString)
            .get();

    // The value of the teleport setting for teleporting players on join, if the value is a world name
    public final String teleportOnJoinValue = getOrDefault("teleport-on-join", TeleportSetting.DISABLE.name);

    // The teleport setting for teleporting players on join
    public final TeleportSetting teleportNewPlayersSetting = getEntry("teleport-new-players", TeleportSetting.NEWBIE)
            .parser(TeleportSetting::fromString)
            .validate(setting -> setting != TeleportSetting.SPAWN, "Cannot set 'teleport-new-players' to 'spawn'")
            .get();

    // The value of the teleport setting for teleporting players on join, if the value is a world name
    public final String teleportNewPlayersValue = getOrDefault("teleport-new-players", TeleportSetting.NEWBIE.name);

    // Teleport delay for teleporting to the hub
    public final int hubTeleportDelay = getOrDefault("teleport-delay.hub", 5);

    // Default teleport delay for teleporting to spawn
    private final int _defaultSpawnDelay = getOrDefault("teleport-delay.spawn.default", 5);

    // Get the spawn delay for a specific world
    public int getTeleportDelay(@NotNull World world) {
        return getOrDefault("teleport-delay.spawn." + world.getName(), _defaultSpawnDelay);
    }

    // Teleport cooldown for teleporting to the hub
    public final int hubTeleportCooldown = getOrDefault("teleport-cooldown.hub", 0);

    // Default teleport cooldown for teleporting to spawn
    private final int _defaultSpawnCooldown = getOrDefault("teleport-cooldown.spawn", 0);

    // Get the spawn cooldown for a specific world
    public int getTeleportCooldown(@NotNull World world) {
        return getOrDefault("teleport-cooldown.spawn." + world.getName(), _defaultSpawnCooldown);
    }

    public enum TeleportSetting {
        SPAWN,
        WORLD,
        NEWBIE,
        HUB,
        DISABLE;

        final String name;

        TeleportSetting() {
            this.name = name().toLowerCase();
        }

        static TeleportSetting fromString(String str) {
            try {
                return TeleportSetting.valueOf(str.toUpperCase());
            } catch (IllegalArgumentException e) {
                return WORLD;
            }
        }
    }
}

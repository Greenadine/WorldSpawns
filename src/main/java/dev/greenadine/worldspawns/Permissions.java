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

import org.bukkit.World;
import org.bukkit.entity.Player;

public final class Permissions {

    private static final String PREFIX = "worldspawns.";

    public static final String ADMIN = PREFIX + ".admin";

    public static final String SPAWN = PREFIX + "spawn";
    public static final String MANAGE_SPAWN = SPAWN + ".manage";
    public static final String NEWBIE = PREFIX + "newbie";
    public static final String HUB = PREFIX + "hub";
    public static final String MANAGE_HUB = HUB + ".manage";
    public static final String FORCE = PREFIX + "force";

    private static final String WORLD = SPAWN + ".world";

    // Checks if the player has permission to teleport to the specified world.
    public static boolean hasPermissionForWorld(Player player, World world) {
        return player.hasPermission(WORLD + ".*") || player.hasPermission(WORLD + "." + world.getName()) || player.hasPermission(ADMIN);
    }
}

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

public final class Permissions {

    private static final String PREFIX = "worldspawns.";

    public static final String HUB = PREFIX + "hub";
    public static final String MANAGE_HUB = HUB + ".manage";
    public static final String SPAWN = PREFIX + "spawn";
    public static final String SPAWN_OTHER = SPAWN + ".other";
    public static final String MANAGE_SPAWN = SPAWN + ".manage";
    public static final String NEWBIE = PREFIX + "newbie";
    public static final String FORCE = PREFIX + "force";

    /**
     * Gets the permission for the specified world.
     *
     * @param world the world to get the permission for.
     * @return the permission for the specified world.
     */
    public static String getWorldPermission(World world) {
        return SPAWN + ".world." + world.getName();
    }
}

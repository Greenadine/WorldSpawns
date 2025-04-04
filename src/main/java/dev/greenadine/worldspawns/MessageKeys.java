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

import co.aikar.locales.MessageKey;
import co.aikar.locales.MessageKeyProvider;
import org.jetbrains.annotations.NotNull;

public enum MessageKeys implements MessageKeyProvider {

    SPAWN_NOT_SET,
    SPAWN_SET,
    SPAWN_CLEARED,
    NEWBIE_HELP,
    NEWBIE_NOT_SET,
    NEWBIE_SET,
    NEWBIE_CLEARED,
    HUB_SET,
    HUB_CLEARED,
    TELEPORTING,
    TELEPORT_CANCELLED_MOVE,
    TELEPORT_CANCELLED_DAMAGE,
    TELEPORTED_TO_SPAWN,
    TELEPORTED_TO_WORLD,
    TELEPORTED_TO_NEWBIE,
    TELEPORTED_TO_HUB,
    TELEPORT_WORLD_NO_PERMISSION,
    MUST_BE_PLAYER,
    TELEPORT_ON_COOLDOWN,
    SECOND,
    SECONDS,
    MINUTE,
    MINUTES,
    HOUR,
    HOURS,
    ;

    private static final String PREFIX = "worldspawns.";

    private final MessageKey key;

    MessageKeys() {
        this.key = MessageKey.of(PREFIX + this.name().toLowerCase());
    }

    @Override
    @NotNull
    public MessageKey getMessageKey() {
        return key;
    }
}

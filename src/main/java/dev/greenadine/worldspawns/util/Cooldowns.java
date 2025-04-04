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

import co.aikar.commands.lib.util.Table;
import dev.greenadine.plcommons.LanguageManager;
import dev.greenadine.worldspawns.MessageKeys;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Cooldowns {

    private final Table<UUID, String, Long> cooldowns = new Table<>();
    private final LanguageManager languageManager;

    public Cooldowns(LanguageManager languageManager) {
        this.languageManager = languageManager;
    }

    /**
     * Sets a cooldown for a player.
     *
     * @param player the player.
     * @param key    the cooldown key.
     * @param delay  the delay in milliseconds.
     */
    public void setCooldown(Player player, String key, long delay) {
        cooldowns.put(player.getUniqueId(), key, System.currentTimeMillis() + delay);
    }

    /**
     * Checks if a player has a cooldown.
     *
     * @param player the player.
     * @param key    the cooldown key.
     * @return {@code true} if the player has a cooldown, {@code false} otherwise.
     */
    public boolean hasCooldown(Player player, String key) {
        return cooldowns.get(player.getUniqueId(), key) != null && cooldowns.get(player.getUniqueId(), key) > System.currentTimeMillis();
    }

    /**
     * Gets the remaining time of a cooldown.
     *
     * @param player the player.
     * @param key    the cooldown key.
     * @return the remaining time in milliseconds.
     */
    public long getRemaining(Player player, String key) {
        return cooldowns.get(player.getUniqueId(), key) - System.currentTimeMillis();
    }

    /**
     * Gets the remaining time of a cooldown formatted (hours, minutes, seconds).
     *
     * @param player the player.
     * @param key    the cooldown key.
     * @return the formatted remaining time.
     */
    public String getCooldownFormatted(Player player, String key) {
        long remaining = getRemaining(player, key);
        if (remaining <= 0)
            return "0 " + languageManager.formatMessage(player, MessageKeys.SECONDS);

        final long hours = remaining / 3600000;
        remaining %= 3600000;
        final long minutes = remaining / 60000;
        remaining %= 60000;
        final long seconds = remaining / 1000;

        final StringBuilder formatted = new StringBuilder();
        if (hours > 0)
            formatted.append(hours)
                     .append(" ")
                     .append(languageManager.formatMessage(player, hours == 1 ? MessageKeys.HOUR : MessageKeys.HOURS))
                     .append(" ");
        if (minutes > 0)
            formatted.append(minutes)
                     .append(" ")
                     .append(languageManager.formatMessage(player, minutes == 1 ? MessageKeys.MINUTE : MessageKeys.MINUTES))
                     .append(" ");
        if (seconds > 0)
            formatted.append(seconds)
                     .append(" ")
                     .append(languageManager.formatMessage(player, seconds == 1 ? MessageKeys.SECOND : MessageKeys.SECONDS));

        return formatted.toString().trim();
    }
}

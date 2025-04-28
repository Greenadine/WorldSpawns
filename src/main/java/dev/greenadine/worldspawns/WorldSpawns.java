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

import co.aikar.commands.MessageType;
import dev.greenadine.plcommons.*;
import dev.greenadine.worldspawns.command.Commands;
import dev.greenadine.worldspawns.listener.PlayerJoinListener;
import dev.greenadine.worldspawns.listener.PlayerRespawnListener;
import dev.greenadine.worldspawns.util.Cooldowns;
import org.bukkit.ChatColor;

import java.util.Locale;

public class WorldSpawns extends PLCommonsPlugin {

    @Override
    protected void onPluginEnable() throws PluginEnableException {
        if (getCommand("spawn") == null)
            throw new PluginEnableException("Unable to register the '/spawn' command. This can be caused by other plugins or by editing the plugin.yml file.");
        if (getCommand("newbie") == null)
            PluginLogger.warn("Unable to register the '/newbie' command. This can be caused by other plugins or by editing the plugin.yml file.");
        if (getCommand("hub") == null)
            PluginLogger.warn("Unable to register the '/hub' command. This can be caused by other plugins or by editing the plugin.yml file.");

        final DependencyManager dm = PLCommons.getDependencyManager();
        // Config & SpawnManager
        saveDefaultConfig();
        final Config config = new Config();
        dm.registerDependency(Config.class, config);
        final DataManager dataManager = new DataManager();
        dm.registerDependency(dataManager);

        // Language manager
        final Locale configLang = config.language;
        if (!isLanguageSupported(configLang))
            throw new PluginEnableException(configLang.getDisplayName(Locale.ENGLISH) + " (" + configLang.getDisplayName(configLang) + ")" + " is not supported.");
        final LanguageManager languageManager = new LanguageManager(configLang);
        languageManager.addMessageBundle("WorldSpawns");
        languageManager.setFormat(MessageType.INFO, ChatColor.WHITE, ChatColor.GRAY, ChatColor.GOLD);
        dm.registerDependency(LanguageManager.class, languageManager);

        // Listeners
        if (config.teleportOnJoinSetting != Config.TeleportSetting.DISABLE || config.teleportNewPlayersSetting != Config.TeleportSetting.DISABLE)
            Events.registerListener(new PlayerJoinListener(), true);
        if (config.teleportOnRespawnSetting != Config.TeleportSetting.DISABLE)
            Events.registerListener(new PlayerRespawnListener(), true);

        // Commands
        dm.registerDependency(new Cooldowns(languageManager));
        Commands.init();
    }

    // Checks if the specified language is supported
    private boolean isLanguageSupported(Locale language) {
        // Construct the resource file name based on the language
        final String resourceFileName = "WorldSpawns_" + language.getLanguage() + ".properties";
        // Check if the resource file exists in the classpath
        return getResource(resourceFileName) != null;
    }
}

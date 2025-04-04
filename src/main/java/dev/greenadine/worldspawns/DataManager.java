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

import dev.greenadine.plcommons.PLCommons;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class DataManager {

    private final Map<String, Location> worldSpawns;
    private Location newbieSpawn;
    private Location hub;

    DataManager() {
        final File dataFile = new File(PLCommons.getPlugin().getDataFolder(), "data.yml");
        if (!dataFile.exists())
            PLCommons.getPlugin().saveResource("data.yml", false);

        final FileConfiguration data = YamlConfiguration.loadConfiguration(dataFile);
        this.worldSpawns = loadWorldSpawns(data);
        this.newbieSpawn = loadNewbieSpawnLocation(data);
        this.hub = loadHubLocation(data);
    }

    /**
     * Gets the spawn location for the specified world.
     *
     * @param world the world to get the spawn location for.
     * @return the spawn location for the specified world.
     */
    public Location getWorldSpawn(World world) {
        return worldSpawns.get(world.getName());
    }

    /**
     * Gets the spawn location for the specified world.
     *
     * @param world the name of the world to get the spawn location for.
     * @return the spawn location for the specified world.
     */
    public Location getWorldSpawn(String world) {
        return worldSpawns.get(world);
    }

    /**
     * Sets the spawn location for the specified world.
     *
     * @param world    the world to set the spawn location for.
     * @param location the location to set as the spawn location.
     */
    public void setWorldSpawn(World world, Location location) {
        worldSpawns.put(world.getName(), location);
        save();
    }

    /**
     * Gets the spawn location for new players.
     *
     * @return the spawn location for new players.
     */
    public Location getNewbieSpawn() {
        return newbieSpawn;
    }

    /**
     * Sets the spawn location for new players.
     *
     * @param location the location to set as the spawn location for new players.
     */
    public void setNewbieSpawn(Location location) {
        this.newbieSpawn = location;
        save();
    }

    /**
     * Gets the hub location.
     *
     * @return the hub location.
     */
    public Location getHub() {
        return hub;
    }

    /**
     * Sets the hub location.
     *
     * @param hub the location to set as the hub.
     */
    public void setHub(Location hub) {
        this.hub = hub;
        save();
    }

    public void save() {
        final File dataFile = new File(PLCommons.getPlugin().getDataFolder(), "data.yml");
        final FileConfiguration data = YamlConfiguration.loadConfiguration(dataFile);

        // Save world spawns
        final ConfigurationSection worlds = data.createSection("worlds");
        for (final Map.Entry<String, Location> entry : worldSpawns.entrySet())
            worlds.set(entry.getKey(), entry.getValue());

        // Save newbie spawn
        if (newbieSpawn != null)
            data.set("newbieSpawn", newbieSpawn);

        // Save hub
        if (hub != null)
            data.set("hub", hub);

        try {
            data.save(dataFile);
        } catch (Exception ex) {
            PLCommons.handleThrown(ex);
        }
    }

    // Load world spawns from file
    private static Map<String, Location> loadWorldSpawns(FileConfiguration data) {
        final Map<String, Location> worldSpawns = new HashMap<>();
        final ConfigurationSection section = data.getConfigurationSection("worlds");
        if (section == null)
            return worldSpawns;

        for (final String world : section.getKeys(false))
            worldSpawns.put(world, section.getLocation(world));
        return worldSpawns;
    }

    // Load hub location from file
    private static Location loadHubLocation(FileConfiguration data) {
        if (!data.isSet("hub"))
            return null;
        return data.getLocation("hub");
    }

    // Load newbie spawn from file
    private static Location loadNewbieSpawnLocation(FileConfiguration data) {
        if (!data.isSet("newbieSpawn"))
            return null;
        return data.getLocation("newbieSpawn");
    }
}

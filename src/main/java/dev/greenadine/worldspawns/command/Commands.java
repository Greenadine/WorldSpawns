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
package dev.greenadine.worldspawns.command;

import co.aikar.commands.BukkitCommandManager;
import co.aikar.commands.CommandCompletions;
import co.aikar.commands.CommandConditions;
import co.aikar.commands.ConditionFailedException;
import com.google.common.collect.ImmutableList;
import dev.greenadine.plcommons.PLCCommandManager;
import dev.greenadine.worldspawns.MessageKeys;
import dev.greenadine.worldspawns.Permissions;
import dev.greenadine.worldspawns.util.TeleportOptions;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Commands {

    // Registers commands
    public static void init() {
        final PLCCommandManager commandManager = new PLCCommandManager();

        final BukkitCommandManager bukkitManager = commandManager.getCommandManager();

        // Context resolvers
        bukkitManager.getCommandContexts().registerOptionalContext(TeleportOptions.class, c -> new TeleportOptions(c.getArgs()));

        // Command conditions
        final CommandConditions<?, ?, ?> commandConditions = bukkitManager.getCommandConditions();
        commandConditions.addCondition("player-only", c -> {
            if (!c.getIssuer().isPlayer())
                throw new ConditionFailedException(MessageKeys.MUST_BE_PLAYER);
        });

        // Command completions
        final CommandCompletions<?> commandCompletions = bukkitManager.getCommandCompletions();
        commandCompletions.registerAsyncCompletion("tp-options-and-worlds", c -> {
            final List<String> completions = new ArrayList<>();
            if (c.getIssuer().hasPermission(Permissions.FORCE))
                Collections.addAll(completions, "-force", "-f", "-now", "-n");

            for (World world : Bukkit.getWorlds())
                completions.add(world.getName());
            return completions;
        });
        commandCompletions.registerCompletion("tp-options", c -> {
            if (c.getIssuer().hasPermission(Permissions.FORCE))
                return ImmutableList.of("-force", "-f", "-now", "-n");
            else
                return Collections.emptyList();
        });

        // Register command
        commandManager.registerCommand(new SpawnCommand());
        commandManager.registerCommand(new NewbieCommand());
        commandManager.registerCommand(new HubCommand());
    }
}

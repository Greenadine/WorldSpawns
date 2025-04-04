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

import co.aikar.commands.annotation.*;
import dev.greenadine.plcommons.CommandInvoker;
import dev.greenadine.plcommons.PLCommonsCommand;
import dev.greenadine.worldspawns.DataManager;
import dev.greenadine.worldspawns.MessageKeys;
import dev.greenadine.worldspawns.Permissions;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@CommandAlias("newbie|new")
@Description("Manage the spawn for new players.")
@CommandPermission(Permissions.NEWBIE)
public class NewbieCommand extends PLCommonsCommand {

    private @Dependency DataManager dataManager;

    @HelpCommand
    public void onDefault(CommandInvoker invoker) {
        invoker.sendInfo(true, MessageKeys.NEWBIE_HELP, "commandLabel", getExecCommandLabel());
    }

    @Subcommand("tp")
    @Description("Teleport to the newbie spawn.")
    @Conditions("player-only")
    public void onTp(CommandInvoker invoker) {
        final Location newbieSpawn = dataManager.getNewbieSpawn();
        if (newbieSpawn == null) {
            invoker.sendInfo(MessageKeys.NEWBIE_NOT_SET);
            return;
        }

        final Player player = invoker.asPlayer();
        player.teleport(newbieSpawn);
        invoker.sendInfo(MessageKeys.TELEPORTED_TO_NEWBIE);
    }

    @Subcommand("set")
    @Description("Set the newbie spawn.")
    @Conditions("player-only")
    public void onSet(CommandInvoker invoker) {
        dataManager.setNewbieSpawn(invoker.asPlayer().getLocation());
        invoker.sendInfo(MessageKeys.NEWBIE_SET);
    }

    @Subcommand("clear")
    @Description("Clears the newbie spawn.")
    public void onClear(CommandInvoker invoker) {
        dataManager.setNewbieSpawn(null);
        invoker.sendInfo(MessageKeys.NEWBIE_CLEARED);
    }
}

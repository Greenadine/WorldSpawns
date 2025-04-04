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

import java.util.ArrayList;
import java.util.List;

// Utility class for command options context
public class TeleportOptions {

    private final List<String> options = new ArrayList<>();

    public TeleportOptions(List<String> options) {
        for (final String option : options)
            if (option.startsWith("-"))
                this.options.add(option.substring(1));
    }

    /**
     * Checks if the command has at least one of the specified options.
     *
     * @param options the options to check for.
     * @return {@code true} if the command has at least one of the specified options, {@code false} otherwise.
     */
    public boolean hasAny(String... options) {
        for (final String option : options)
            if (this.options.contains(option))
                return true;
        return false;
    }
}

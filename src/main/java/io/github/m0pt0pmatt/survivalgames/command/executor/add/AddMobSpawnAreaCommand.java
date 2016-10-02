/*
 * This file is part of SurvivalGames, licensed under the MIT License (MIT).
 *
 * Copyright (c) Matthew Broomfield <m0pt0pmatt17@gmail.com>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package io.github.m0pt0pmatt.survivalgames.command.executor.add;

import static io.github.m0pt0pmatt.survivalgames.Util.getOrThrow;
import static io.github.m0pt0pmatt.survivalgames.Util.sendSuccess;

import com.flowpowered.math.vector.Vector3d;
import io.github.m0pt0pmatt.survivalgames.command.CommandKeys;
import io.github.m0pt0pmatt.survivalgames.command.element.SurvivalGameCommandElement;
import io.github.m0pt0pmatt.survivalgames.command.executor.BaseCommand;
import io.github.m0pt0pmatt.survivalgames.command.executor.SurvivalGamesCommand;
import io.github.m0pt0pmatt.survivalgames.data.MobSpawnArea;
import io.github.m0pt0pmatt.survivalgames.game.SurvivalGame;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.EntityType;

import java.util.Collections;

import javax.annotation.Nonnull;

class AddMobSpawnAreaCommand extends BaseCommand {

    private static SurvivalGamesCommand INSTANCE = new AddMobSpawnAreaCommand();

    private AddMobSpawnAreaCommand() {
        super(
                AddCommand.getInstance(),
                "mob-spawn-area",
                GenericArguments.seq(
                        SurvivalGameCommandElement.getInstance(),
                        GenericArguments.vector3d(CommandKeys.VECTOR1),
                        GenericArguments.vector3d(CommandKeys.VECTOR2),
                        GenericArguments.catalogedElement(CommandKeys.ENTITY_TYPE, EntityType.class),
                        GenericArguments.integer(CommandKeys.SPAWN_RATE_PER_MINUTE)
                ), Collections.emptyMap());
    }

    @Override
    @Nonnull
    public CommandResult execute(@Nonnull CommandSource src, @Nonnull CommandContext args) throws CommandException {

        SurvivalGame survivalGame = (SurvivalGame) getOrThrow(args, CommandKeys.SURVIVAL_GAME);
        Vector3d vector1 = (Vector3d) getOrThrow(args, CommandKeys.VECTOR1);
        Vector3d vector2 = (Vector3d) getOrThrow(args, CommandKeys.VECTOR2);
        EntityType entityType = (EntityType) getOrThrow(args, CommandKeys.ENTITY_TYPE);
        Integer spawnRatePerMinute = (Integer) getOrThrow(args, CommandKeys.SPAWN_RATE_PER_MINUTE);

        MobSpawnArea mobSpawnArea = new MobSpawnArea();
        mobSpawnArea.addBoundaryVector(vector1);
        mobSpawnArea.addBoundaryVector(vector2);
        mobSpawnArea.setEntityType(entityType);
        mobSpawnArea.setSpawnRatePerMinute(spawnRatePerMinute);

        survivalGame.getConfig().getMobSpawnAreas().add(mobSpawnArea);

        sendSuccess(src, "Added mob-spawn-area");
        return CommandResult.success();
    }

    static SurvivalGamesCommand getInstance() {
        return INSTANCE;
    }
}

package io.ilikeorangutans.ancol.game.player;

import io.ilikeorangutans.ecs.Entity;

import java.util.List;

/**
 *
 */
public interface PlayerEntities {

	List<Entity> getActiveUnits();

	List<Entity> getUnitsWithActivity();

	List<Entity> getColonies();

}

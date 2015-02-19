package io.ilikeorangutans.ancol.map.tile;

import io.ilikeorangutans.ecs.Entity;

import java.util.List;

/**
 *
 */
public interface GameTile extends Tile {

	List<Entity> getEntities();

}

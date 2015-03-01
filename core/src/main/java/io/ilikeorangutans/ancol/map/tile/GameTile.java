package io.ilikeorangutans.ancol.map.tile;

import io.ilikeorangutans.ancol.Point;
import io.ilikeorangutans.ecs.Entity;

import java.util.List;

/**
 *
 */
public interface GameTile extends Tile {

	Point getPoint();

	List<Entity> getEntities();

}

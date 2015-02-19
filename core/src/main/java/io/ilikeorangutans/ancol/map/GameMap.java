package io.ilikeorangutans.ancol.map;

import io.ilikeorangutans.ancol.Point;
import io.ilikeorangutans.ancol.map.surrounding.SurroundingTile;
import io.ilikeorangutans.ecs.Entity;

import java.util.List;

/**
 * A map view that exposes entities on the map.
 */
public interface GameMap extends Map {

	SurroundingTile getTileAt(Point p);

	/**
	 * Returns the entities at the given location. Obviously this will only return entities that contain at least the
	 * {@link io.ilikeorangutans.ancol.map.PositionComponent}.
	 *
	 * @param p
	 * @return
	 */
	List<Entity> getEntitiesAt(Point p);

}

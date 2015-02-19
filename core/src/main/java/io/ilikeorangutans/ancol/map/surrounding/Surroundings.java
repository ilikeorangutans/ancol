package io.ilikeorangutans.ancol.map.surrounding;

import io.ilikeorangutans.ancol.Point;
import io.ilikeorangutans.ancol.map.tile.GameTile;
import io.ilikeorangutans.ecs.Entity;

import java.util.List;

/**
 *
 */
public interface Surroundings extends Iterable<GameTile> {

	/**
	 * Returns the tile at the given point.
	 *
	 * @param where
	 * @return
	 */
	GameTileImpl getTile(Selector where);

	/**
	 * Returns the entities at the given point.
	 *
	 * @param where
	 * @return
	 */
	List<Entity> getEntities(Selector where);

	List<GameTile> getAllWithoutCenter();

	public enum Selector {
		NW(-1, -1),
		N(0, -1),
		NE(1, -1),
		W(-1, 0),
		Center(0, 0),
		E(1, 0),
		SW(-1, 1),
		S(0, 1),
		SE(1, 1);

		private final int deltaX;
		private final int deltaY;

		Selector(int deltaX, int deltaY) {

			this.deltaX = deltaX;
			this.deltaY = deltaY;
		}

		public Point apply(Point p) {
			return new Point(p.x + deltaX, p.y + deltaY);
		}
	}
}

package io.ilikeorangutans.ancol.map.surrounding;

import io.ilikeorangutans.ancol.Point;
import io.ilikeorangutans.ancol.map.GameMap;
import io.ilikeorangutans.ancol.map.PositionComponent;
import io.ilikeorangutans.ancol.map.tile.GameTile;
import io.ilikeorangutans.ancol.map.tile.Tile;
import io.ilikeorangutans.ancol.map.tile.TileType;
import io.ilikeorangutans.ecs.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * The view of a map tile as seen by Surroundings. Allows access to the actual map tile, entities, colonies, etc.
 */
public class GameTileImpl implements GameTile {

	private final Point point;
	private final Tile tile;
	private final GameMap gameMap;

	public GameTileImpl(Point point, Tile tile, GameMap gameMap) {
		this.point = point;
		this.tile = tile;
		this.gameMap = gameMap;
	}

	/**
	 * Returns the map tile.
	 *
	 * @return the map tile
	 */
	public Tile getTile() {
		return tile;
	}

	/**
	 * Returns the coordinates of this tile.
	 *
	 * @return
	 */
	@Override
	public Point getPoint() {
		return point;
	}

	@Override
	public String toString() {
		return "GameTileImpl{" +
				"tile=" + tile +
				", point=" + point +
				'}';
	}

	/**
	 * Returns a list of all entities on this location.
	 *
	 * @return a list of entities.
	 */
	public List<Entity> getEntities() {

		List<Entity> ents = gameMap.getEntitiesAt(point);
		List<Entity> result = new ArrayList<Entity>();

		for (Entity ent : ents) {
			PositionComponent pc = ent.getComponent(PositionComponent.class);
			if (pc.getPoint().equals(point)) {
				result.add(ent);
			}
		}

		return result;
	}

	@Override
	public TileType getType() {
		return tile.getType();
	}
}

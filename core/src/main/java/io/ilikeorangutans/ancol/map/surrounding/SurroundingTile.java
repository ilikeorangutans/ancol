package io.ilikeorangutans.ancol.map.surrounding;

import io.ilikeorangutans.ancol.Point;
import io.ilikeorangutans.ancol.map.PositionComponent;
import io.ilikeorangutans.ancol.map.tile.Tile;
import io.ilikeorangutans.ecs.ComponentType;
import io.ilikeorangutans.ecs.Entities;
import io.ilikeorangutans.ecs.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * The view of a map tile as seen by Surroundings. Allows access to the actual map tile, entities, colonies, etc.
 * <p/>
 * TODO: This is a horrible name. Think of a better name for this class.
 */
public class SurroundingTile {

	private final Point point;
	private final Tile tile;
	private final Entities entities;

	public SurroundingTile(Point point, Tile tile, Entities entities) {
		this.point = point;
		this.tile = tile;
		this.entities = entities;
	}

	/**
	 * Returns the map tile.
	 *
	 * @return the map tile
	 */
	public Tile getTile() {
		return tile;
	}

	public Point getPoint() {
		return point;
	}

	@Override
	public String toString() {
		return "SurroundingTile{" +
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
		List<Entity> ents = entities.getEntityByType(ComponentType.fromClass(PositionComponent.class));
		List<Entity> result = new ArrayList<Entity>();

		for (Entity ent : ents) {
			PositionComponent pc = ent.getComponent(PositionComponent.class);
			if (pc.getPoint().equals(point)) {
				result.add(ent);
			}
		}

		return result;
	}

}

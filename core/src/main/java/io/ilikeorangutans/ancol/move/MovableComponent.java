package io.ilikeorangutans.ancol.move;

import io.ilikeorangutans.ancol.Point;
import io.ilikeorangutans.ancol.map.GameMap;
import io.ilikeorangutans.ancol.map.tile.Tile;
import io.ilikeorangutans.ancol.map.tile.TileType;
import io.ilikeorangutans.ancol.path.Movable;
import io.ilikeorangutans.ancol.path.Path;
import io.ilikeorangutans.ecs.Component;
import io.ilikeorangutans.ecs.ComponentType;

/**
 *
 */
public class MovableComponent implements Component, Movable {

	private static final ComponentType COMPONENT_TYPE = ComponentType.fromClass(MovableComponent.class);
	private final GameMap map;
	private final MovableType type;
	private Path path;

	public MovableComponent(GameMap map, MovableType type) {
		this.map = map;
		this.type = type;
	}

	public static ComponentType getComponentType() {
		return COMPONENT_TYPE;
	}

	public Point getDestination() {
		return path == null || path.isEmpty() ? null : path.getDestination();
	}

	@Override
	public ComponentType getType() {
		return COMPONENT_TYPE;
	}

	public boolean hasDestination() {
		return getDestination() != null;
	}

	public Path getPath() {
		return path;
	}

	public void setPath(Path path) {
		this.path = path;
	}

	@Override
	public boolean canAccess(Point p) {
		Tile tile = map.getTileAt(p);

		// TODO: we'll need to use GameMap instead of Map so we can access what entities are on this tile. Entities can
		// influence pathfinding and accessibility, for example a ship (with empty cargo holds) will allow a land unit
		// to "enter" a sea tile, and vice versa, a colony will allow a ship to enter a land tile.
		TileType.Role role = tile.getType().getRole();
		if (type == MovableType.Land) {
			// TODO: might want to consider unexplored accessible.
			return role == TileType.Role.Land || role == TileType.Role.Unexplored;
		} else {
			return role == TileType.Role.Ocean || role == TileType.Role.Lake || role == TileType.Role.TradeRoute || role == TileType.Role.Unexplored;
		}

		// TODO: need to check if the target tile has a ship that we can board or a colony we can enter.
	}

	@Override
	public float getCost(Point p) {
		return 1;
	}

	public enum MovableType {
		Land,
		Sea
	}
}

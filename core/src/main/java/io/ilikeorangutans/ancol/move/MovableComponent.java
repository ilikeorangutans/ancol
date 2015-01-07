package io.ilikeorangutans.ancol.move;

import io.ilikeorangutans.ancol.Point;
import io.ilikeorangutans.ancol.map.Map;
import io.ilikeorangutans.ancol.map.tile.Tile;
import io.ilikeorangutans.ancol.path.Movable;
import io.ilikeorangutans.ancol.path.Path;
import io.ilikeorangutans.ecs.Component;
import io.ilikeorangutans.ecs.ComponentType;

/**
 *
 */
public class MovableComponent implements Component, Movable {

	private static final ComponentType COMPONENT_TYPE = ComponentType.fromClass(MovableComponent.class);
	private final Map map;
	private final MovableType type;
	private Path path;

	public MovableComponent(Map map, MovableType type) {
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

		int id = tile.getType().getId();
		if (type == MovableType.Land) {
			return id != 1;
		} else {
			return id == 0 || id == 1;
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

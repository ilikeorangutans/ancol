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
	private Path path;

	public MovableComponent(Map map) {
		this.map = map;
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
		return tile.getType().getId() != 1;
	}

	@Override
	public float getCost(Point p) {
		return 1;
	}
}

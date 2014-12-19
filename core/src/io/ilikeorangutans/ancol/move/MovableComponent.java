package io.ilikeorangutans.ancol.move;

import io.ilikeorangutans.ancol.Point;
import io.ilikeorangutans.ancol.path.Path;
import io.ilikeorangutans.ecs.Component;
import io.ilikeorangutans.ecs.ComponentType;

/**
 *
 */
public class MovableComponent implements Component {

	private static final ComponentType COMPONENT_TYPE = ComponentType.fromClasses(MovableComponent.class)[0];
	private Path path;

	public static ComponentType getComponentType() {
		return COMPONENT_TYPE;
	}

	public Point getDestination() {
		return path == null ? null : path.getDestination();
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
}

package io.ilikeorangutans.ancol.map;

import io.ilikeorangutans.ecs.Component;
import io.ilikeorangutans.ecs.ComponentType;

/**
 *
 */
public class PositionComponent implements Component {

	private static final ComponentType COMPONENT_TYPE = ComponentType.fromClasses(PositionComponent.class)[0];

	private int x, y;

	public PositionComponent(int x, int y) {
		set(x, y);
	}

	@Override
	public ComponentType getType() {
		return COMPONENT_TYPE;
	}

	public void set(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}

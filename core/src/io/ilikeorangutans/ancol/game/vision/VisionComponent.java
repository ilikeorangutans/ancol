package io.ilikeorangutans.ancol.game.vision;

import io.ilikeorangutans.ecs.Component;
import io.ilikeorangutans.ecs.ComponentType;

/**
 *
 */
public class VisionComponent implements Component {

	private static final ComponentType COMPONENT_TYPE = ComponentType.fromClasses(VisionComponent.class)[0];


	private final int radius;

	public VisionComponent(int radius) {
		this.radius = radius;
	}

	@Override
	public ComponentType getType() {
		return COMPONENT_TYPE;
	}

	public int getRadius() {
		return radius;
	}
}

package io.ilikeorangutans.ancol.game.vision;

import io.ilikeorangutans.ancol.map.surrounding.Surroundings;
import io.ilikeorangutans.ecs.Component;
import io.ilikeorangutans.ecs.ComponentType;

/**
 *
 */
public class VisionComponent implements Component {

	private static final ComponentType COMPONENT_TYPE = ComponentType.fromClass(VisionComponent.class);

	private final int radius;
	private Surroundings surroundings;

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

	/**
	 * Returns the surroundings as seen by this component.
	 *
	 * @return
	 */
	public Surroundings getSurroundings() {
		return surroundings;
	}

	public void setSurroundings(Surroundings surroundings) {
		this.surroundings = surroundings;
	}
}

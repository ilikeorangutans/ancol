package io.ilikeorangutans.ancol.game.cargo;

import io.ilikeorangutans.ecs.Component;
import io.ilikeorangutans.ecs.ComponentType;

/**
 * Indicates that the containing entity can be transported.
 */
public class TransportableComponent implements Component {

	public static final ComponentType COMPONENT_TYPE = ComponentType.fromClass(TransportableComponent.class);
	private final int numHolds;

	public TransportableComponent(int numHolds) {
		this.numHolds = numHolds;
	}

	public int getNumHolds() {
		return numHolds;
	}

	@Override
	public ComponentType getType() {
		return COMPONENT_TYPE;
	}
}

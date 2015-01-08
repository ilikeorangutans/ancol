package io.ilikeorangutans.ancol.game.cargo;

import io.ilikeorangutans.ecs.Component;
import io.ilikeorangutans.ecs.ComponentType;

/**
 *
 */
public class ShipComponent implements Component {
	public static final ComponentType COMPONENT_TYPE = ComponentType.fromClass(ShipComponent.class);

	@Override
	public ComponentType getType() {
		return COMPONENT_TYPE;
	}

	public String getName() {
		return "I'm a ship!";
	}

}

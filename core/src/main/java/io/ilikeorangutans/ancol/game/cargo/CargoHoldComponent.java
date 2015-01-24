package io.ilikeorangutans.ancol.game.cargo;

import io.ilikeorangutans.ecs.Component;
import io.ilikeorangutans.ecs.ComponentType;

/**
 * Component to make Entities capable of carrying cargo.
 */
public class CargoHoldComponent implements Component {

	public static final ComponentType COMPONENT_TYPE = ComponentType.fromClass(CargoHoldComponent.class);

	private final CargoHold cargohold;

	public CargoHoldComponent(int holds) {
		this.cargohold = new CargoHold(holds);
	}

	@Override
	public ComponentType getType() {
		return COMPONENT_TYPE;
	}

	public CargoHold getCargohold() {
		return cargohold;
	}
}

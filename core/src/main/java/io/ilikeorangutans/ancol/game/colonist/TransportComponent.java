package io.ilikeorangutans.ancol.game.colonist;

import io.ilikeorangutans.ecs.Component;
import io.ilikeorangutans.ecs.ComponentType;

/**
 *
 */
public class TransportComponent implements Component {
	private String cargo; // Cargo loaded
	private int holds; // cargo holds.

	@Override
	public ComponentType getType() {
		return null;
	}
}

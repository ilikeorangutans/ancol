package io.ilikeorangutans.ancol.game.colony;

import io.ilikeorangutans.ancol.game.player.Player;
import io.ilikeorangutans.ecs.Component;
import io.ilikeorangutans.ecs.ComponentType;

/**
 * Indicates that a given position is worked by a player.
 */
public class FieldsWorkedComponent implements Component {

	public static final ComponentType COMPONENT_TYPE = ComponentType.fromClass(FieldsWorkedComponent.class);

	private final Player owner;

	public FieldsWorkedComponent(Player owner) {
		this.owner = owner;
	}

	public Player getOwner() {
		return owner;
	}

	@Override
	public ComponentType getType() {
		return COMPONENT_TYPE;
	}
}

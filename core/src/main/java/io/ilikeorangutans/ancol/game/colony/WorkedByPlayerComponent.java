package io.ilikeorangutans.ancol.game.colony;

import io.ilikeorangutans.ancol.game.player.Player;
import io.ilikeorangutans.ancol.game.player.PlayerOwned;
import io.ilikeorangutans.ecs.Component;
import io.ilikeorangutans.ecs.ComponentType;

/**
 * Indicates that a given position is worked by a player.
 */
public class WorkedByPlayerComponent implements Component, PlayerOwned {

	public static final ComponentType COMPONENT_TYPE = ComponentType.fromClass(WorkedByPlayerComponent.class);

	private final Player owner;

	public WorkedByPlayerComponent(Player owner) {
		this.owner = owner;
	}


	@Override
	public ComponentType getType() {
		return COMPONENT_TYPE;
	}

	@Override
	public Player getPlayer() {
		return owner;
	}
}

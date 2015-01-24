package io.ilikeorangutans.ancol.game.event;

import io.ilikeorangutans.ancol.game.player.Player;
import io.ilikeorangutans.bus.Event;

/**
 * Fired when all the player's entities have been simulated.
 */
public class AllEntitiesSimulatedEvent implements Event {
	public final Player player;

	public AllEntitiesSimulatedEvent(Player player) {
		this.player = player;
	}
}

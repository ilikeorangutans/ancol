package io.ilikeorangutans.ancol.game.player;

import io.ilikeorangutans.bus.Event;

/**
 *
 */
public class PlayerJoinedEvent implements Event {

	public final Player player;

	public PlayerJoinedEvent(Player player) {
		this.player = player;
	}

	@Override
	public String toString() {
		return "PlayerJoinedEvent{" +
				"player=" + player +
				'}';
	}
}

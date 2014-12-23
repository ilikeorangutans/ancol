package io.ilikeorangutans.ancol.game.turn;

import io.ilikeorangutans.ancol.game.Player;
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

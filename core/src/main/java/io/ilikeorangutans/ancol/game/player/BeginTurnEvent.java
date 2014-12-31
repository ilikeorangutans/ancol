package io.ilikeorangutans.ancol.game.player;

import io.ilikeorangutans.bus.Event;

/**
 * Fired off whenever it's a new player's turn.
 */
public class BeginTurnEvent implements Event {

    public final Player player;

    public BeginTurnEvent(Player player) {
        this.player = player;
    }

}

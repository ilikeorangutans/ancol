package io.ilikeorangutans.ancol.game.turn;

import io.ilikeorangutans.ancol.game.Player;
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

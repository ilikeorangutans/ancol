package io.ilikeorangutans.ancol.game.event;

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
}

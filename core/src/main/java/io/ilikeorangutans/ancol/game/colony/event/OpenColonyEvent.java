package io.ilikeorangutans.ancol.game.colony.event;

import io.ilikeorangutans.bus.Event;
import io.ilikeorangutans.ecs.Entity;

/**
 *
 */
public class OpenColonyEvent implements Event {

	public final Entity colony;

	public OpenColonyEvent(Entity colony) {
		this.colony = colony;
	}
}

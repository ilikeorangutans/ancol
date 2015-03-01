package io.ilikeorangutans.ancol.game.colony.population;

import io.ilikeorangutans.bus.Event;
import io.ilikeorangutans.ecs.Entity;

/**
 *
 */
public class ColonistJoinedEvent implements Event {

	public final Entity colonist;

	public ColonistJoinedEvent(Entity colonist) {
		this.colonist = colonist;
	}
}

package io.ilikeorangutans.ancol.game.colony.event;

import io.ilikeorangutans.bus.Event;
import io.ilikeorangutans.ecs.Entity;

/**
 *
 */
public class JobAssignedEvent implements Event {

	public final Entity colonist;

	public JobAssignedEvent(Entity colonist) {
		this.colonist = colonist;
	}
}

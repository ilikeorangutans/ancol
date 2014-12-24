package io.ilikeorangutans.ancol.game.actionpoint;

import io.ilikeorangutans.bus.Event;
import io.ilikeorangutans.ecs.Entity;

/**
 *
 */
public class ActionPointsConsumedEvent implements Event {

	public final Entity entity;

	public ActionPointsConsumedEvent(Entity entity) {
		this.entity = entity;
	}
}

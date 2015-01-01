package io.ilikeorangutans.ancol.select.event;

import io.ilikeorangutans.bus.Event;
import io.ilikeorangutans.ecs.Entity;

/**
 * Fired off when we would like to select a specific entity.
 */
public class SelectEntityEvent implements Event {

	public final Entity entity;

	public SelectEntityEvent(Entity entity) {
		this.entity = entity;
	}
}

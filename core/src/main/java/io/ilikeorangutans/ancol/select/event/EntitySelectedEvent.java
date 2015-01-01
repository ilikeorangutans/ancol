package io.ilikeorangutans.ancol.select.event;

import io.ilikeorangutans.bus.Event;
import io.ilikeorangutans.ecs.Entity;

/**
 *
 */
public class EntitySelectedEvent implements Event {

	public final Entity entity;

	public EntitySelectedEvent(Entity entity) {
		this.entity = entity;
	}

}

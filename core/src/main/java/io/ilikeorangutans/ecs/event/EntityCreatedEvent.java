package io.ilikeorangutans.ecs.event;

import io.ilikeorangutans.bus.Event;
import io.ilikeorangutans.ecs.Entity;

/**
 *
 */
public class EntityCreatedEvent implements Event {

	public final Entity entity;

	public EntityCreatedEvent(Entity entity) {
		this.entity = entity;
	}
}

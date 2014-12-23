package io.ilikeorangutans.ecs.event;

import io.ilikeorangutans.bus.Event;
import io.ilikeorangutans.ecs.Entity;

/**
 * Fired whenever an entity is updated or modified.
 */
public class EntityUpdatedEvent implements Event {

	public final Entity entity;

	public EntityUpdatedEvent(Entity entity) {
		this.entity = entity;
	}
}

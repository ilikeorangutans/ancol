package io.ilikeorangutans.ancol.move;

import io.ilikeorangutans.ancol.Point;
import io.ilikeorangutans.bus.Event;
import io.ilikeorangutans.ecs.Entity;

/**
 * Fired when an Entity moved.
 */
public class MovedEvent implements Event {

	public final Entity entity;
	public final Point position;

	public MovedEvent(Entity entity, Point position) {
		this.entity = entity;
		this.position = position;
	}
}

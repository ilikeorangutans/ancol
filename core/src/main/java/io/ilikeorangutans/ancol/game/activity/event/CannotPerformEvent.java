package io.ilikeorangutans.ancol.game.activity.event;

import io.ilikeorangutans.ancol.game.activity.Activity;
import io.ilikeorangutans.bus.Event;
import io.ilikeorangutans.ecs.Entity;

/**
 *
 */
public class CannotPerformEvent implements Event {
	public final Entity entity;
	public final Activity activity;

	public CannotPerformEvent(Entity entity, Activity activity) {
		this.entity = entity;
		this.activity = activity;
	}
}

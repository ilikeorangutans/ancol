package io.ilikeorangutans.ancol.game.activity;

import io.ilikeorangutans.bus.Event;
import io.ilikeorangutans.ecs.Entity;

/**
 * Fired whenever an activity has been completed.
 */
public class ActivityCompleteEvent implements Event {

	public final Entity entity;

	public final Activity activity;

	public ActivityCompleteEvent(Entity entity, Activity activity) {
		this.entity = entity;
		this.activity = activity;
	}
}

package io.ilikeorangutans.ancol.game.colony;

import io.ilikeorangutans.bus.Event;
import io.ilikeorangutans.ecs.Entity;

/**
 *
 */
public class BuildColonyEvent implements Event {
	public final Entity builder;

	public BuildColonyEvent(Entity builder) {
		this.builder = builder;
	}


}

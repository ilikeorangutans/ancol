package io.ilikeorangutans.ancol.game.colony.population;

import io.ilikeorangutans.bus.Event;
import io.ilikeorangutans.ecs.Entity;

/**
 *
 */
public class ColonistLeftEvent implements Event {

	public final Entity colonist;

	public ColonistLeftEvent(Entity colonist) {
		this.colonist = colonist;
	}
}

package io.ilikeorangutans.ancol.select;

import io.ilikeorangutans.bus.Event;
import io.ilikeorangutans.ecs.Entity;

import java.util.List;

/**
 *
 */
public class MultipleSelectOptionsEvent implements Event {
	public final List<Entity> selectable;

	public MultipleSelectOptionsEvent(List<Entity> selectable) {
		this.selectable = selectable;
	}
}

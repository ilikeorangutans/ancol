package io.ilikeorangutans.ancol.map.viewport;

import io.ilikeorangutans.ancol.Point;
import io.ilikeorangutans.ancol.map.PositionComponent;
import io.ilikeorangutans.bus.Event;
import io.ilikeorangutans.ecs.Entity;

/**
 *
 */
public class CenterViewEvent implements Event {

	public final Point point;

	public CenterViewEvent(Point point) {
		this.point = point;
	}

	public CenterViewEvent(Entity entity) {
		point = entity.getComponent(PositionComponent.class).getPoint();
	}
}

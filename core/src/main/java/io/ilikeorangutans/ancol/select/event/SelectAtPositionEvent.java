package io.ilikeorangutans.ancol.select.event;

import io.ilikeorangutans.ancol.Point;
import io.ilikeorangutans.bus.Event;

/**
 * Fired off when a select is requested for a given coordinate. This might or might not result in a selected entity.
 */
public class SelectAtPositionEvent implements Event {

	public final Point point;

	public SelectAtPositionEvent(Point point) {
		this.point = point;
	}

	public SelectAtPositionEvent(int x, int y) {
		this(new Point(x, y));
	}

	@Override
	public String toString() {
		return "SelectAtPositionEvent{" +
				"point=" + point +
				'}';
	}
}

package io.ilikeorangutans.ancol.map.viewport;

import io.ilikeorangutans.bus.Event;

/**
 *
 */
public class ScrollEvent implements Event {

	public final int deltaX, deltaY;

	public ScrollEvent(int deltaX, int deltaY) {
		this.deltaX = deltaX;
		this.deltaY = deltaY;
	}

}

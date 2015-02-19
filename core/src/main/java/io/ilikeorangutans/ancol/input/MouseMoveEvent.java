package io.ilikeorangutans.ancol.input;

import io.ilikeorangutans.bus.Event;

/**
 *
 */
public class MouseMoveEvent implements Event {
	public final int screenX;
	public final int screenY;

	public MouseMoveEvent(int screenX, int screenY) {

		this.screenX = screenX;
		this.screenY = screenY;
	}

	@Override
	public String toString() {
		return "MouseMoveEvent{" +
				"screenX=" + screenX +
				", screenY=" + screenY +
				'}';
	}
}

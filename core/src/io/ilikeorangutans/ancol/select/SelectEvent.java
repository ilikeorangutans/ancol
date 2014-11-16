package io.ilikeorangutans.ancol.select;

import io.ilikeorangutans.bus.Event;

/**
 *
 */
public class SelectEvent implements Event {

    final int x, y;

    public SelectEvent(int x, int y) {
        this.x = x;
        this.y = y;
    }

}

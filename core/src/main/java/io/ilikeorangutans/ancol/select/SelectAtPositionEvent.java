package io.ilikeorangutans.ancol.select;

import io.ilikeorangutans.bus.Event;

/**
 * Fired off when a select is requested for a given coordinate. This might or might not result in a selected entity.
 */
public class SelectAtPositionEvent implements Event {

    /**
     * Coordinates of the select in map relative coordinates.
     */
    final int x, y;

    public SelectAtPositionEvent(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "SelectEvent{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}

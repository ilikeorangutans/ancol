package io.ilikeorangutans.ancol.move;

import io.ilikeorangutans.ancol.Point;
import io.ilikeorangutans.bus.Event;

/**
 *
 */
public class MoveEvent implements Event {

    public final Point destination;

    public MoveEvent(Point destination) {
        this.destination = destination;
    }
}

package io.ilikeorangutans.ancol.game.event;

import io.ilikeorangutans.bus.Event;
import io.ilikeorangutans.ecs.Entity;

/**
 *
 */
public class SimulateEntityEvent implements Event {

    public final Entity entity;

    public SimulateEntityEvent(Entity entity) {
        this.entity = entity;
    }
}

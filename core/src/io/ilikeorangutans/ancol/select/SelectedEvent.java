package io.ilikeorangutans.ancol.select;

import io.ilikeorangutans.bus.Event;
import io.ilikeorangutans.ecs.Entity;

/**
 *
 */
public class SelectedEvent implements Event {

    public final Entity entity;

    public SelectedEvent(Entity entity) {
        this.entity = entity;
    }

}

package io.ilikeorangutans.ancol.select;

import io.ilikeorangutans.bus.Emitter;
import io.ilikeorangutans.bus.Subscribe;
import io.ilikeorangutans.ecs.Entities;
import io.ilikeorangutans.ecs.System;

/**
 *
 */
public class SelectionSystem implements System {

    private final Entities entities;

    private final Emitter emitter;

    public SelectionSystem(Entities entities, Emitter emitter) {
        this.entities = entities;
        this.emitter = emitter;
    }

    @Override
    public void step(float deltaTime) {

    }

    @Subscribe
    public void onSelectEvent(SelectEvent selectEvent) {


        emitter.fire(new SelectedEvent(null));
    }

}

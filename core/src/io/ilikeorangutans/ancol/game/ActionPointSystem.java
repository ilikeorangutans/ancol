package io.ilikeorangutans.ancol.game;

import io.ilikeorangutans.ancol.game.event.SimulateEntityEvent;
import io.ilikeorangutans.bus.Emitter;
import io.ilikeorangutans.bus.Subscribe;
import io.ilikeorangutans.ecs.Engine;

/**
 *
 */
public class ActionPointSystem {

    private final Engine engine;

    private final Emitter bus;

    private Player player;

    public ActionPointSystem(Emitter bus, Engine engine) {
        this.engine = engine;
        this.bus = bus;
    }

    @Subscribe
    public void onSimulateEntity(SimulateEntityEvent e) {

        System.out.println("ActionPointSystem.onSimulateEntity " + e.entity);

        engine.step(0f);
    }

}

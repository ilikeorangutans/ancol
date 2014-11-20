package io.ilikeorangutans.ecs;

import io.ilikeorangutans.bus.EventBus;

/**
 *
 */
public class Facade {

    private final EventBus bus;

    private Engine engine;

    private Entities entities;

    public Facade(EventBus bus) {
        this.bus = bus;
    }

    public void init() {
        engine = new Engine();
        entities = new Entities();
    }

    public void shutdown() {

    }

    public Entities getEntities() {
        return entities;
    }

    public void step() {
        engine.step(0.0f);
    }

    public void addSystem(System s) {
        engine.add(s);
    }
}

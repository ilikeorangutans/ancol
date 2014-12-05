package io.ilikeorangutans.ecs;

import io.ilikeorangutans.bus.EventBus;

/**
 *
 */
public class Facade {

	private final EventBus bus;

	private Engine engine;

	private SimpleEntities entities;

	public Facade(EventBus bus) {
		this.bus = bus;
	}

	public void init() {
		engine = new Engine();
		entities = new SimpleEntities();
	}

	public SimpleEntities getEntities() {
		return entities;
	}

}

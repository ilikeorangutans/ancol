package io.ilikeorangutans.ancol.game.colony.population;

import io.ilikeorangutans.ancol.game.cmd.ControllableComponent;
import io.ilikeorangutans.ancol.game.colony.ColonistsImpl;
import io.ilikeorangutans.ancol.game.production.worker.Worker;
import io.ilikeorangutans.ancol.game.ware.Ware;
import io.ilikeorangutans.ancol.graphics.RenderableComponent;
import io.ilikeorangutans.bus.Emitter;
import io.ilikeorangutans.ecs.Entity;

/**
 *
 */
public class Population extends ColonistsImpl implements Worker {


	private final Emitter emitter;

	public Population(Emitter emitter) {
		this.emitter = emitter;
	}

	/**
	 * Helper method that will remove a given entity from the map.
	 *
	 * @param colonist
	 */
	private void removeColonistFromMap(Entity colonist) {
		colonist.getComponent(RenderableComponent.class).setVisible(false);
		ControllableComponent controllableComponent = colonist.getComponent(ControllableComponent.class);
		controllableComponent.clearCommands();
		controllableComponent.setActive(false);
	}

	/**
	 * Adds the given colonist to the map and ensures he's controllable.
	 *
	 * @param colonist
	 */
	private void addColonistToMap(Entity colonist) {
		colonist.getComponent(RenderableComponent.class).setVisible(true);
		ControllableComponent controllableComponent = colonist.getComponent(ControllableComponent.class);
		controllableComponent.clearCommands();
		controllableComponent.setActive(true);
	}

	@Override
	public int calculateOutput(Ware ware) {
		System.out.println("Population.calculateOutput TODO: calculate how much food we consume");
		// TODO We need to get how much food is available if not enough is there, we'll have to starve a colonist.
		return size() * 2;
	}

	@Override
	public void add(Entity colonist) {
		super.add(colonist);
		removeColonistFromMap(colonist);
		emitter.fire(new ColonistJoinedEvent(colonist));
	}

	@Override
	public void remove(Entity colonist) {
		super.remove(colonist);
		emitter.fire(new ColonistLeftEvent(colonist));
	}
}

package io.ilikeorangutans.ancol.game.colony.population;

import io.ilikeorangutans.ancol.game.cmd.ControllableComponent;
import io.ilikeorangutans.ancol.game.production.worker.Worker;
import io.ilikeorangutans.ancol.game.ware.Ware;
import io.ilikeorangutans.ancol.graphics.RenderableComponent;
import io.ilikeorangutans.bus.Emitter;
import io.ilikeorangutans.ecs.Entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 */
public class Population implements Iterable<Entity>, Worker {

	private final List<Entity> colonists = new ArrayList<Entity>();
	private final Emitter emitter;

	public Population(Emitter emitter) {
		this.emitter = emitter;
	}

	public void join(Entity colonist) {
		removeColonistFromMap(colonist);
		colonists.add(colonist);
		emitter.fire(new ColonistJoinedEvent(colonist));
	}

	public void leave(Entity colonist) {
		addColonistToMap(colonist);
		colonists.remove(colonist);
		emitter.fire(new ColonistLeftEvent(colonist));
	}

	public int size() {
		return colonists.size();
	}


	@Override
	public Iterator<Entity> iterator() {
		return colonists.iterator();
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
}

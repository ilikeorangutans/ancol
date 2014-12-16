package io.ilikeorangutans.ancol.game.activity;

import io.ilikeorangutans.ancol.game.Player;
import io.ilikeorangutans.ancol.game.PlayerOwnedComponent;
import io.ilikeorangutans.ancol.game.cmd.ControllableComponent;
import io.ilikeorangutans.ancol.game.cmd.event.CommandQueuedEvent;
import io.ilikeorangutans.ancol.game.event.SimulateEntityEvent;
import io.ilikeorangutans.ancol.game.turn.BeginTurnEvent;
import io.ilikeorangutans.bus.Emitter;
import io.ilikeorangutans.bus.Subscribe;
import io.ilikeorangutans.ecs.ComponentType;
import io.ilikeorangutans.ecs.Entities;
import io.ilikeorangutans.ecs.Entity;

import java.util.List;

/**
 *
 */
public class ActivitySystem {

	private final Emitter emitter;
	private final Entities entities;

	public ActivitySystem(Emitter emitter, Entities entities) {
		this.emitter = emitter;
		this.entities = entities;
	}

	@Subscribe
	public void onCommandQueuedEvent(CommandQueuedEvent e) {
		final Entity entity = e.entity;
		if (!entity.hasComponent(ComponentType.fromClass(ControllableComponent.class))) {
			throw new IllegalArgumentException("Cannot simulate entity " + entity + ", it doesn't have a controllable component.");
		}

		ActivityComponent activityComponent = entity.getComponent(ActivityComponent.class);
		if (activityComponent.hasActivity())
			return;

		// TODO: Might wanna do this via an event...?
		ControllableComponent controllableComponent = entity.getComponent(ControllableComponent.class);
		controllableComponent.getNextCommand();

		e.command.apply(emitter, entity);
		emitter.fire(new SimulateEntityEvent(entity));
		entity.updated();
	}

	@Subscribe
	public void onSimulateEntity(SimulateEntityEvent e) {
		final Entity entity = e.entity;

		ActivityComponent activityComponent = entity.getComponent(ActivityComponent.class);

		while (activityComponent.hasActivity() && activityComponent.canPerform()) {
			activityComponent.step(emitter);
		}

	}

	/**
	 * Runs at the beginning of each turn and resets the action points for the current player.
	 *
	 * @param bte
	 */
	@Subscribe
	public void onBeginTurn(BeginTurnEvent bte) {
		replenishActionPoints(bte.player);
	}

	/**
	 * Finds all entities controllable by the given player and replenishes their action points.
	 *
	 * @param player
	 */
	private void replenishActionPoints(Player player) {
		List<Entity> entityByType = entities.getEntityByType(ComponentType.fromClass(PlayerOwnedComponent.class, ActivityComponent.class));

		for (Entity entity : entityByType) {
			PlayerOwnedComponent playerOwned = entity.getComponent(PlayerOwnedComponent.class);

			if (!playerOwned.getPlayer().equals(player))
				continue;

			ActivityComponent activityComponent = entity.getComponent(ActivityComponent.class);
			activityComponent.replenish();
		}
	}

}
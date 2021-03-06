package io.ilikeorangutans.ancol.game.activity;

import io.ilikeorangutans.ancol.game.actionpoint.ActionPointsConsumedEvent;
import io.ilikeorangutans.ancol.game.activity.event.ActivityCompleteEvent;
import io.ilikeorangutans.ancol.game.cmd.Command;
import io.ilikeorangutans.ancol.game.cmd.ControllableComponent;
import io.ilikeorangutans.ancol.game.cmd.event.CommandQueuedEvent;
import io.ilikeorangutans.ancol.game.event.SimulateEntityEvent;
import io.ilikeorangutans.ancol.game.player.Player;
import io.ilikeorangutans.ancol.game.player.PlayerOwnedComponent;
import io.ilikeorangutans.ancol.game.player.event.BeginTurnEvent;
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
	public void onCommandQueued(CommandQueuedEvent e) {
		final Entity entity = e.entity;
		if (!entity.hasComponent(ComponentType.fromClasses(ControllableComponent.class))) {
			throw new IllegalArgumentException("Cannot simulate entity " + entity + ", it doesn't have a controllable component.");
		}

		scheduleActivity(entity, getNextCommand(entity));
	}

	private void scheduleActivity(Entity entity, Command command) {
		ActivityComponent activityComponent = entity.getComponent(ActivityComponent.class);
		if (activityComponent.hasActivity()) {
			return;
		}

		command.apply(emitter, entity);
		emitter.fire(new SimulateEntityEvent(entity));
		entity.updated();
	}

	private Command getNextCommand(Entity entity) {
		ControllableComponent controllableComponent = entity.getComponent(ControllableComponent.class);
		return controllableComponent.getNextCommand();
	}

	@Subscribe
	public void onActivityComplete(ActivityCompleteEvent ace) {
		scheduleActivity(ace.entity, getNextCommand(ace.entity));
	}

	@Subscribe
	public void onSimulateEntity(SimulateEntityEvent e) {
		simulateEntity(e.entity);
	}

	private void simulateEntity(Entity entity) {
		ActivityComponent activityComponent = entity.getComponent(ActivityComponent.class);

		while (activityComponent.hasActivity() && activityComponent.canPerform()) {
			activityComponent.step(emitter);
			entity.updated();

			if (activityComponent.getActivity().isComplete()) {
				activityComponent.setActivity(null);
				emitter.fire(new ActivityCompleteEvent(entity, activityComponent.getActivity()));
			}
		}

		emitter.fire(new ActionPointsConsumedEvent(entity));
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
		List<Entity> entityByType = entities.getEntityByType(ComponentType.fromClasses(PlayerOwnedComponent.class, ActivityComponent.class));

		for (Entity entity : entityByType) {
			PlayerOwnedComponent playerOwned = entity.getComponent(PlayerOwnedComponent.class);

			if (!playerOwned.getPlayer().equals(player))
				continue;

			ActivityComponent activityComponent = entity.getComponent(ActivityComponent.class);
			activityComponent.replenish();
			entity.updated();
		}
	}

}

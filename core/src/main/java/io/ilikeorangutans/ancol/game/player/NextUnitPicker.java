package io.ilikeorangutans.ancol.game.player;

import io.ilikeorangutans.ancol.game.actionpoint.ActionPointsConsumedEvent;
import io.ilikeorangutans.ancol.game.activity.ActivityComponent;
import io.ilikeorangutans.ancol.game.event.AllEntitiesSimulatedEvent;
import io.ilikeorangutans.ancol.game.event.SimulateEntityEvent;
import io.ilikeorangutans.ancol.game.event.SimulateQueuedEntitiesEvent;
import io.ilikeorangutans.ancol.game.player.event.BeginTurnEvent;
import io.ilikeorangutans.ancol.game.player.event.PickNextEntityEvent;
import io.ilikeorangutans.ancol.game.player.event.TurnConcludedEvent;
import io.ilikeorangutans.ancol.map.viewport.CenterViewEvent;
import io.ilikeorangutans.ancol.select.event.SelectEntityEvent;
import io.ilikeorangutans.bus.Emitter;
import io.ilikeorangutans.bus.Subscribe;
import io.ilikeorangutans.ecs.Entity;

import java.util.List;

/**
 * Will pick the next unit for the player to select, and, if applicable, simulate entities with queued/pending
 * activities before the end of the turn. Instances of this are bound to a specific player.
 */
public class NextUnitPicker {

	private final Player player;

	private final Emitter emitter;

	private final PlayerEntities entities;


	private boolean enabled = false;

	public NextUnitPicker(Emitter emitter, Player player, PlayerEntities entities) {
		this.player = player;
		this.emitter = emitter;
		this.entities = entities;
	}

	private boolean hasActionPoints(Entity entity) {
		return entity.getComponent(ActivityComponent.class).canPerform();
	}

	@Subscribe
	public void onActionPointsConsumed(ActionPointsConsumedEvent event) {
		if (!hasActionPoints(event.entity)) {
			selectNextEntity();
		}
	}

	@Subscribe
	public void onSimulateQueuedEntities(SimulateQueuedEntitiesEvent event) {
		simulateQueuedEntities();
	}

	@Subscribe
	public void onTurnConcluded(TurnConcludedEvent event) {
		setEnabled(false);
	}


	@Subscribe
	public void onPickNextEntity(PickNextEntityEvent event) {
		selectNextEntity();
	}

	@Subscribe
	public void onBeginTurn(BeginTurnEvent event) {
		if (event.player.equals(player)) {
			setEnabled(true);
			selectNextEntity();
		}
	}

	/**
	 * Finds the next entity that has action points and no activity and selects it for the player.
	 */
	private void selectNextEntity() {
		if (!isEnabled()) {
			return;
		}

		List<Entity> allUnits = entities.getActiveUnits();

		if (allUnits.size() > 0) {
			Entity entity = allUnits.get(0);
			emitter.fire(new SelectEntityEvent(entity));
			emitter.fire(new CenterViewEvent(entity));
			return;
		}

		setEnabled(false);

		simulateQueuedEntities();
	}

	public void simulateQueuedEntities() {
		List<Entity> unitsWithActivity = entities.getUnitsWithActivity();
		for (Entity entity : unitsWithActivity) {
			emitter.fire(new SimulateEntityEvent(entity));

			ActivityComponent ac = entity.getComponent(ActivityComponent.class);
			if (ac.canPerform()) {
				setEnabled(true);
				selectNextEntity();
				return;
			}
		}

		emitter.fire(new AllEntitiesSimulatedEvent(player));
	}

	/**
	 * Flag whether we are allowed to act or not; enabled when the current player switches to the player this instance
	 * was initialized with.
	 */
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}

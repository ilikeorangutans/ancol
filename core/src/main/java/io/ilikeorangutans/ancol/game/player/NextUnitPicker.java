package io.ilikeorangutans.ancol.game.player;

import io.ilikeorangutans.ancol.game.actionpoint.ActionPointsConsumedEvent;
import io.ilikeorangutans.ancol.game.activity.ActivityComponent;
import io.ilikeorangutans.ancol.game.activity.event.ActivityCompleteEvent;
import io.ilikeorangutans.ancol.game.cmd.ControllableComponent;
import io.ilikeorangutans.ancol.game.event.SimulateEntityEvent;
import io.ilikeorangutans.ancol.game.player.event.BeginTurnEvent;
import io.ilikeorangutans.ancol.game.player.event.PickNextEntityEvent;
import io.ilikeorangutans.ancol.game.player.event.TurnConcludedEvent;
import io.ilikeorangutans.ancol.map.viewport.CenterViewEvent;
import io.ilikeorangutans.ancol.select.event.SelectEntityEvent;
import io.ilikeorangutans.bus.Emitter;
import io.ilikeorangutans.bus.Subscribe;
import io.ilikeorangutans.ecs.ComponentType;
import io.ilikeorangutans.ecs.Entities;
import io.ilikeorangutans.ecs.Entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Will pick the next unit for the player to select, and, if applicable, simulate entities with queued/pending
 * activities before the end of the turn. Instances of this are bound to a specific player.
 */
public class NextUnitPicker {

	private final Player player;

	private final Emitter emitter;

	private final Entities entities;

	/**
	 * Flag whether we are allowed to act or not; enabled when the current player switches to the player this instance
	 * was initialized with.
	 */
	private boolean enabled = false;

	public NextUnitPicker(Emitter emitter, Player player, Entities entities) {
		this.player = player;
		this.emitter = emitter;
		this.entities = entities;
	}

	@Subscribe
	public void onActivityComplete(ActivityCompleteEvent event) {
		if (!event.entity.getComponent(ActivityComponent.class).canPerform())
			selectNextEntity();
	}

	@Subscribe
	public void onActionPointsConsumed(ActionPointsConsumedEvent event) {
		selectNextEntity();
	}

	@Subscribe
	public void onPickNextEntity(PickNextEntityEvent event) {
		selectNextEntity();
	}

	@Subscribe
	public void onBeginTurn(BeginTurnEvent event) {
		if (event.player.equals(player)) {
			enabled = true;
			selectNextEntity();
		}
	}

	private void selectNextEntity() {
		if (!enabled)
			return;

		List<Entity> allUnits = getNextUnits();

		for (Entity entity : allUnits) {
			ActivityComponent ac = entity.getComponent(ActivityComponent.class);

			if (!ac.canPerform()) {
				continue;
			}

			if (!ac.hasActivity()) {
				emitter.fire(new SelectEntityEvent(entity));
				emitter.fire(new CenterViewEvent(entity));
				return;
			}

			if (ac.hasActivity() && ac.canPerform()) {
				emitter.fire(new SimulateEntityEvent(entity));
				emitter.fire(new CenterViewEvent(entity));
			}

		}

		enabled = false;
		emitter.fire(new TurnConcludedEvent());
	}

	private List<Entity> getNextUnits() {

		List<Entity> controllable = entities.getEntityByType(ComponentType.fromClasses(PlayerOwnedComponent.class, ActivityComponent.class, ControllableComponent.class));
		if (controllable.size() == 0)
			return Collections.emptyList();

		List<Entity> result = new ArrayList<Entity>();
		for (Entity entity : controllable) {
			if (!entity.getComponent(ControllableComponent.class).isActive())
				continue;

			result.add(entity);
		}

		Collections.sort(result, new Comparator<Entity>() {
			@Override
			public int compare(Entity o1, Entity o2) {

				ActivityComponent ac1 = o1.getComponent(ActivityComponent.class);
				ActivityComponent ac2 = o2.getComponent(ActivityComponent.class);

				if (ac1.hasActivity() && ac2.hasActivity()) {
					return 0;
				}

				if (ac1.hasActivity() && !ac2.hasActivity())
					return 1;

				if (ac2.hasActivity() && !ac1.hasActivity())
					return -1;

				return 0;
			}
		});

		return result;
	}

}

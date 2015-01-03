package io.ilikeorangutans.ancol.game.player;

import io.ilikeorangutans.ancol.game.activity.ActivityComponent;
import io.ilikeorangutans.ancol.game.cmd.ControllableComponent;
import io.ilikeorangutans.ecs.ComponentType;
import io.ilikeorangutans.ecs.Entities;
import io.ilikeorangutans.ecs.Entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Holds a list of the next units the player can issue commands to.
 */
public class NextUnits {

	private final Entities entities;

	private final Player player;

	public NextUnits(Entities entities, Player player) {
		this.entities = entities;
		this.player = player;
	}

	/**
	 * Returns all units of the player
	 *
	 * @return
	 */
	public List<Entity> getActiveUnits() {
		List<Entity> controllable = entities.getEntityByType(ComponentType.fromClasses(PlayerOwnedComponent.class, ActivityComponent.class, ControllableComponent.class));
		if (controllable.size() == 0)
			return Collections.emptyList();

		List<Entity> result = new ArrayList<Entity>();

		for (Entity entity : controllable) {

			if (!entity.getComponent(PlayerOwnedComponent.class).getPlayer().equals(player)) {
				continue;
			}

			if (!entity.getComponent(ControllableComponent.class).isActive()) {
				continue;
			}

			ActivityComponent activityComponent = entity.getComponent(ActivityComponent.class);
			if (!activityComponent.canPerform() || activityComponent.hasActivity()) {
				continue;
			}

			result.add(entity);

		}

		return result;
	}

	public List<Entity> getUnitsWithActivity() {
		List<Entity> controllable = entities.getEntityByType(ComponentType.fromClasses(PlayerOwnedComponent.class, ActivityComponent.class, ControllableComponent.class));
		if (controllable.size() == 0)
			return Collections.emptyList();

		List<Entity> result = new ArrayList<Entity>();

		for (Entity entity : controllable) {

			if (!entity.getComponent(PlayerOwnedComponent.class).getPlayer().equals(player)) {
				continue;
			}

			if (!entity.getComponent(ControllableComponent.class).isActive()) {
				continue;
			}

			ActivityComponent activityComponent = entity.getComponent(ActivityComponent.class);
			if (activityComponent.canPerform() && activityComponent.hasActivity()) {
				continue;
			}

			result.add(entity);

		}

		return result;

	}

}

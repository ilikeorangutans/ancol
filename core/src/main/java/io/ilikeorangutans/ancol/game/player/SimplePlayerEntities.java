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
 * Provides access to a player's entities with different filters. All entities returned by this class have the
 * {@link io.ilikeorangutans.ancol.game.player.PlayerOwnedComponent} and belong to the Player instance provided by the
 * constructor.
 */
public class SimplePlayerEntities implements PlayerEntities {

	private final Entities entities;

	private final Player player;

	public SimplePlayerEntities(Entities entities, Player player) {
		this.entities = entities;
		this.player = player;
	}

	/**
	 * Returns all active units of the player, i.e. entities that have
	 * {@link io.ilikeorangutans.ancol.game.activity.ActivityComponent}, more than zero action points, no pending
	 * activity, and {@link io.ilikeorangutans.ancol.game.cmd.ControllableComponent} with the active flag set to true.
	 *
	 * @return
	 */
	@Override
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

	@Override
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
			if (!activityComponent.canPerform() || !activityComponent.hasActivity()) {
				continue;
			}

			result.add(entity);

		}

		return result;

	}

	@Override
	public List<Entity> getColonies() {
		return null;
	}

}

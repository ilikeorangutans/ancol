package io.ilikeorangutans.ancol.game.ability;

import io.ilikeorangutans.ecs.Entity;

/**
 * An ability describes whether an entity can perform a certain action.
 */
public interface Ability {

	/**
	 * Returns the unique name of this ability.
	 *
	 * @return
	 */
	String getName();

	/**
	 * Checks whether this ability can be used by the given entity. This will return false if the given entity does
	 * not have the ability. Some abilities can be used based on the location or other factors of the entity.
	 *
	 * @param entity
	 * @return true if the given Entity can use this ability right now.
	 */
	boolean canBeUsedBy(Entity entity);
}

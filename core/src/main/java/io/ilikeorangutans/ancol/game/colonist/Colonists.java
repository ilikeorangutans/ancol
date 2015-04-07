package io.ilikeorangutans.ancol.game.colonist;

import io.ilikeorangutans.ecs.Entity;

/**
 * Describes a group of colonists.
 */
public interface Colonists extends Iterable<Entity> {

	int size();

	void add(Entity colonist);

	void remove(Entity colonist);
}

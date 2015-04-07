package io.ilikeorangutans.ancol.game.colony;

import io.ilikeorangutans.ancol.game.colonist.Colonists;
import io.ilikeorangutans.ecs.Entity;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 */
public class ColonistsImpl implements Colonists {
	private final Set<Entity> colonists = new LinkedHashSet<Entity>();

	public int size() {
		return colonists.size();
	}

	@Override
	public void add(Entity colonist) {
		colonists.add(colonist);
	}

	@Override
	public void remove(Entity colonist) {
		colonists.remove(colonist);
	}

	@Override
	public Iterator<Entity> iterator() {
		return colonists.iterator();
	}
}

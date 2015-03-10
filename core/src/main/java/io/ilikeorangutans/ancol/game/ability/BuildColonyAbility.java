package io.ilikeorangutans.ancol.game.ability;

import io.ilikeorangutans.ancol.game.vision.VisionComponent;
import io.ilikeorangutans.ancol.map.surrounding.Surroundings;
import io.ilikeorangutans.ecs.Entity;

/**
 *
 */
public class BuildColonyAbility extends ImmutableAbility {
	public static final String NAME = "found-colony";
	public static final Ability INSTANCE = new BuildColonyAbility();

	private BuildColonyAbility() {
		super(NAME);
	}

	@Override
	public boolean canBeUsedBy(Entity entity) {
		if (!hasAbility(entity, this))
			return false;

		Surroundings surroundings = entity.getComponent(VisionComponent.class).getSurroundings();
		boolean terrainCanBeSettled = surroundings.getCenter().getType().canBeSettled();

		return terrainCanBeSettled;
	}

}

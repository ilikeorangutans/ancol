package io.ilikeorangutans.ancol.game.ability;

import io.ilikeorangutans.ecs.ComponentType;
import io.ilikeorangutans.ecs.Entity;

/**
 *
 */
public class ImmutableAbility implements Ability {

	private final String name;

	public ImmutableAbility(String name) {
		this.name = name;
	}

	public static final boolean hasAbility(Entity entity, Ability ability) {
		if (!entity.hasComponent(ComponentType.fromClass(AbilitiesComponent.class)))
			return false;

		return entity.getComponent(AbilitiesComponent.class).has(ability);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean canBeUsedBy(Entity entity) {
		return false;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ImmutableAbility that = (ImmutableAbility) o;

		if (!name.equals(that.name)) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override

	public String toString() {
		return "ImmutableCapability{" +
				"name='" + name + '\'' +
				'}';
	}
}

package io.ilikeorangutans.ancol.game.ability;

import io.ilikeorangutans.ecs.Component;
import io.ilikeorangutans.ecs.ComponentType;

import java.util.ArrayList;
import java.util.List;

/**
 * Tracks the {@link io.ilikeorangutans.ancol.game.ability.Ability}s this entity has.
 */
public class AbilitiesComponent implements Abilities, Component {

	private static final ComponentType COMPONENT_TYPE = ComponentType.fromClass(AbilitiesComponent.class);

	private final List<Ability> capabilities = new ArrayList<Ability>();

	@Override
	public ComponentType getType() {
		return COMPONENT_TYPE;
	}

	public void remove(Ability ability) {
		capabilities.remove(ability);
	}

	@Override
	public boolean has(Ability ability) {
		return capabilities.contains(ability);
	}

	public AbilitiesComponent add(Ability... caps) {
		for (Ability cap : caps) {
			if (!capabilities.contains(cap))
				capabilities.add(cap);
		}
		return this;
	}
}

package io.ilikeorangutans.ancol.game.ability;

import java.util.HashMap;
import java.util.Map;

/**
 * All existing {@link io.ilikeorangutans.ancol.game.ability.Ability}s in the current mod.
 */
public class AvailableAbilities {

	private final Map<String, Ability> abilities = new HashMap<String, Ability>();

	public AvailableAbilities() {
		abilities.put(BuildColonyAbility.NAME, BuildColonyAbility.INSTANCE);

	}

	/**
	 * Finds an ability by name.
	 *
	 * @param name
	 * @return the ability or null if no such ability exists.
	 */
	public Ability findByName(String name) {
		return abilities.get(name);
	}

}

package io.ilikeorangutans.ancol.game.production.requirement;

import io.ilikeorangutans.ancol.game.colony.building.Building;

/**
 *
 */
public class BuildingRequirement implements Requirement {

	public BuildingRequirement(Building building) {

	}

	@Override
	public boolean isFulfilled() {
		// TODO: here we'd check if the building exists.
		return true;
	}
}

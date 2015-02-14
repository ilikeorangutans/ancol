package io.ilikeorangutans.ancol.game.colony;

import io.ilikeorangutans.ancol.game.colony.building.Building;
import io.ilikeorangutans.ancol.game.production.Modifier;
import io.ilikeorangutans.ancol.game.production.Workplace;

/**
 *
 */
public class BuildingWorkplace implements Workplace {
	private final Building building;

	public BuildingWorkplace(Building building) {
		this.building = building;
	}

	@Override
	public Modifier getModifier() {
		return building;
	}
}

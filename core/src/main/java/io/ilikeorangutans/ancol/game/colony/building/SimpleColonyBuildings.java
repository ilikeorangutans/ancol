package io.ilikeorangutans.ancol.game.colony.building;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class SimpleColonyBuildings implements ColonyBuildings {
	private final List<Building> buildings = new ArrayList<Building>();

	@Override
	public List<Building> getBuildings() {
		return buildings;
	}

	@Override
	public boolean hasBuilding(Blueprint blueprint) {
		return false;
	}

	@Override
	public Building construct(Blueprint blueprint) {
		Building building = blueprint.build();
		buildings.add(building);

		return building;
	}
}

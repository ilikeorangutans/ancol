package io.ilikeorangutans.ancol.game.colony.building;

import io.ilikeorangutans.ancol.game.colonist.AvailableProfessions;
import io.ilikeorangutans.ancol.game.ware.AvailableWares;
import io.ilikeorangutans.ancol.game.ware.Ware;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class AvailableBuildings {

	private List<BuildingTypeFromJSON> buildings;

	public BuildingType findByName(String name) {
		for (BuildingType b : buildings) {
			if (name.equals(b.getName())) {
				return b;
			}
		}

		throw new IllegalArgumentException("No building with name " + name + " found");
	}

	public BuildingType findByOutput(Ware ware) {
		for (BuildingType bt : buildings) {
			if (ware.equals(bt.getOutput()))
				return bt;
		}

		throw new IllegalArgumentException("No building with output " + ware.getName() + " found");
	}

	/**
	 * Returns a list of buildings that every colony starts with.
	 *
	 * @return
	 */
	public List<BuildingType> getInitialBuildings() {

		List<BuildingType> result = new ArrayList<BuildingType>();

		for (BuildingType building : buildings) {
			if (building.isInitial())
				result.add(building);
		}

		return result;
	}

	@Override
	public String toString() {
		return "AvailableBuildings{" +
				"buildings=" + buildings +
				'}';
	}

	public void postProcess(AvailableWares wares, AvailableProfessions professions) {
		for (BuildingTypeFromJSON buildingType : buildings) {
			buildingType.postProcess(this, wares, professions);
		}
	}
}

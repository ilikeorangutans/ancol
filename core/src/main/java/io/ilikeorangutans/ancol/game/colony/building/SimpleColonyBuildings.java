package io.ilikeorangutans.ancol.game.colony.building;

import io.ilikeorangutans.ancol.game.colony.event.BuildingErectedEvent;
import io.ilikeorangutans.ancol.game.ware.Ware;
import io.ilikeorangutans.bus.Emitter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 */
public class SimpleColonyBuildings implements ColonyBuildings {
	private final List<Building> buildings = new ArrayList<Building>();
	private final Emitter emitter;

	public SimpleColonyBuildings(Emitter emitter) {
		this.emitter = emitter;
	}

	@Override
	public List<Building> getBuildings() {
		return buildings;
	}

	@Override
	public Building construct(BuildingType blueprint) {
		Building building = new ProductionBuilding(blueprint, 0, 0);
		buildings.add(building);

		emitter.fire(new BuildingErectedEvent(building));

		return building;
	}

	@Override
	public Building findByOutput(Ware ware) {
		for (Building building : buildings) {
			if (ware.equals(building.getOutput()))
				return building;
		}
		return null;
	}

	@Override
	public Iterator<Building> iterator() {
		return buildings.iterator();
	}
}

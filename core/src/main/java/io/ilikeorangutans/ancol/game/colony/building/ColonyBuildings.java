package io.ilikeorangutans.ancol.game.colony.building;

import io.ilikeorangutans.ancol.game.ware.Ware;

import java.util.List;

/**
 *
 */
public interface ColonyBuildings extends Iterable<Building> {

	List<Building> getBuildings();

	Building construct(BuildingType buildingType);

	Building findByOutput(Ware ware);
}

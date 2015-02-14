package io.ilikeorangutans.ancol.game.colony.building;

import io.ilikeorangutans.ancol.game.ware.Ware;

import java.util.List;

/**
 *
 */
public interface ColonyBuildings {

	List<Building> getBuildings();

	Building construct(BuildingType buildingType);

	Building findByOutput(Ware ware);
}

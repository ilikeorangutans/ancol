package io.ilikeorangutans.ancol.game.colony.building;

import java.util.List;

/**
 *
 */
public interface ColonyBuildings {

	List<Building> getBuildings();

	boolean hasBuilding(Blueprint blueprint);

	Building construct(Blueprint townHall);
}

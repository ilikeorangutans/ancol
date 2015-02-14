package io.ilikeorangutans.ancol.game.colony.event;

import io.ilikeorangutans.ancol.game.colony.building.Building;
import io.ilikeorangutans.bus.Event;

/**
 *
 */
public class BuildingErectedEvent implements Event {

	public final Building building;

	public BuildingErectedEvent(Building building) {

		this.building = building;
	}
}

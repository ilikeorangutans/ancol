package io.ilikeorangutans.ancol.game.colony.building;

import io.ilikeorangutans.ancol.game.production.Workplace;
import io.ilikeorangutans.ancol.game.ware.Ware;

/**
 *
 */
public interface Building extends Workplace {

	String getName();

	Ware getOutput();

	BuildingType getBuildingType();

}

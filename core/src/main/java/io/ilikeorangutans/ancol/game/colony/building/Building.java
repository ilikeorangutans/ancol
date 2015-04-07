package io.ilikeorangutans.ancol.game.colony.building;

import io.ilikeorangutans.ancol.game.colonist.Colonists;
import io.ilikeorangutans.ancol.game.production.Workplace;
import io.ilikeorangutans.ancol.game.ware.Ware;

/**
 *
 */
public interface Building extends Colonists, Workplace {

	String getName();

	Ware getOutput();

	BuildingType getBuildingType();

}

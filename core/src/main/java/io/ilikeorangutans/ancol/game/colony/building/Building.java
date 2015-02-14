package io.ilikeorangutans.ancol.game.colony.building;

import io.ilikeorangutans.ancol.game.production.Modifier;
import io.ilikeorangutans.ancol.game.ware.Ware;

/**
 *
 */
public interface Building extends Modifier {

	String getName();

	Ware getOutput();

	BuildingType getBuildingType();

}

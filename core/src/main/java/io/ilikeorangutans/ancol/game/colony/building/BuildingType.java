package io.ilikeorangutans.ancol.game.colony.building;

import io.ilikeorangutans.ancol.game.colonist.Job;
import io.ilikeorangutans.ancol.game.ware.Ware;

/**
 * Created by jakob on 2015-04-05.
 */
public interface BuildingType {
	int getWorkplaces();

	int getCostHammers();

	int getCostTools();

	int getMinPopulation();

	boolean isInitial();

	Ware getOutput();

	BuildingType getUpgrade();

	String getName();

	Ware getInput();

	/**
	 * Returns the job that can work at this building.
	 *
	 * @return
	 */
	Job getJob();
}

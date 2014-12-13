package io.ilikeorangutans.ancol.game.activity;

import io.ilikeorangutans.ancol.game.actionpoint.ActionPoints;
import io.ilikeorangutans.bus.Emitter;

/**
 *
 */
public interface Activity {

	String getName();

	/**
	 * Performs the activity with the given amount of action points.
	 *
	 * @param bus
	 * @param actionPoints
	 * @return
	 */
	void perform(Emitter bus, ActionPoints actionPoints);

	boolean isComplete();
}

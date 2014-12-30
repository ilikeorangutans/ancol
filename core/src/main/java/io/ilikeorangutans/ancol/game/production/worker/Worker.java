package io.ilikeorangutans.ancol.game.production.worker;

import io.ilikeorangutans.ancol.game.ware.WareType;

/**
 *
 */
public interface Worker {

	/**
	 * Calculates how much of the given output this worker can produce.
	 *
	 * @param type ware that is to be produced.
	 * @return how much this worker can produce of the given ware type.
	 */
	int getOutput(WareType type);
}

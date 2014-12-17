package io.ilikeorangutans.ancol.move;

import io.ilikeorangutans.ancol.Point;

/**
 *
 */
public interface Movable {

	/**
	 * Updates the entity's location to the given point.
	 *
	 * @param destination
	 */
	void moveTo(Point destination);

}

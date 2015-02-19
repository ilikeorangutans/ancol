package io.ilikeorangutans.ancol.map.viewport;

import io.ilikeorangutans.ancol.Point;

/**
 *
 */
public interface ScreenToTile {

	/**
	 * Converts the given screen relative coordinates into tile coordinates.
	 *
	 * @param x
	 * @param y
	 * @return
	 */
	Point screenToTile(int x, int y);
}

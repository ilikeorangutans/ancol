package io.ilikeorangutans.ancol.map;

import io.ilikeorangutans.ancol.Point;
import io.ilikeorangutans.ancol.map.tile.Tile;

/**
 * The most basic map that holds only the terrain. It isCapableOf a height, width, and tiles.
 */
public interface Map {

	/**
	 * Width of the map in tiles.
	 *
	 * @return
	 */
	int getWidth();

	/**
	 * Height of the map in tiles.
	 *
	 * @return
	 */
	int getHeight();

	Tile getTileAt(int x, int y);

	Tile getTileAt(Point p);
}

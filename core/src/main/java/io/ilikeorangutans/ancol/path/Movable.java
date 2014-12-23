package io.ilikeorangutans.ancol.path;

import io.ilikeorangutans.ancol.map.tile.Tile;

/**
 *
 */
public interface Movable {

	boolean canAccess(Tile tile);

	float getCost(Tile tile);

}

package io.ilikeorangutans.ancol.map.tile;

/**
 *
 */
public interface TileTypes extends Iterable<TileType> {
	TileType getTypeForId(int id);

}

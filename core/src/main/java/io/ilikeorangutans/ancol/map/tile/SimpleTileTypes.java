package io.ilikeorangutans.ancol.map.tile;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class SimpleTileTypes implements TileTypes {

	private final Map<Integer, TileType> types = new HashMap<Integer, TileType>();

	public void add(TileType t) {
		types.put(t.getId(), t);
	}

	@Override
	public TileType getTypeForId(int id) {
		return types.get(id);
	}

	@Override
	public String toString() {
		return "SimpleTileTypes{" +
				"types=" + types +
				'}';
	}
}

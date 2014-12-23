package io.ilikeorangutans.ancol.map.tile;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class TileTypes {

	private final Map<Integer, TileType> types = new HashMap<Integer, TileType>();

	public TileTypes() {
		add(new TileType("unexplored", 0));
		add(new TileType("ocean", 1));
		add(new TileType("grassland", 2));
	}

	public void add(TileType t) {
		types.put(t.getId(), t);
	}

	public TileType getTypeForId(int id) {
		return types.get(id);
	}

}

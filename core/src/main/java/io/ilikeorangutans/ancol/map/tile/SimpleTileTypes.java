package io.ilikeorangutans.ancol.map.tile;

import io.ilikeorangutans.ancol.game.ware.AvailableWares;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 *
 */
public class SimpleTileTypes implements TileTypes {

	private final List<TileType> types = new ArrayList<TileType>();
	private HashMap<Integer, TileType> typesById;


	@Override
	public TileType getTypeForId(int id) {
		if (typesById == null) {
			initTypesById();
		}
		return types.get(id);
	}

	private void initTypesById() {
		typesById = new HashMap<Integer, TileType>();
		for (TileType type : types) {
			if (typesById.containsKey(type.getId()))
				throw new IllegalArgumentException("Duplicate tile type id " + type.getId() + " with name " + type.getName());
			typesById.put(type.getId(), type);
		}
	}

	@Override
	public String toString() {
		return "SimpleTileTypes{" +
				"types=" + types +
				'}';
	}

	public void postProcess(AvailableWares wares) {
		for (TileType type : types) {
			type.postProduce(wares);
		}
	}

	@Override
	public Iterator<TileType> iterator() {
		return types.iterator();
	}
}

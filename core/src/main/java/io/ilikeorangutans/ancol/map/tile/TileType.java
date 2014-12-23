package io.ilikeorangutans.ancol.map.tile;

/**
 *
 */
public class TileType {

	private final String name;

	private final int id;

	public TileType(String name, int id) {
		this.name = name;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

}

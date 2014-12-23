package io.ilikeorangutans.ancol.map;

import io.ilikeorangutans.ancol.Point;
import io.ilikeorangutans.ancol.map.tile.Tile;
import io.ilikeorangutans.ancol.map.tile.TileType;
import io.ilikeorangutans.ancol.map.tile.TileTypes;

import java.util.Random;

/**
 *
 */
public class RandomMap implements Map {

	private final int height;
	private final int width;
	private final Tile[] tiles;

	public RandomMap(TileTypes tileTypes, int height, int width) {
		this.height = height;
		this.width = width;
		tiles = new Tile[width * height];

		TileType water = tileTypes.getTypeForId(1);
		TileType grass = tileTypes.getTypeForId(2);

		Random r = new Random();

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {

				TileType tt = water;

				if (y == 0 || x == 0 || y == height - 1 || x == width - 1) {
					tt = water;
				} else if (r.nextInt(10) > 6) {
					tt = water;
				} else {
					tt = grass;
				}

				tiles[index(x, y)] = new Tile(tt);
			}
		}
	}

	public RandomMap(TileTypes tileTypes) {
		this(tileTypes, 30, 30);
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public Tile getTileAt(int x, int y) {
		try {
			return tiles[index(x, y)];
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new RuntimeException("Could not retrieve tile at " + x + "/" + y, e);
		}
	}

	@Override
	public Tile getTileAt(Point p) {
		return getTileAt(p.x, p.y);
	}

	private int index(int x, int y) {
		return (y * width) + x;
	}
}

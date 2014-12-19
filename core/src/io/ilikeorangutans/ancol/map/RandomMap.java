package io.ilikeorangutans.ancol.map;

import io.ilikeorangutans.ancol.map.tile.Tile;
import io.ilikeorangutans.ancol.map.tile.TileType;

/**
 *
 */
public class RandomMap implements Map {

    private final int height;
    private final int width;
    private final Tile[] tiles;

    public RandomMap() {
        height = 30;
        width = 30;

        tiles = new Tile[width * height];

        TileType water = new TileType("water", 1);
        TileType grass = new TileType("grass", 2);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                TileType tt = water;

                if (x > y)
                    tt = grass;

                if (y == 0 || y == height - 1 || x == 0 || x == width - 1) {
                    tt = water;
                }

                tiles[index(x, y)] = new Tile(tt);
            }
        }

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

    private int index(int x, int y) {
        return (y * width) + x;
    }
}

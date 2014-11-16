package io.ilikeorangutans.ancol.map;

/**
 *
 */
public class RandomMap implements Map {

    private final int height;
    private final int width;

    public RandomMap() {
        height = 100;
        width = 100;
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
        return null;
    }
}

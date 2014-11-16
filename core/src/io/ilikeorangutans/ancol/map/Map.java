package io.ilikeorangutans.ancol.map;

/**
 *
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

}

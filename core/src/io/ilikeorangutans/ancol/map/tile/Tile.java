package io.ilikeorangutans.ancol.map.tile;

/**
 *
 */
public class Tile {

    private final TileType type;

    public Tile(TileType type) {
        this.type = type;
    }

    public TileType getType() {
        return type;
    }

}

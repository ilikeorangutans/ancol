package io.ilikeorangutans.ancol.map;

import io.ilikeorangutans.bus.Emitter;

import java.awt.*;

/**
 * Describes a viewport in terms of size and position on the map and provides facilities to convert coordinates within
 * this viewport to map coordinates and vice versa.
 */
public class MapViewport {

    private final Emitter emitter;
    private final Map map;

    /**
     * Position relative to the map and dimensions of the view.
     */
    private int x;
    private int y;
    private int viewWidthInPixels;
    private int viewHeightInPixels;

    /**
     * Dimensions of the map in pixels.
     */
    private int mapWidthInPixels, mapHeightInPixels;

    /**
     * Size of the viewport in tiles with padding to fill in case of scrolling.
     */
    private int widthInTiles, heightInTiles;

    /**
     * Dimensions of the tiles.
     */
    private int tileWidth, tileHeight;

    /**
     * Maximum x and y values for the current dimensions of the viewport.
     */
    private int maxX, maxY;

    public MapViewport(Emitter emitter, int x, int y, int viewWidth, int viewHeight, int tileWidth, int tileHeight, Map map) {
        this.emitter = emitter;

        this.x = x;
        this.y = y;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.map = map;

        mapWidthInPixels = map.getWidth() * tileWidth;
        mapHeightInPixels = map.getHeight() * tileHeight;

        resize(viewWidth, viewHeight);
    }

    public void resize(int width, int height) {
        viewWidthInPixels = width;
        viewHeightInPixels = height;

        maxX = mapWidthInPixels - width;
        maxY = mapHeightInPixels - height;

        widthInTiles = (int) Math.ceil((float)width / (float)tileWidth);
        heightInTiles = (int) Math.ceil((float)height / (float)tileHeight);
    }

    public void moveTo(int x, int y) {
        this.x = bounded(0, x, maxX);
        this.y = bounded(0, y, maxY);
    }

    public void moveBy(int deltaX, int deltaY) {
        x = bounded(0, x + deltaX, maxX);
        y = bounded(0, y + deltaY, maxY);
    }

    private int bounded(int min, int val, int max) {
        if (val < min)
            return min;

        if (val > max)
            return max;

        return val;
    }

    public int getWidthInTiles() {
        return widthInTiles;
    }

    public int getHeightInTiles() {
        return heightInTiles;
    }

    /**
     * Calculates the on screen coordinates of the given map relative coordinates.
     *
     * @param x
     * @param y
     * @return
     */
    public Point mapToScreen(int x, int y) {
        // map: 1000x1000
        // @0,0 100x100

        // 0,100 -> 0,0
        // 0,0 -> 0,100

        // @50,50

        // 100,100 -> 50,50

        // @900,900

        // 900,900 -> 0,100
        // 1000,1000 -> 100,0

        int cx = x - this.x;
        int cy = viewHeightInPixels - (y - this.y);
        return new Point(cx, cy);
    }

    /**
     * Calculates the map relative coordinates given the screen coordinates.
     *
     * @param x
     * @param y
     * @return
     */
    public Point screenToMap(int x, int y) {

        return new Point();
    }

    @Override
    public String toString() {
        return "MapViewport{" +
                "x=" + x +
                ", y=" + y +
                ", viewWidthInPixels=" + viewWidthInPixels +
                ", viewHeightInPixels=" + viewHeightInPixels +
                ", mapWidthInPixels=" + mapWidthInPixels +
                ", mapHeightInPixels=" + mapHeightInPixels +
                ", widthInTiles=" + widthInTiles +
                ", heightInTiles=" + heightInTiles +
                '}';
    }
}

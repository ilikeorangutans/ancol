package io.ilikeorangutans.ancol.map.viewport;

import io.ilikeorangutans.ancol.BoundaryUtils;
import io.ilikeorangutans.ancol.Point;
import io.ilikeorangutans.ancol.map.Map;
import io.ilikeorangutans.bus.Emitter;
import io.ilikeorangutans.bus.Subscribe;


/**
 * Describes a viewport in terms of size and position on the map and provides facilities to convert coordinates within
 * this viewport to map coordinates and vice versa.
 */
public class MapViewport implements MapToScreen, ScreenToMap, ScreenToTile {

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

	public int getTileWidth() {
		return tileWidth;
	}

	public int getTileHeight() {
		return tileHeight;
	}

	public void resize(int width, int height) {
		viewWidthInPixels = width;
		viewHeightInPixels = height;

		maxX = mapWidthInPixels - width;
		maxY = mapHeightInPixels - height;

		widthInTiles = (int) Math.ceil((float) width / (float) tileWidth) + 1;
		heightInTiles = (int) Math.ceil((float) height / (float) tileHeight) + 1;
	}

	public void moveTo(int x, int y) {
		this.x = BoundaryUtils.bounded(0, x, maxX);
		this.y = BoundaryUtils.bounded(0, y, maxY);
	}

	public void moveBy(int deltaX, int deltaY) {
		x = BoundaryUtils.bounded(0, x + deltaX, maxX);
		y = BoundaryUtils.bounded(0, y + deltaY, maxY);
	}

	/**
	 * Returns the width of the viewport in tiles.
	 *
	 * @return
	 */
	public int getWidthInTiles() {
		return widthInTiles;
	}

	/**
	 * Returns the height of the viewport in tiles.
	 *
	 * @return
	 */
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
	@Override
	public Point mapToScreen(int x, int y) {
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
	@Override
	public Point screenToMap(int x, int y) {
		int px = x + this.x;
		int py = y + this.y;

		return new Point(px, py);
	}

	/**
	 * Converts a map relative coordinate to a tile coordinate.
	 *
	 * @return
	 */
	public Point mapToTile(Point m) {
		return new Point((int) Math.floor(m.x / tileWidth), (int) Math.floor(m.y / tileHeight));
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
				", min=" + getFirstVisibleTile() +
				'}';
	}

	@Subscribe
	public void onScrollEvent(ScrollEvent e) {
		moveBy(e.deltaX, e.deltaY);
	}

	/**
	 * Returns the first currently visible tile in this viewport, that is the top left tile that is visible.
	 *
	 * @return //
	 */
	public Point getFirstVisibleTile() {

		// TODO: bug here that will crash in the renderer when scrolling to the bottom right corner of the map.
		// This method does not take into account that we're rendering Ht + 1 tiles, where Ht is the height of the
		// screen in tiles.
		return mapToTile(screenToMap(0, 0));
	}

	/**
	 * Returns true if the given map relative coordinates are visible in this viewport.
	 *
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isVisible(int x, int y) {
		return this.x - getTileWidth() < x && this.x + viewWidthInPixels > x && this.y - getTileHeight() < y && this.y + viewHeightInPixels > y;
	}

	@Override
	public Point screenToTile(int x, int y) {
		Point p = screenToMap(x, y);
		p.x = p.x / getTileWidth();
		p.y = p.y / getTileHeight();
		return p;
	}
}

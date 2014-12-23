package io.ilikeorangutans.ancol.map.viewport;


import io.ilikeorangutans.ancol.Point;

/**
 * Converts map relative coordinates to screen relative coordinates.
 */
public interface MapToScreen {
    Point mapToScreen(int x, int y);
}

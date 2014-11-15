package io.ilikeorangutans.ancol.map;

import io.ilikeorangutans.ancol.Point;

/**
 * Maps screen relative coordinates to map coordinates.
 */
public interface ScreenToMap {
    Point screenToMap(int x, int y);
}

package io.ilikeorangutans.ancol.path;

import io.ilikeorangutans.ancol.Point;

/**
 *
 */
public interface PathFinder {

	Path find(Movable movable, Point from, Point to);

}

package io.ilikeorangutans.ancol.path;

import io.ilikeorangutans.ancol.Point;

/**
 *
 */
public interface Movable {

	boolean canAccess(Point p);

	float getCost(Point p);

}

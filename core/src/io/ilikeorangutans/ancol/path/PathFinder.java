package io.ilikeorangutans.ancol.path;

import io.ilikeorangutans.ancol.Point;
import io.ilikeorangutans.ancol.map.Map;

/**
 *
 */
public interface PathFinder {

	Path find(Map map, Point from, Point to);

}

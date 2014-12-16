package io.ilikeorangutans.ancol.path;

import io.ilikeorangutans.ancol.Point;
import io.ilikeorangutans.ancol.map.Map;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class DumbPathFinder implements PathFinder {
	@Override
	public Path find(Map map, Point from, Point to) {

		List<Point> tmp = new ArrayList<Point>();

		Point cur = from;

		while (!cur.equals(to)) {

			Point p = new Point(to.x, to.y);
			if (cur.x < to.x) {
				p.x = cur.x + 1;
			} else if (cur.x > to.x) {
				p.x = cur.x - 1;
			}

			if (cur.y < to.y) {
				p.y = cur.y + 1;
			} else if (cur.y > to.y) {
				p.y = cur.y - 1;
			}

			cur = p;
			tmp.add(cur);
		}

		return new Path(tmp.toArray(new Point[]{}));
	}
}

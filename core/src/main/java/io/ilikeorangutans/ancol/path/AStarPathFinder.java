package io.ilikeorangutans.ancol.path;

import io.ilikeorangutans.ancol.Point;
import io.ilikeorangutans.ancol.map.Map;
import io.ilikeorangutans.ancol.map.tile.Tile;

import java.util.*;

/**
 *
 */
public class AStarPathFinder implements PathFinder {


	private final Map map;

	public AStarPathFinder(Map map) {
		this.map = map;
	}

	@Override
	public Path find(Movable movable, Point from, Point to) {
		Tile targetTile = map.getTileAt(to);

		if (from.equals(to))
			return new Path(new Point[]{from});

		if (!movable.canAccess(targetTile))
			return new Path(new Point[]{from});

		Queue<SearchNode> frontier = new PriorityQueue<SearchNode>();
		java.util.Map<Point, Point> cameFrom = new HashMap<Point, Point>();
		java.util.Map<Point, Float> costSoFar = new HashMap<Point, Float>();

		frontier.add(new SearchNode(from));
		cameFrom.put(from, null);
		costSoFar.put(from, 0.0F);

		while (!frontier.isEmpty()) {
			final Point current = frontier.remove().p;

			if (current.equals(to))
				break;

			final int yFrom = Math.max(0, current.y - 1);
			final int yTo = Math.min(map.getHeight() - 1, current.y + 1);
			final int xFrom = Math.max(0, current.x - 1);
			final int xTo = Math.min(map.getWidth() - 1, current.x + 1);

			for (int y = yFrom; y < yTo + 1; y++) {
				for (int x = xFrom; x < xTo + 1; x++) {
					if (y == current.y && x == current.x)
						continue;


					Point n = new Point(x, y);

					if (!movable.canAccess(map.getTileAt(n)))
						continue;

					float cost = costSoFar.get(current) + heuristic(current, to);

					if (!costSoFar.containsKey(n) || cost < costSoFar.get(n)) {
						costSoFar.put(n, cost);

						SearchNode node = new SearchNode(n);
						node.priority = cost;
						frontier.add(node);
						cameFrom.put(n, current);
					}
				}
			}
		}

		List<Point> tmp = new ArrayList<Point>();
		Point current = to;

		tmp.add(current);
		while (!current.equals(from)) {
			current = cameFrom.get(current);
			tmp.add(0, current);
		}

		return new Path(tmp.toArray(new Point[]{}));
	}

	private float heuristic(Point from, Point to) {
		float dx = to.x - from.x;
		float dy = to.y - from.y;

		return (float) Math.sqrt((dx * dx) + (dy * dy));
	}

	private class SearchNode implements Comparable<SearchNode> {

		final Point p;

		float priority;

		public SearchNode(Point p) {
			this.p = p;
		}

		@Override
		public int compareTo(SearchNode o) {
			return (int) (priority - o.priority);
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			SearchNode that = (SearchNode) o;

			if (p != null ? !p.equals(that.p) : that.p != null) return false;

			return true;
		}

		@Override
		public int hashCode() {
			return p != null ? p.hashCode() : 0;
		}
	}


}

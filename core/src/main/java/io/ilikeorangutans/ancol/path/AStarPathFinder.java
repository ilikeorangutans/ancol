package io.ilikeorangutans.ancol.path;

import io.ilikeorangutans.ancol.Point;

import java.util.*;

/**
 *
 */
public class AStarPathFinder implements PathFinder {


	private final int mapHeight;
	private final int mapWidth;

	public AStarPathFinder(int mapHeight, int mapWidth) {
		this.mapHeight = mapHeight;
		this.mapWidth = mapWidth;
	}

	@Override
	public Path find(Movable movable, Point from, Point to) {
		if (from.equals(to))
			return new Path(new Point[]{from});

		if (!movable.canAccess(to))
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

			// TODO: can we replace this with something smart using Surroundings?
			final int yFrom = Math.max(0, current.y - 1);
			final int yTo = Math.min(mapHeight - 1, current.y + 1);
			final int xFrom = Math.max(0, current.x - 1);
			final int xTo = Math.min(mapWidth - 1, current.x + 1);

			for (int y = yFrom; y < yTo + 1; y++) {
				for (int x = xFrom; x < xTo + 1; x++) {
					if (y == current.y && x == current.x)
						continue;

					Point n = new Point(x, y);

					if (!movable.canAccess(n))
						continue;

					float newCost = costSoFar.get(current) + movable.getCost(n);

					if (!costSoFar.containsKey(n) || newCost < costSoFar.get(n)) {
						costSoFar.put(n, newCost);
						float priority = newCost + heuristic(n, to);
						frontier.add(new SearchNode(n, priority));
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

		public SearchNode(Point n, float priority) {
			this.p = n;
			this.priority = priority;
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

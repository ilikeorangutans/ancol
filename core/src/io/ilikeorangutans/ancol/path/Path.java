package io.ilikeorangutans.ancol.path;

import io.ilikeorangutans.ancol.Point;

/**
 *
 */
public class Path {

	public final Point[] segments;

	public Path(Point[] segments) {
		this.segments = segments;
	}

	public Point getDestination() {
		return segments[segments.length - 1];
	}
}


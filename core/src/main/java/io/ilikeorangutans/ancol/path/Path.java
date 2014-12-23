package io.ilikeorangutans.ancol.path;

import io.ilikeorangutans.ancol.Point;

/**
 *
 */
public class Path {

	public final Point[] segments;
	private int step = 0;

	public Path(Point[] segments) {
		this.segments = segments;
	}

	public boolean hasDestination() {
		return segments.length > 0;
	}

	public Point getDestination() {
		return segments[segments.length - 1];
	}

	public int getLength() {
		return segments == null ? 0 : segments.length;
	}

	public boolean isEmpty() {
		return getLength() == 0;
	}

	public Point getCurrentStep() {
		return segments[step];
	}

	public Point nextStep() {
		step++;
		return getCurrentStep();
	}

	public boolean isLastStep() {
		return step == segments.length - 1;
	}
}


package io.ilikeorangutans.ancol;

/**
 *
 */
public final class Point {

	public int x, y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Point() {
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Point point = (Point) o;

		if (x != point.x) return false;
		if (y != point.y) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = x;
		result = 31 * result + y;
		return result;
	}

	@Override
	public String toString() {
		return "Point{" +
				"x=" + x +
				", y=" + y +
				'}';
	}
}

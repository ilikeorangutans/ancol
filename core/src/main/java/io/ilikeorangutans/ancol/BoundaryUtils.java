package io.ilikeorangutans.ancol;

/**
 *
 */
public class BoundaryUtils {
	public static int bounded(int min, int val, int max) {
		if (val < min)
			return min;

		if (val > max)
			return max;

		return val;
	}
}

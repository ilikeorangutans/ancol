package io.ilikeorangutans.ancol.game.production.worker;

import io.ilikeorangutans.ancol.game.ware.Ware;

/**
 * Simple worker that works at a fixed rate.
 */
public class FixedWorker implements Worker {

	private final int output;

	public FixedWorker(int output) {
		this.output = output;
	}

	@Override
	public int calculateOutput(Ware type) {
		return output;
	}

	@Override
	public String toString() {
		return "FixedWorker{" +
				"output=" + output +
				'}';
	}
}

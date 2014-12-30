package io.ilikeorangutans.ancol.game.production.worker;

import io.ilikeorangutans.ancol.game.ware.WareType;

/**
 * Simple worker that works at a fixed rate.
 */
public class FixedWorker implements Worker {

	private final int output;

	public FixedWorker(int output) {
		this.output = output;
	}

	@Override
	public int getOutput(WareType type) {
		return output;
	}
}

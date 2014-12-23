package io.ilikeorangutans.ancol.game.activity;

import io.ilikeorangutans.ancol.game.actionpoint.ActionPoints;
import io.ilikeorangutans.bus.Emitter;

/**
 *
 */
public class ImproveTileActivity implements Activity {

	private final int requiredPoints = 5; // TODO: needs to be calculated based on terrain.
	private int remaining;
	private boolean complete = false;

	public ImproveTileActivity() {
		this.remaining = requiredPoints;
	}

	@Override
	public String getName() {
		return "improve tile";
	}

	@Override
	public void perform(Emitter bus, ActionPoints actionPoints) {

		int consume = Math.min(actionPoints.getAvailablePoints(), remaining);
		remaining -= consume;
		actionPoints.consume(consume);

		// TODO: improve tile

		if (remaining <= 0) {
			complete = true;
		}
	}

	@Override
	public boolean isComplete() {
		return complete;
	}
}

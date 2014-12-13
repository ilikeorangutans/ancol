package io.ilikeorangutans.ancol.game.activity;

import io.ilikeorangutans.ancol.game.actionpoint.ActionPoints;
import io.ilikeorangutans.bus.Emitter;

/**
 *
 */
public class BuildColonyActivity implements Activity {

	private boolean complete = false;

	@Override
	public String getName() {
		return "build colony";
	}

	@Override
	public void perform(Emitter bus, ActionPoints actionPoints) {
		if (actionPoints.consume(actionPoints.getAvailablePoints()) > 0) {
			complete = true;
		}

		// TODO: Build colony.
	}

	@Override
	public boolean isComplete() {
		return complete;
	}

}

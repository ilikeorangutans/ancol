package io.ilikeorangutans.ancol.game.activity;

import io.ilikeorangutans.ancol.game.actionpoint.ActionPoints;
import io.ilikeorangutans.bus.Emitter;

/**
 *
 */
public class IdleActivity implements Activity {

	@Override
	public String getName() {
		return "idle";
	}

	@Override
	public void perform(Emitter bus, ActionPoints actionPoints) {
		actionPoints.consume(actionPoints.getAvailablePoints());
	}

	@Override
	public boolean isComplete() {
		return true;
	}

}

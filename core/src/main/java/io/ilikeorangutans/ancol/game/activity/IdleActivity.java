package io.ilikeorangutans.ancol.game.activity;

import io.ilikeorangutans.ancol.game.actionpoint.ActionPoints;
import io.ilikeorangutans.bus.Emitter;

/**
 *
 */
public class IdleActivity implements Activity {

	public static final IdleActivity IDLE_ACTIVITY = new IdleActivity();

	@Override
	public String getName() {
		return "idling";
	}

	@Override
	public void perform(Emitter bus, ActionPoints actionPoints) {
		actionPoints.consume(actionPoints.getAvailablePoints());
	}

	@Override
	public boolean isComplete() {
		return true;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;

		if (obj instanceof IdleActivity)
			return true;

		return false;
	}
}

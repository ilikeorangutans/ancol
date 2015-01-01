package io.ilikeorangutans.ancol.game.activity;

import io.ilikeorangutans.ancol.game.actionpoint.ActionPoints;
import io.ilikeorangutans.bus.Emitter;

/**
 *
 */
public class TestActivity implements Activity {
	private final int points;

	private int consumed = 0;

	public TestActivity(int points) {
		this.points = points;
	}

	@Override
	public String getName() {
		return "test";
	}

	@Override
	public void perform(Emitter bus, ActionPoints actionPoints) {
		consumed += actionPoints.consume(points);
	}

	@Override
	public boolean isComplete() {
		return consumed == points;
	}
}

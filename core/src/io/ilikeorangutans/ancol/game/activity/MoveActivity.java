package io.ilikeorangutans.ancol.game.activity;

import io.ilikeorangutans.ancol.Point;
import io.ilikeorangutans.ancol.game.actionpoint.ActionPoints;
import io.ilikeorangutans.ancol.map.PositionComponent;
import io.ilikeorangutans.ancol.move.MovableComponent;
import io.ilikeorangutans.ancol.path.Path;
import io.ilikeorangutans.bus.Emitter;

/**
 *
 */
public class MoveActivity implements Activity {

	private final Path path;
	private final PositionComponent positionComponent;
	private final MovableComponent movableComponent;
	private int step = 0;

	public MoveActivity(Path path, PositionComponent positionComponent, MovableComponent movableComponent) {

		this.path = path;
		this.positionComponent = positionComponent;
		this.movableComponent = movableComponent;
	}

	@Override
	public String getName() {
		return "move";
	}

	@Override
	public void perform(Emitter emitter, ActionPoints actionPoints) {
		// TODO: calculate action points based on terrain

		actionPoints.consume(1);
		Point p = path.segments[step];
		positionComponent.set(p.x, p.y);
		step++;

		if (isComplete()) {
			movableComponent.setPath(null);
		}

	}

	@Override
	public boolean isComplete() {
		return step == path.segments.length;
	}

}

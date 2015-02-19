package io.ilikeorangutans.ancol.game.activity;

import io.ilikeorangutans.ancol.Point;
import io.ilikeorangutans.ancol.game.actionpoint.ActionPoints;
import io.ilikeorangutans.ancol.map.PositionComponent;
import io.ilikeorangutans.ancol.move.MovableComponent;
import io.ilikeorangutans.ancol.move.MovedEvent;
import io.ilikeorangutans.ancol.path.Path;
import io.ilikeorangutans.bus.Emitter;
import io.ilikeorangutans.ecs.Entity;

/**
 *
 */
public class MoveActivity implements Activity {

	private final Path path;
	private final Entity entity;
	private final PositionComponent positionComponent;
	private final MovableComponent movableComponent;
	private boolean complete;

	public MoveActivity(Path path, Entity entity, PositionComponent positionComponent, MovableComponent movableComponent) {
		this.path = path;
		this.entity = entity;
		this.positionComponent = positionComponent;
		this.movableComponent = movableComponent;
	}

	@Override
	public String getName() {
		return "move";
	}

	@Override
	public void perform(Emitter emitter, ActionPoints actionPoints) {
		if (path.isEmpty() || path.isLastStep())
			return;
		
		Point p = path.nextStep();
		if (!movableComponent.canAccess(p)) {
			movableComponent.setPath(null);
			complete = true;
			return;
		}

		// TODO: calculate action points based on terrain
		actionPoints.consume(1);
		positionComponent.set(p.x, p.y);

		emitter.fire(new MovedEvent(entity, p));

		if (isComplete()) {
			movableComponent.setPath(null);
		}
	}

	@Override
	public boolean isComplete() {
		return complete || path.isLastStep();
	}

}

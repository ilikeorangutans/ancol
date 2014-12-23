package io.ilikeorangutans.ancol.input.action;

import io.ilikeorangutans.ancol.Point;
import io.ilikeorangutans.ancol.game.cmd.MoveCommand;
import io.ilikeorangutans.ancol.game.cmd.event.CommandEvent;
import io.ilikeorangutans.ancol.map.PositionComponent;
import io.ilikeorangutans.ancol.move.MovableComponent;
import io.ilikeorangutans.ancol.path.PathFinder;
import io.ilikeorangutans.ancol.select.EntitySelectedEvent;
import io.ilikeorangutans.bus.Emitter;
import io.ilikeorangutans.bus.Subscribe;
import io.ilikeorangutans.ecs.ComponentType;
import io.ilikeorangutans.ecs.Entity;

/**
 *
 */
public class MoveAction extends Action {

	private final Emitter emitter;
	private final PathFinder pathFinder;
	private Entity entity;
	private Point destination;

	public MoveAction(Emitter emitter, PathFinder pathFinder) {
		this.emitter = emitter;
		this.pathFinder = pathFinder;
	}

	public void setDestination(Point destination) {
		this.destination = destination;
	}

	@Override
	public void doPerform() {
		PositionComponent positionComponent = entity.getComponent(PositionComponent.class);
		MovableComponent mc = entity.getComponent(MovableComponent.class);

		emitter.fire(new CommandEvent(new MoveCommand(pathFinder.find(mc, new Point(positionComponent.getX(), positionComponent.getY()), destination))));
	}

	private boolean hasEntity() {
		return entity != null;
	}

	@Subscribe
	public void onEntitySelected(EntitySelectedEvent event) {
		entity = event.entity;

		setEnabled(false);
		if (hasEntity() && entity.hasComponent(ComponentType.fromClasses(PositionComponent.class))) {
			setEnabled(true);
		}
	}

}

package io.ilikeorangutans.ancol.game.cmd;

import io.ilikeorangutans.ancol.game.activity.ActivityComponent;
import io.ilikeorangutans.ancol.game.activity.MoveActivity;
import io.ilikeorangutans.ancol.map.PositionComponent;
import io.ilikeorangutans.ancol.move.MovableComponent;
import io.ilikeorangutans.ancol.path.Path;
import io.ilikeorangutans.bus.Emitter;
import io.ilikeorangutans.ecs.ComponentType;
import io.ilikeorangutans.ecs.Entity;

/**
 *
 */
public class MoveCommand implements Command {

	private final Path path;

	public MoveCommand(Path path) {
		this.path = path;
	}

	@Override
	public void apply(Emitter bus, Entity entity) {
		if (!entity.hasComponent(ComponentType.fromClasses(MovableComponent.class))) {
			throw new IllegalArgumentException("Cannot apply MoveCommand for entity " + entity + " that does not have MovableComponent");
		}

		if (path.isEmpty())
			return;

		final MovableComponent mc = entity.getComponent(MovableComponent.class);

		mc.setPath(path);

		ActivityComponent ac = entity.getComponent(ActivityComponent.class);
		PositionComponent pc = entity.getComponent(PositionComponent.class);

		ac.setActivity(new MoveActivity(path, entity, pc, mc));
	}

}

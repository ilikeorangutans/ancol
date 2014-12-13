package io.ilikeorangutans.ancol.game.cmd;

import io.ilikeorangutans.ancol.game.activity.ActivityComponent;
import io.ilikeorangutans.ancol.game.activity.IdleActivity;
import io.ilikeorangutans.bus.Emitter;
import io.ilikeorangutans.ecs.Entity;

/**
 *
 */
public class IdleCommand implements Command {

	@Override
	public void apply(Emitter bus, Entity entity) {
		ActivityComponent activityComponent = entity.getComponent(ActivityComponent.class);
		if (activityComponent.hasActivity()) {
			// TODO: Not sure what makes the most sense in this case; this shouldn't happen to begin with. However, it's
			// probably best to not do anything. If the entity is already performing an activity, why bother with
			// the idle activity?
			System.out.println("IdleCommand.apply not adding activity as entity is already performing activity.");
			return;
		}

		activityComponent.setActivity(new IdleActivity());
	}
}

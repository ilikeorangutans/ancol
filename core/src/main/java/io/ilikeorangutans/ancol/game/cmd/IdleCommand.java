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
		activityComponent.setActivity(new IdleActivity());
	}
}

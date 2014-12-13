package io.ilikeorangutans.ancol.game.cmd;

import io.ilikeorangutans.ancol.game.activity.ActivityComponent;
import io.ilikeorangutans.ancol.game.activity.BuildColonyActivity;
import io.ilikeorangutans.bus.Emitter;
import io.ilikeorangutans.ecs.Entity;

/**
 *
 */
public class BuildColonyCommand implements Command {
	@Override
	public void apply(Emitter bus, Entity entity) {
		ActivityComponent ac = entity.getComponent(ActivityComponent.class);
		ac.setActivity(new BuildColonyActivity());
	}
}

package io.ilikeorangutans.ancol.game.cmd;

import io.ilikeorangutans.ancol.game.activity.ActivityComponent;
import io.ilikeorangutans.ancol.game.activity.ImproveTileActivity;
import io.ilikeorangutans.bus.Emitter;
import io.ilikeorangutans.ecs.Entity;

/**
 *
 */
public class ImproveTileCommand implements Command {

	@Override
	public void apply(Emitter bus, Entity entity) {
		ActivityComponent ac = entity.getComponent(ActivityComponent.class);
		ac.setActivity(new ImproveTileActivity());

	}
}

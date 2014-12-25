package io.ilikeorangutans.ancol.game.cmd;

import io.ilikeorangutans.ancol.game.activity.ActivityComponent;
import io.ilikeorangutans.ancol.game.activity.BuildColonyActivity;
import io.ilikeorangutans.bus.Emitter;
import io.ilikeorangutans.ecs.Entity;

/**
 *
 */
public class BuildColonyCommand implements Command {

	public final Entity builder;

	public BuildColonyCommand(Entity builder) {
		this.builder = builder;
	}

	@Override
	public void apply(Emitter bus, Entity entity) {
		ActivityComponent ac = builder.getComponent(ActivityComponent.class);
		ac.setActivity(new BuildColonyActivity(builder));
	}
}

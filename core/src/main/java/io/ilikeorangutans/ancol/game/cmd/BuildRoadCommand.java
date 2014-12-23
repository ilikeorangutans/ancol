package io.ilikeorangutans.ancol.game.cmd;

import io.ilikeorangutans.ancol.game.activity.ActivityComponent;
import io.ilikeorangutans.bus.Emitter;
import io.ilikeorangutans.ecs.Entity;

/**
 *
 */
public class BuildRoadCommand implements Command {

	public final Entity builder;

	public BuildRoadCommand(Entity builder) {
		this.builder = builder;
	}

	@Override
	public void apply(Emitter bus, Entity entity) {
		ActivityComponent ac = entity.getComponent(ActivityComponent.class);
		// TODO: implement
		System.out.println("BuildRoadCommand.apply implement me");
	}
}

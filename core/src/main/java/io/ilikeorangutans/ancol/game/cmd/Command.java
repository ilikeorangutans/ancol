package io.ilikeorangutans.ancol.game.cmd;

import io.ilikeorangutans.bus.Emitter;
import io.ilikeorangutans.ecs.Entity;

/**
 *
 */
public interface Command {

	void apply(Emitter bus, Entity entity);

}

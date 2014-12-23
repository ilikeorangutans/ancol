package io.ilikeorangutans.ancol.game.cmd.event;

import io.ilikeorangutans.ancol.game.cmd.Command;
import io.ilikeorangutans.bus.Event;
import io.ilikeorangutans.ecs.Entity;

/**
 *
 */
public class CommandQueuedEvent implements Event {

	public final Entity entity;

	public final Command command;

	public CommandQueuedEvent(Entity entity, Command command) {
		this.entity = entity;
		this.command = command;
	}
}

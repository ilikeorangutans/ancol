package io.ilikeorangutans.ancol.game.cmd.event;

import io.ilikeorangutans.ancol.game.cmd.Command;
import io.ilikeorangutans.bus.Event;

/**
 * Fired when a new command has been issued for an entity.
 */
public class CommandEvent implements Event {

	public final Command command;

	public CommandEvent(Command command) {
		this.command = command;
	}
}

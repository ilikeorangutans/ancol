package io.ilikeorangutans.ancol.game.event;

import io.ilikeorangutans.ancol.game.cmd.Command;
import io.ilikeorangutans.bus.Event;

/**
 *
 */
public class CommandEvent implements Event {

    public final Command command;

    public CommandEvent(Command command) {
        this.command = command;
    }
}

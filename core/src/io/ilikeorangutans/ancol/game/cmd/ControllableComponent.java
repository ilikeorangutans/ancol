package io.ilikeorangutans.ancol.game.cmd;

import io.ilikeorangutans.ecs.Component;
import io.ilikeorangutans.ecs.ComponentType;

import java.util.LinkedList;
import java.util.Queue;

/**
 * An entity that can take, queue, and execute {@link io.ilikeorangutans.ancol.game.cmd.Command}s.
 */
public class ControllableComponent implements Component {

	public static final ComponentType COMPONENT_TYPE = ComponentType.fromClass(ControllableComponent.class)[0];

	private final Queue<Command> commands = new LinkedList<Command>();

	public void add(Command cmd) {
		commands.add(cmd);
	}

	public boolean hasCommands() {
		return !commands.isEmpty();
	}

	@Override
	public ComponentType getType() {
		return COMPONENT_TYPE;
	}

	public Command getNextCommand() {
		if (!hasCommands()) {
			// TODO: this might not be a good idea... ?
			return new IdleCommand();
		}

		return commands.remove();
	}

	public int getQueueLength() {
		return commands.size();
	}
}

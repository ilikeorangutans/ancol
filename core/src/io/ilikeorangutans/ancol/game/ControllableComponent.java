package io.ilikeorangutans.ancol.game;

import io.ilikeorangutans.ancol.game.cmd.Command;
import io.ilikeorangutans.ancol.game.cmd.NullCommand;
import io.ilikeorangutans.ecs.Component;
import io.ilikeorangutans.ecs.ComponentType;

import java.util.LinkedList;
import java.util.Queue;

/**
 *
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
            return new NullCommand();
        }

        return commands.remove();
    }
}

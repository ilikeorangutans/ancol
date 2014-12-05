package io.ilikeorangutans.ancol.game.cmd;

import io.ilikeorangutans.ecs.Entity;

/**
 *
 */
public class BuildColonyCommand implements Command {
    @Override
    public void apply(Entity entity) {
        System.out.println("BuildColonyCommand.apply BUILDING COLONY!");
    }
}

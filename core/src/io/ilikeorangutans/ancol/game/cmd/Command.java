package io.ilikeorangutans.ancol.game.cmd;

import io.ilikeorangutans.ecs.Entity;

/**
 *
 */
public interface Command {

    void apply(Entity entity);

}

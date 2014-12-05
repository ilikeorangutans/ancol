package io.ilikeorangutans.ancol.game.cmd;

import io.ilikeorangutans.ancol.game.ControllableComponent;
import io.ilikeorangutans.ancol.game.event.CommandEvent;
import io.ilikeorangutans.ancol.select.SelectedEvent;
import io.ilikeorangutans.bus.Subscribe;
import io.ilikeorangutans.ecs.ComponentType;
import io.ilikeorangutans.ecs.Entity;

/**
 *
 */
public class CommandManager {

    private Entity entity;

    @Subscribe
    public void onSelected(SelectedEvent e) {
        entity = e.entity;
    }

    @Subscribe
    public void onCommand(CommandEvent e) {
        if (entity == null)
            return;

        if (!entity.hasComponent(ComponentType.fromClass(ControllableComponent.class)))
            throw new IllegalStateException("Cannot give command to " + entity);

        System.out.println("CommandManager.onCommand " + e.command + " for " + entity);

        ControllableComponent cc = entity.getComponent(ControllableComponent.class);

        cc.add(e.command);
    }

}

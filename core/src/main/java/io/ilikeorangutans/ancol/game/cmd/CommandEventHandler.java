package io.ilikeorangutans.ancol.game.cmd;

import io.ilikeorangutans.ancol.game.cmd.event.CommandEvent;
import io.ilikeorangutans.ancol.game.cmd.event.CommandQueuedEvent;
import io.ilikeorangutans.ancol.select.event.EntitySelectedEvent;
import io.ilikeorangutans.bus.Emitter;
import io.ilikeorangutans.bus.Subscribe;
import io.ilikeorangutans.ecs.ComponentType;
import io.ilikeorangutans.ecs.Entity;

/**
 * Listens to command events and adds commands to entities.
 */
public class CommandEventHandler {

	private final Emitter emitter;
	private Entity entity;

	public CommandEventHandler(Emitter emitter) {
		this.emitter = emitter;
	}

	@Subscribe
	public void onSelected(EntitySelectedEvent e) {
		entity = e.entity;
	}

	@Subscribe
	public void onCommand(CommandEvent e) {
		if (entity == null)
			return;

		if (!entity.hasComponent(ComponentType.fromClasses(ControllableComponent.class)))
			throw new IllegalStateException("Cannot give command to " + entity);

		ControllableComponent cc = entity.getComponent(ControllableComponent.class);

		cc.add(e.command);

		emitter.fire(new CommandQueuedEvent(entity, e.command));
		entity.updated();
	}

}

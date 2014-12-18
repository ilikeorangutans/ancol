package io.ilikeorangutans.ancol.input.action;

import io.ilikeorangutans.ancol.game.cmd.BuildRoadCommand;
import io.ilikeorangutans.ancol.game.cmd.event.CommandEvent;
import io.ilikeorangutans.ancol.select.EntitySelectedEvent;
import io.ilikeorangutans.bus.Emitter;
import io.ilikeorangutans.bus.Subscribe;
import io.ilikeorangutans.ecs.Entity;

/**
 *
 */
public class BuildRoadAction extends Action {

	private final Emitter emitter;
	private Entity entity;

	public BuildRoadAction(Emitter emitter) {
		this.emitter = emitter;
	}

	@Override
	public void doPerform() {
		emitter.fire(new CommandEvent(new BuildRoadCommand(entity)));
	}

	private boolean hasEntity() {
		return entity != null;
	}

	@Subscribe
	public void onEntitySelected(EntitySelectedEvent event) {
		entity = event.entity;

		setEnabled(false);
		if (hasEntity()) {
			// TODO: need to check if the selected entity can actually improve tiles.
			setEnabled(true);
		}
	}
}

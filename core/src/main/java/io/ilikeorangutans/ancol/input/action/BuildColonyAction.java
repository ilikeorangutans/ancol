package io.ilikeorangutans.ancol.input.action;

import io.ilikeorangutans.ancol.game.cmd.BuildColonyCommand;
import io.ilikeorangutans.ancol.game.cmd.event.CommandEvent;
import io.ilikeorangutans.ancol.select.EntitySelectedEvent;
import io.ilikeorangutans.bus.Emitter;
import io.ilikeorangutans.bus.Subscribe;
import io.ilikeorangutans.ecs.Entity;

/**
 *
 */
public class BuildColonyAction extends Action {

	private final Emitter emitter;
	private Entity entity;

	public BuildColonyAction(Emitter emitter) {
		this.emitter = emitter;
	}

	@Subscribe
	public void onEntitySelected(EntitySelectedEvent event) {
		entity = event.entity;

		if (entity == null) {
			// TODO: need more checks here: can the selected entity actually build colonies or is it a valid location to build a colony?
			setEnabled(false);
		} else {
			setEnabled(true);
		}
	}

	@Override
	public void doPerform() {
		emitter.fire(new CommandEvent(new BuildColonyCommand(entity)));
	}
}

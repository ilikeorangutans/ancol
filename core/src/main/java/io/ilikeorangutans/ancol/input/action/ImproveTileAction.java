package io.ilikeorangutans.ancol.input.action;

import io.ilikeorangutans.ancol.game.cmd.ImproveTileCommand;
import io.ilikeorangutans.ancol.game.cmd.event.CommandEvent;
import io.ilikeorangutans.ancol.select.EntitySelectedEvent;
import io.ilikeorangutans.bus.Emitter;
import io.ilikeorangutans.bus.Subscribe;
import io.ilikeorangutans.ecs.Entity;

/**
 *
 */
public class ImproveTileAction extends Action {

	private final Emitter emitter;
	private Entity entity;

	public ImproveTileAction(Emitter emitter) {
		this.emitter = emitter;
	}

	@Override
	public void doPerform() {
		// TODO: get the actual tile to be improved.
		emitter.fire(new CommandEvent(new ImproveTileCommand()));
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

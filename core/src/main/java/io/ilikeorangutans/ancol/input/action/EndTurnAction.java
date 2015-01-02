package io.ilikeorangutans.ancol.input.action;

import io.ilikeorangutans.ancol.game.player.event.TurnConcludedEvent;
import io.ilikeorangutans.bus.Emitter;

/**
 *
 */
public class EndTurnAction extends Action {

	private final Emitter emitter;

	public EndTurnAction(Emitter emitter) {
		this.emitter = emitter;
		setEnabled(true);
	}

	@Override
	public void doPerform() {
		emitter.fire(new TurnConcludedEvent());
	}
}

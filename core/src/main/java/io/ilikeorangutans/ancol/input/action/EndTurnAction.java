package io.ilikeorangutans.ancol.input.action;

import io.ilikeorangutans.ancol.game.event.AllEntitiesSimulatedEvent;
import io.ilikeorangutans.ancol.game.event.SimulateQueuedEntitiesEvent;
import io.ilikeorangutans.ancol.game.player.event.TurnConcludedEvent;
import io.ilikeorangutans.bus.Emitter;
import io.ilikeorangutans.bus.Subscribe;

/**
 *
 */
public class EndTurnAction extends Action {

	private final Emitter emitter;

	private boolean allSimulated = false;

	public EndTurnAction(Emitter emitter) {
		this.emitter = emitter;
		setEnabled(true);
	}

	@Override
	public void doPerform() {
		if (allSimulated) {
			allSimulated = false;
			emitter.fire(new TurnConcludedEvent());
		} else {
			emitter.fire(new SimulateQueuedEntitiesEvent());
		}
	}

	@Subscribe
	public void onAllEntitiesSimulated(AllEntitiesSimulatedEvent event) {
		allSimulated = true;
	}
}

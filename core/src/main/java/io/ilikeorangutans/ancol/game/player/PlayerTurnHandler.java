package io.ilikeorangutans.ancol.game.player;

import io.ilikeorangutans.ancol.game.GameState;
import io.ilikeorangutans.ancol.game.event.GameStartedEvent;
import io.ilikeorangutans.ancol.game.player.event.BeginTurnEvent;
import io.ilikeorangutans.ancol.game.player.event.TurnConcludedEvent;
import io.ilikeorangutans.bus.Emitter;
import io.ilikeorangutans.bus.Subscribe;

/**
 * The player turn loop. Keeps track which player is currently active and manages the succession of players. Listens to
 * the necessary events to proceed the player loop and emits events to signal change of the active player.
 */
public class PlayerTurnHandler {

	private final GameState gameState;
	private Emitter emitter;

	public PlayerTurnHandler(Emitter bus, GameState gameState) {
		this.emitter = bus;
		this.gameState = gameState;
	}

	@Subscribe
	public void onTurnConcluded(TurnConcludedEvent e) {
		nextPlayer();
	}

	private void nextPlayer() {
		gameState.nextPlayer();

		// Important that this event is queued; otherwise the order of the events will be messed up as all event
		// handlers for BeginTurnEvent would be executed synchronously right here and then be followed up by the
		// handlers for the TurnConcludedEvent.
		emitter.queue(new BeginTurnEvent(gameState.getCurrentPlayer()));
	}

	@Subscribe
	public void onGameStarted(GameStartedEvent event) {
		start();
	}

	public void start() {
		emitter.fire(new BeginTurnEvent(gameState.getCurrentPlayer()));
	}


}

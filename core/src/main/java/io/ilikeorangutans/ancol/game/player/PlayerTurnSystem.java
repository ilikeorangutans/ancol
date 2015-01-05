package io.ilikeorangutans.ancol.game.player;

import io.ilikeorangutans.ancol.game.event.GameStartedEvent;
import io.ilikeorangutans.ancol.game.player.event.BeginTurnEvent;
import io.ilikeorangutans.ancol.game.player.event.PlayerJoinedEvent;
import io.ilikeorangutans.ancol.game.player.event.TurnConcludedEvent;
import io.ilikeorangutans.bus.Emitter;
import io.ilikeorangutans.bus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * The player turn loop. Keeps track which player is currently active and manages the succession of players. Listens to
 * the necessary events to proceed the player loop and emits events to signal change of the active player.
 */
public class PlayerTurnSystem {

	private final List<Player> players = new ArrayList<Player>();

	private Emitter bus;
	private int currentPlayerIndex = 0;

	public PlayerTurnSystem(Emitter bus) {
		this.bus = bus;
	}

	@Subscribe
	public void onTurnConcluded(TurnConcludedEvent e) {
		nextPlayer();
	}

	@Subscribe
	public void onGameStarted(GameStartedEvent event) {
		start();
	}

	public void start() {
		bus.fire(new BeginTurnEvent(getCurrentPlayer()));
	}

	public void addPlayer(Player p) {
		players.add(p);
		bus.fire(new PlayerJoinedEvent(p));
	}

	public void nextPlayer() {
		//currentPlayerIndex++;
		currentPlayerIndex = (currentPlayerIndex + 1) % players.size();

		// Important that this event is queued; otherwise the order of the events will be messed up as all event
		// handlers for BeginTurnEvent would be executed synchronously right here and then be followed up by the
		// handlers for the TurnConcludedEvent.
		bus.queue(new BeginTurnEvent(getCurrentPlayer()));
	}

	public Player getCurrentPlayer() {
		return players.get(currentPlayerIndex);
	}
}

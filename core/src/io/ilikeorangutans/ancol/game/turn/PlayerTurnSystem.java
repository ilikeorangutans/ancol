package io.ilikeorangutans.ancol.game.turn;

import io.ilikeorangutans.ancol.game.Player;
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

		bus.fire(new BeginTurnEvent(getCurrentPlayer()));
	}

	public Player getCurrentPlayer() {
		return players.get(currentPlayerIndex);
	}
}

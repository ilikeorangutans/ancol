package io.ilikeorangutans.ancol.game;

import io.ilikeorangutans.ancol.game.player.Player;
import io.ilikeorangutans.ancol.map.Map;

import java.util.ArrayList;
import java.util.List;

/**
 * Encapsulates the game state i.e. everything that, if given to the game mechanics engine, makes a game.
 */
public class GameState {

	private final List<Player> players = new ArrayList<Player>();
	private int currentPlayerIndex = 0;
	private Map map;

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public Player getCurrentPlayer() {
		return players.get(currentPlayerIndex);
	}

	public void addPlayer(Player p) {
		players.add(p);
		// Not sure if this is the best place for this. // emitter.fire(new PlayerJoinedEvent(p));
	}

	public void nextPlayer() {
		currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
	}


}

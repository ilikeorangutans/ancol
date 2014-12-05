package io.ilikeorangutans.ancol.game;

import io.ilikeorangutans.ancol.game.event.BeginTurnEvent;
import io.ilikeorangutans.ancol.game.event.PlayerJoinedEvent;
import io.ilikeorangutans.ancol.game.event.TurnConcludedEvent;
import io.ilikeorangutans.bus.Emitter;
import io.ilikeorangutans.bus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 *
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

    public void addPlayer(Player p) {
        players.add(p);
        bus.fire(new PlayerJoinedEvent(p));
    }


    public void nextPlayer() {
        currentPlayerIndex = currentPlayerIndex++ % players.size();

        bus.fire(new BeginTurnEvent(getCurrentPlayer()));
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }
}

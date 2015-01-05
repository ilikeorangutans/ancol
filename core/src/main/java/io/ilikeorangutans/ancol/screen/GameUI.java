package io.ilikeorangutans.ancol.screen;

import com.badlogic.gdx.Gdx;
import io.ilikeorangutans.ancol.game.player.Player;
import io.ilikeorangutans.ancol.map.viewport.MapViewport;
import io.ilikeorangutans.bus.EventBus;

/**
 *
 */
public class GameUI {

	private final MapViewport viewport;

	public GameUI(EventBus bus, Player player) {

		viewport = new MapViewport(bus, 30, 30, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 60, 60, player.getMap());

	}

	public MapViewport getViewport() {
		return viewport;
	}
}

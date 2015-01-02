package io.ilikeorangutans.ancol.map;

import io.ilikeorangutans.ancol.Point;
import io.ilikeorangutans.ancol.game.player.Player;
import io.ilikeorangutans.ancol.game.player.event.BeginTurnEvent;
import io.ilikeorangutans.ancol.game.player.event.PlayerJoinedEvent;
import io.ilikeorangutans.ancol.map.tile.Tile;
import io.ilikeorangutans.ancol.map.tile.TileType;
import io.ilikeorangutans.bus.EventBus;
import io.ilikeorangutans.bus.Subscribe;

import java.util.HashMap;

/**
 * Holds references to all player owned maps and will delegate all calls to the map of the currently active player.
 */
public class CurrentPlayerMap implements Map {

	private final java.util.Map<Player, Map> maps = new HashMap<Player, Map>();

	private final EventBus bus;
	private final Map map;
	private final TileType unexplored;
	private Map current;

	public CurrentPlayerMap(EventBus bus, Map map, TileType unexplored) {
		this.bus = bus;
		this.map = map;
		this.unexplored = unexplored;
		current = map;
	}

	public Map getMapForPlayer(Player player) {
		return maps.get(player);
	}

	@Subscribe
	public void onPlayerJoined(PlayerJoinedEvent event) {
		PlayerVisibilityMap playerVisibilityMap = new PlayerVisibilityMap(map, event.player, unexplored);
		bus.subscribe(playerVisibilityMap);
		maps.put(event.player, playerVisibilityMap);

	}

	@Subscribe
	public void onBeginTurn(BeginTurnEvent event) {
		current = maps.get(event.player);
	}

	@Override
	public int getWidth() {
		return map.getWidth();
	}

	@Override
	public int getHeight() {
		return map.getHeight();
	}

	@Override
	public Tile getTileAt(int x, int y) {
		return current.getTileAt(x, y);
	}

	@Override
	public Tile getTileAt(Point p) {
		return getTileAt(p.x, p.y);
	}
}

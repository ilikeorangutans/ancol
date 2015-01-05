package io.ilikeorangutans.ancol.game;

import com.badlogic.gdx.Gdx;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.ilikeorangutans.ancol.game.activity.ActivityComponent;
import io.ilikeorangutans.ancol.game.cmd.ControllableComponent;
import io.ilikeorangutans.ancol.game.colonist.ColonistComponent;
import io.ilikeorangutans.ancol.game.colonist.Profession;
import io.ilikeorangutans.ancol.game.player.Player;
import io.ilikeorangutans.ancol.game.player.PlayerOwnedComponent;
import io.ilikeorangutans.ancol.game.vision.VisionComponent;
import io.ilikeorangutans.ancol.graphics.RenderableComponent;
import io.ilikeorangutans.ancol.map.Map;
import io.ilikeorangutans.ancol.map.PlayerVisibilityMap;
import io.ilikeorangutans.ancol.map.PositionComponent;
import io.ilikeorangutans.ancol.map.RandomMap;
import io.ilikeorangutans.ancol.map.tile.SimpleTileTypes;
import io.ilikeorangutans.ancol.map.tile.TileTypes;
import io.ilikeorangutans.ancol.move.MovableComponent;
import io.ilikeorangutans.ancol.select.SelectableComponent;
import io.ilikeorangutans.bus.EventBus;
import io.ilikeorangutans.ecs.EntitiesEntityFactory;
import io.ilikeorangutans.ecs.NameComponent;

import java.util.Collection;
import java.util.HashMap;

/**
 *
 */
public class Startup {

	private final EventBus bus;
	private final EntitiesEntityFactory entities;
	private final java.util.Map<Integer, Player> players = new HashMap<Integer, Player>();
	private TileTypes tileTypes;
	private Map map;

	public Startup(EventBus bus, EntitiesEntityFactory entities) {
		this.bus = bus;
		this.entities = entities;
	}

	public Map getMap() {
		return map;
	}

	public void startSampleGame() {
		loadFromJSON();
		map = new RandomMap(tileTypes);

		Player p1 = new Player(0, "player 1");
		players.put(0, p1);
		PlayerVisibilityMap player1Map = new PlayerVisibilityMap(map, p1, getTileTypes().getTypeForId(0));
		bus.subscribe(player1Map);
		p1.setMap(player1Map);

		Player p2 = new Player(1, "player 2");
		players.put(1, p2);
		PlayerVisibilityMap player2Map = new PlayerVisibilityMap(map, p2, getTileTypes().getTypeForId(0));
		bus.subscribe(player2Map);
		p2.setMap(player2Map);

		Profession profession = new Profession("Free Colonist");
		entities.create(
				new PositionComponent(11, 10),
				new RenderableComponent(1),
				new NameComponent("test entity 1"),
				new SelectableComponent(),
				new MovableComponent(map),
				new PlayerOwnedComponent(p1),
				new ControllableComponent(),
				new ActivityComponent(2),
				new VisionComponent(1),
				new ColonistComponent(profession));
		entities.create(
				new PositionComponent(6, 5),
				new RenderableComponent(1),
				new NameComponent("test entity 2"),
				new SelectableComponent(),
				new MovableComponent(map),
				new PlayerOwnedComponent(p1),
				new ControllableComponent(),
				new ActivityComponent(2),
				new VisionComponent(1),
				new ColonistComponent(profession));
		entities.create(
				new PositionComponent(5, 3),
				new RenderableComponent(1),
				new NameComponent("test entity 3"),
				new SelectableComponent(),
				new MovableComponent(map),
				new PlayerOwnedComponent(p1),
				new ControllableComponent(),
				new ActivityComponent(2),
				new VisionComponent(2),
				new ColonistComponent(new Profession("Seasoned Scout")));
	}

	public void loadFromJSON() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		tileTypes = gson.fromJson(Gdx.files.internal("ancol/tiletypes.json").reader(), SimpleTileTypes.class);
	}

	public TileTypes getTileTypes() {
		return tileTypes;
	}

	public Collection<Player> getPlayers() {
		return players.values();
	}

	public Player getLocalPlayer() {
		return players.get(0);
	}

}


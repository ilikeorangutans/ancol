package io.ilikeorangutans.ancol.game;

import com.badlogic.gdx.Gdx;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.ilikeorangutans.ancol.game.activity.ActivityComponent;
import io.ilikeorangutans.ancol.game.cargo.CargoHoldComponent;
import io.ilikeorangutans.ancol.game.cargo.EntityTransportable;
import io.ilikeorangutans.ancol.game.cargo.ShipComponent;
import io.ilikeorangutans.ancol.game.cargo.TransportableComponent;
import io.ilikeorangutans.ancol.game.cmd.ControllableComponent;
import io.ilikeorangutans.ancol.game.colonist.ColonistComponent;
import io.ilikeorangutans.ancol.game.colonist.Profession;
import io.ilikeorangutans.ancol.game.player.Player;
import io.ilikeorangutans.ancol.game.player.PlayerOwnedComponent;
import io.ilikeorangutans.ancol.game.vision.VisionComponent;
import io.ilikeorangutans.ancol.game.ware.WareType;
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
import io.ilikeorangutans.ecs.Entity;
import io.ilikeorangutans.ecs.NameComponent;

import java.util.Collection;
import java.util.HashMap;

/**
 *
 */
public class GameStateBuilder {

	private final EventBus bus;
	private final java.util.Map<Integer, Player> players = new HashMap<Integer, Player>();
	private final GameState gameState;
	private EntitiesEntityFactory entities;
	private TileTypes tileTypes;
	private Map map;

	public GameStateBuilder(EventBus bus) {
		this.bus = bus;
		gameState = new GameState();
	}

	public GameStateBuilder addPlayer(Player player) {
		players.put(player.getId(), player);
		PlayerVisibilityMap playerMap = new PlayerVisibilityMap(map, player, getTileTypes().getTypeForId(0));
		bus.subscribe(playerMap);
		player.setMap(playerMap);
		gameState.addPlayer(player);

		return this;
	}

	public GameState setupSampleGame() {

		loadFromJSON();
		map = new RandomMap(tileTypes);

		addPlayer(new Player(0, "player 1"));
		addPlayer(new Player(1, "player 2"));

		gameState.setMap(map);


		Profession profession = new Profession("Free Colonist");
		entities.create(
				new PositionComponent(11, 10),
				new RenderableComponent(1),
				new NameComponent("test entity 1"),
				new SelectableComponent(),
				new MovableComponent(map, MovableComponent.MovableType.Land),
				new PlayerOwnedComponent(getLocalPlayer()),
				new ControllableComponent(),
				new ActivityComponent(2),
				new VisionComponent(1),
				new ColonistComponent(profession),
				new TransportableComponent(1));
		entities.create(
				new PositionComponent(6, 5),
				new RenderableComponent(1),
				new NameComponent("test entity 2"),
				new SelectableComponent(),
				new MovableComponent(map, MovableComponent.MovableType.Land),
				new PlayerOwnedComponent(getLocalPlayer()),
				new ControllableComponent(),
				new ActivityComponent(2),
				new VisionComponent(1),
				new ColonistComponent(profession),
				new TransportableComponent(1));
		entities.create(
				new PositionComponent(5, 3),
				new RenderableComponent(1),
				new NameComponent("test entity 3"),
				new SelectableComponent(),
				new MovableComponent(map, MovableComponent.MovableType.Land),
				new PlayerOwnedComponent(getLocalPlayer()),
				new ControllableComponent(),
				new ActivityComponent(2),
				new VisionComponent(2),
				new ColonistComponent(new Profession("Seasoned Scout")),
				new TransportableComponent(1));

		Entity toLoad = entities.create(
				new PositionComponent(1, 1),
				new RenderableComponent(1),
				new NameComponent("loaded test entity 4"),
				new SelectableComponent(),
				new MovableComponent(map, MovableComponent.MovableType.Land),
				new PlayerOwnedComponent(getLocalPlayer()),
				new ControllableComponent(),
				new ActivityComponent(2),
				new VisionComponent(1),
				new ColonistComponent(profession),
				new TransportableComponent(1));
		CargoHoldComponent cargoHoldComponent = new CargoHoldComponent(2);
		cargoHoldComponent.getCargohold().load(new EntityTransportable(toLoad), 1);
		cargoHoldComponent.getCargohold().load(WareType.Muskets, 100);
		entities.create(
				new PositionComponent(0, 1),
				new RenderableComponent(2),
				new NameComponent("ship 1"),
				new SelectableComponent(),
				new MovableComponent(map, MovableComponent.MovableType.Sea),
				new PlayerOwnedComponent(getLocalPlayer()),
				new ControllableComponent(),
				new ActivityComponent(4),
				new VisionComponent(2),
				new ShipComponent(),
				cargoHoldComponent);


		return gameState;
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

	public void withEntities(EntitiesEntityFactory entities) {
		this.entities = entities;
	}
}


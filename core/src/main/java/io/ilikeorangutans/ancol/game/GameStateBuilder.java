package io.ilikeorangutans.ancol.game;

import com.badlogic.gdx.graphics.Color;
import io.ilikeorangutans.ancol.game.ability.AbilitiesComponent;
import io.ilikeorangutans.ancol.game.ability.BuildColonyAbility;
import io.ilikeorangutans.ancol.game.activity.ActivityComponent;
import io.ilikeorangutans.ancol.game.cargo.CargoHoldComponent;
import io.ilikeorangutans.ancol.game.cargo.EntityTransportable;
import io.ilikeorangutans.ancol.game.cargo.ShipComponent;
import io.ilikeorangutans.ancol.game.cargo.TransportableComponent;
import io.ilikeorangutans.ancol.game.cmd.ControllableComponent;
import io.ilikeorangutans.ancol.game.colonist.ColonistComponent;
import io.ilikeorangutans.ancol.game.colonist.Profession;
import io.ilikeorangutans.ancol.game.mod.Mod;
import io.ilikeorangutans.ancol.game.nation.Nation;
import io.ilikeorangutans.ancol.game.player.Player;
import io.ilikeorangutans.ancol.game.player.PlayerOwnedComponent;
import io.ilikeorangutans.ancol.game.vision.PlayerVisibilityMap;
import io.ilikeorangutans.ancol.game.vision.VisionComponent;
import io.ilikeorangutans.ancol.graphics.RenderableComponent;
import io.ilikeorangutans.ancol.map.Map;
import io.ilikeorangutans.ancol.map.PositionComponent;
import io.ilikeorangutans.ancol.map.RandomMap;
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
	private final Mod mod;
	private final java.util.Map<Integer, Player> players = new HashMap<Integer, Player>();
	private final GameState gameState;
	private EntitiesEntityFactory entities;

	private Map map;

	public GameStateBuilder(EventBus bus, Mod mod) {
		this.bus = bus;
		this.mod = mod;
		gameState = new GameState();
	}

	public Player addPlayer(Player player) {
		players.put(player.getId(), player);
		PlayerVisibilityMap playerMap = new PlayerVisibilityMap(map, player, mod.getTileTypes().getTypeForId(0), entities);
		bus.subscribe(playerMap);
		player.setMap(playerMap);
		gameState.addPlayer(player);

		return player;
	}

	public GameState setupSampleGame() {

		map = new RandomMap(mod.getTileTypes());

		addPlayer(new Player(1, "player 1", new Nation("New England", Color.RED)));
		Player p2 = addPlayer(new Player(2, "player 2", new Nation("New France", Color.BLUE)));

		gameState.setMap(map);

		Profession profession = new Profession("Free Colonist", 2);
		Player p = getLocalPlayer();
		AbilitiesComponent abilitiesComponent = new AbilitiesComponent();
		System.out.println("GameStateBuilder.setupSampleGame");
		System.out.println("entities = " + entities);
		System.out.println("p = " + p);
		entities.create(
				new PositionComponent(11, 10),
				new RenderableComponent(1),
				new NameComponent("test entity 1"),
				new SelectableComponent(),
				new MovableComponent(p.getMap(), abilitiesComponent),
				new PlayerOwnedComponent(p),
				new ControllableComponent(),
				new ActivityComponent(2),
				new VisionComponent(1),
				new ColonistComponent(profession),
				new TransportableComponent(1),
				abilitiesComponent.add(MovableComponent.TRAVERSE_LAND, BuildColonyAbility.INSTANCE));
		abilitiesComponent = new AbilitiesComponent();
		entities.create(
				new PositionComponent(6, 5),
				new RenderableComponent(1),
				new NameComponent("test entity 2"),
				new SelectableComponent(),
				new MovableComponent(p2.getMap(), abilitiesComponent),
				new PlayerOwnedComponent(p2),
				new ControllableComponent(),
				new ActivityComponent(2),
				new VisionComponent(1),
				new ColonistComponent(profession),
				new TransportableComponent(1),
				abilitiesComponent.add(MovableComponent.TRAVERSE_LAND, BuildColonyAbility.INSTANCE));
		abilitiesComponent = new AbilitiesComponent();
		entities.create(
				new PositionComponent(5, 3),
				new RenderableComponent(1),
				new NameComponent("test entity 3"),
				new SelectableComponent(),
				new MovableComponent(p.getMap(), abilitiesComponent),
				new PlayerOwnedComponent(p),
				new ControllableComponent(),
				new ActivityComponent(2),
				new VisionComponent(2),
				new ColonistComponent(new Profession("Seasoned Scout", 2)),
				new TransportableComponent(1),
				abilitiesComponent.add(MovableComponent.TRAVERSE_LAND, BuildColonyAbility.INSTANCE));
		abilitiesComponent = new AbilitiesComponent();
		Entity toLoad = entities.create(
				new PositionComponent(1, 1),
				new RenderableComponent(1),
				new NameComponent("loaded test entity 4"),
				new SelectableComponent(),
				new MovableComponent(p.getMap(), abilitiesComponent),
				new PlayerOwnedComponent(p),
				new ControllableComponent(),
				new ActivityComponent(2),
				new VisionComponent(1),
				new ColonistComponent(profession),
				new TransportableComponent(1),
				abilitiesComponent.add(MovableComponent.TRAVERSE_LAND, BuildColonyAbility.INSTANCE));
		abilitiesComponent = new AbilitiesComponent();
		CargoHoldComponent cargoHoldComponent = new CargoHoldComponent(2);
		cargoHoldComponent.getCargohold().load(new EntityTransportable(toLoad), 1);
		entities.create(
				new PositionComponent(0, 1),
				new RenderableComponent(2),
				new NameComponent("ship 1"),
				new SelectableComponent(),
				new MovableComponent(p.getMap(), abilitiesComponent),
				new PlayerOwnedComponent(p),
				new ControllableComponent(),
				new ActivityComponent(4),
				new VisionComponent(2),
				new ShipComponent(),
				cargoHoldComponent,
				abilitiesComponent.add(MovableComponent.TRAVERSE_WATER));


		return gameState;
	}

	public Collection<Player> getPlayers() {
		return players.values();
	}

	public Player getLocalPlayer() {
		return players.get(1);
	}

	public void withEntities(EntitiesEntityFactory entities) {
		this.entities = entities;
	}
}


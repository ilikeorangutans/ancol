package io.ilikeorangutans.ancol.game.colony;

import io.ilikeorangutans.ancol.Point;
import io.ilikeorangutans.ancol.game.cmd.ControllableComponent;
import io.ilikeorangutans.ancol.game.colonist.ColonistComponent;
import io.ilikeorangutans.ancol.game.colony.building.BuildingType;
import io.ilikeorangutans.ancol.game.colony.building.ColonyBuildings;
import io.ilikeorangutans.ancol.game.colony.building.SimpleColonyBuildings;
import io.ilikeorangutans.ancol.game.player.Player;
import io.ilikeorangutans.ancol.game.player.PlayerOwnedComponent;
import io.ilikeorangutans.ancol.game.production.Production;
import io.ilikeorangutans.ancol.game.production.ProductionBuilder;
import io.ilikeorangutans.ancol.game.production.Workplace;
import io.ilikeorangutans.ancol.game.rule.Rules;
import io.ilikeorangutans.ancol.game.ware.Ware;
import io.ilikeorangutans.ancol.game.ware.Warehouse;
import io.ilikeorangutans.ancol.graphics.RenderableComponent;
import io.ilikeorangutans.ancol.map.PositionComponent;
import io.ilikeorangutans.ancol.map.surrounding.GameTileImpl;
import io.ilikeorangutans.ancol.map.surrounding.Surroundings;
import io.ilikeorangutans.ancol.map.tile.Tile;
import io.ilikeorangutans.bus.EventBus;
import io.ilikeorangutans.bus.SimpleEventBus;
import io.ilikeorangutans.ecs.Component;
import io.ilikeorangutans.ecs.ComponentType;
import io.ilikeorangutans.ecs.Entity;
import io.ilikeorangutans.ecs.EntityFactory;

import java.util.*;

/**
 *
 */
public class ColonyComponent extends Observable implements Component {

	public static final ComponentType COMPONENT_TYPE = ComponentType.fromClass(ColonyComponent.class);
	/**
	 * Bus used to distribute events within the colony.
	 */
	private transient final EventBus localBus = new SimpleEventBus();
	private transient final Rules rules;
	private final EntityFactory entityFactory;

	private final List<Entity> colonists = new ArrayList<Entity>();
	private final Surroundings surroundings;
	private Warehouse warehouse;
	private ColonyProduction output;
	private ColonyBuildings buildings;
	private String name;
	private Player player;
	private Map<Point, TileWorkplace> workedTiles = new HashMap<Point, TileWorkplace>();

	public ColonyComponent(String name, Surroundings surroundings, Rules rules, EntityFactory entities) {
		this.name = name;
		this.surroundings = surroundings;
		this.rules = rules;
		this.entityFactory = entities;
	}

	/**
	 * Called when the colony is founded
	 */
	public void found(Player player) {
		this.player = player;
		warehouse = new Warehouse(rules.getWares().getAll());
		output = new ColonyProduction();
		buildings = new SimpleColonyBuildings(localBus);

		for (BuildingType buildingType : rules.getBuildings().getInitialBuildings()) {
			buildings.construct(buildingType);
		}

		localBus.subscribe(output);
	}

	public boolean isTileWorked(Tile p) {


		return false;
	}

	@Override
	public ComponentType getType() {
		return COMPONENT_TYPE;
	}

	/**
	 * Adds the given colonist to the colony. This will automatically pick an appropriate job for the colonist.
	 *
	 * @param colonist the colonist to add.
	 */
	public void addColonist(Entity colonist) {
		// Take the colonist off the map and remove any pending commands.
		colonist.getComponent(RenderableComponent.class).setVisible(false);
		ControllableComponent controllableComponent = colonist.getComponent(ControllableComponent.class);
		controllableComponent.clearCommands();
		controllableComponent.setActive(false);

		colonists.add(colonist);

		// Figure out what we need to produce:

		Ware ware = pickBestGood(colonist);
		Workplace workplace = createWorkplaceFor(colonist, ware);

		ColonistComponent colonistComponent = colonist.getComponent(ColonistComponent.class);
		Production production = new ProductionBuilder()
				.produce(ware)
				.with(colonistComponent)
				.at(workplace)
				.create();

		output.addProduction(production);
	}

	/**
	 * Finds the best workplace to produce the given ware
	 *
	 * @param ware what we want to produce
	 * @return
	 */
	private Workplace createWorkplaceFor(Entity colonist, Ware ware) {
		if (ware.isHarvested()) {

			GameTileImpl tile = null;
			for (GameTileImpl gameTile : surroundings.getAllWithoutCenter()) {

				// TOOD: this needs to actually look at the tiles.
				if (new Random().nextBoolean()) {
					tile = gameTile;
					break;
				}
			}

			if (tile == null)
				throw new IllegalStateException("Could not find a tile that produces " + ware.getName());

			// Create an entity to record that we are working this tile
			Entity entity = entityFactory.create(new PlayerOwnedComponent(player), new PositionComponent(tile.getPoint()), new FieldsWorkedComponent(player));

			TileWorkplace tileWorkplace = new TileWorkplace(tile.getTile(), entity, colonist);
			workedTiles.put(tile.getPoint(), tileWorkplace);
			return tileWorkplace;
		} else {
			return new BuildingWorkplace(buildings.findByOutput(ware));
		}
	}

	private Ware pickBestGood(Entity colonist) {
		// TODO Need to take into account how full buildings are and what tiles are accessible; also, if there's enough
		// food or raw materials, etc
		return rules.getWares().getAll().get(0);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Warehouse getWarehouse() {
		return warehouse;
	}

	public int getSize() {
		return colonists.size();
	}

	public List<Entity> getColonists() {
		return colonists;
	}

	public Surroundings getSurroundings() {
		return surroundings;
	}

	public List<Entity> getOutsideColonists() {

		List<Entity> entities = surroundings.getEntities(Surroundings.Selector.Center);
		List<Entity> result = new ArrayList<Entity>();

		for (Entity entity : entities) {
			if (!entity.hasComponent(ComponentType.fromClass(ColonistComponent.class)))
				continue;

			// TODO: could just pick all non active colonists
			if (isColonistEmployed(entity))
				continue;

			result.add(entity);
		}

		return result;
	}

	public boolean isColonistEmployed(Entity entity) {
		return colonists.contains(entity);
	}

	public void beginTurn() {
		output.produce(getWarehouse());
		// TODO: need to consume food either directly or preferably as a specific production chain
	}

	public ColonyProduction getOutput() {
		return output;
	}

	public ColonyBuildings getBuildings() {
		return buildings;
	}


	public boolean isTileWorked(Surroundings.Selector selector) {
		GameTileImpl gameTile = surroundings.getTile(selector);
		return workedTiles.containsKey(gameTile.getPoint());
	}

	public TileWorkplace getTileWorkplace(Surroundings.Selector selector) {
		GameTileImpl gameTile = surroundings.getTile(selector);

		return workedTiles.get(gameTile.getPoint());
	}
}

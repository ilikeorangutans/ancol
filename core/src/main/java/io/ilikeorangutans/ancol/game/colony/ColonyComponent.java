package io.ilikeorangutans.ancol.game.colony;

import io.ilikeorangutans.ancol.game.cmd.ControllableComponent;
import io.ilikeorangutans.ancol.game.colonist.ColonistComponent;
import io.ilikeorangutans.ancol.game.colony.building.Blueprint;
import io.ilikeorangutans.ancol.game.colony.building.Building;
import io.ilikeorangutans.ancol.game.colony.building.ColonyBuildings;
import io.ilikeorangutans.ancol.game.colony.building.SimpleColonyBuildings;
import io.ilikeorangutans.ancol.game.production.ProductionBuilder;
import io.ilikeorangutans.ancol.game.production.worker.FixedWorker;
import io.ilikeorangutans.ancol.game.ware.WareType;
import io.ilikeorangutans.ancol.game.ware.Warehouse;
import io.ilikeorangutans.ancol.graphics.RenderableComponent;
import io.ilikeorangutans.ancol.map.surrounding.Surroundings;
import io.ilikeorangutans.ecs.Component;
import io.ilikeorangutans.ecs.ComponentType;
import io.ilikeorangutans.ecs.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class ColonyComponent implements Component {
	public static final ComponentType COMPONENT_TYPE = ComponentType.fromClass(ColonyComponent.class);
	private final List<Entity> colonists = new ArrayList<Entity>();
	private final Surroundings surroundings;
	private final Warehouse warehouse = new Warehouse();
	private final ColonyOutput output;
	private ColonyBuildings buildings;
	private String name;

	public ColonyComponent(String name, Surroundings surroundings) {
		this.name = name;
		this.surroundings = surroundings;

		output = new ColonyOutput();

		buildings = new SimpleColonyBuildings();
		Building townhall = buildings.construct(Blueprint.TownHall);
		output.addProduction(new ProductionBuilder().in(townhall).produce(WareType.LibertyBells).with(new FixedWorker(1)).create());
		buildings.construct(Blueprint.CarpentersShop);


		// Some sample productions for now:
		output.addWorkedTile(WareType.Food, surroundings.getTile(Surroundings.Selector.Center), new FixedWorker(3));
		output.addWorkedTile(WareType.Sugar, surroundings.getTile(Surroundings.Selector.Center), new FixedWorker(2));
		output.addWorkedTile(WareType.Sugar, surroundings.getTile(Surroundings.Selector.N), new FixedWorker(3));
		output.addProduction(new ProductionBuilder().consume(WareType.Sugar).produce(WareType.Rum).with(new FixedWorker(6)).create());


		output.addProduction(new ProductionBuilder().consume(WareType.Tools).produce(WareType.Muskets).with(new FixedWorker(3)).create());
		output.addProduction(new ProductionBuilder().consume(WareType.Ore).produce(WareType.Tools).with(new FixedWorker(4)).create());
		output.addProduction(new ProductionBuilder().produce(WareType.Ore).with(new FixedWorker(5)).create());

	}

	public static ComponentType getComponentType() {
		return COMPONENT_TYPE;
	}

	@Override
	public ComponentType getType() {
		return COMPONENT_TYPE;
	}

	public void addColonist(Entity entity) {
		entity.getComponent(RenderableComponent.class).setVisible(false);
		entity.getComponent(ControllableComponent.class).clearCommands();

		colonists.add(entity);
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
	}

	public ColonyOutput getOutput() {
		return output;
	}

	public ColonyBuildings getBuildings() {
		return buildings;
	}
}

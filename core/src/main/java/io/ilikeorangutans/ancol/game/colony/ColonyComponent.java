package io.ilikeorangutans.ancol.game.colony;

import io.ilikeorangutans.ancol.game.cmd.ControllableComponent;
import io.ilikeorangutans.ancol.game.colonist.ColonistComponent;
import io.ilikeorangutans.ancol.game.colonist.Colonists;
import io.ilikeorangutans.ancol.game.colonist.Job;
import io.ilikeorangutans.ancol.game.colony.building.BuildingType;
import io.ilikeorangutans.ancol.game.colony.building.ColonyBuildings;
import io.ilikeorangutans.ancol.game.colony.building.SimpleColonyBuildings;
import io.ilikeorangutans.ancol.game.colony.event.JobAssignedEvent;
import io.ilikeorangutans.ancol.game.colony.population.Population;
import io.ilikeorangutans.ancol.game.mod.Mod;
import io.ilikeorangutans.ancol.game.player.Player;
import io.ilikeorangutans.ancol.game.player.PlayerOwned;
import io.ilikeorangutans.ancol.game.production.Production;
import io.ilikeorangutans.ancol.game.production.ProductionBuilder;
import io.ilikeorangutans.ancol.game.production.Workplace;
import io.ilikeorangutans.ancol.game.ware.Ware;
import io.ilikeorangutans.ancol.game.ware.Warehouse;
import io.ilikeorangutans.ancol.map.surrounding.Surroundings;
import io.ilikeorangutans.bus.EventBus;
import io.ilikeorangutans.bus.SimpleEventBus;
import io.ilikeorangutans.ecs.Component;
import io.ilikeorangutans.ecs.ComponentType;
import io.ilikeorangutans.ecs.Entity;
import io.ilikeorangutans.ecs.EntityFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 *
 */
public class ColonyComponent extends Observable implements Component, PlayerOwned {

	public static final ComponentType COMPONENT_TYPE = ComponentType.fromClass(ColonyComponent.class);
	/**
	 * Bus used to distribute events within the colony.
	 */
	private transient final EventBus localBus = new SimpleEventBus();
	private transient final Mod mod;
	private final EntityFactory entityFactory;
	private final Surroundings surroundings;

	private final Population population = new Population(localBus);
	private Workplaces workplaces;
	private Warehouse warehouse;
	private ColonyProduction output;

	/**
	 * Returns the local event bus for this colony.
	 *
	 * @return
	 */
	public EventBus getLocalBus() {
		return localBus;
	}

	private ColonyBuildings buildings;
	private String name;
	private Player player;

	// TODO:
	// Create one workplace instance per building/tile
	// Add workers to workplaces and let the workplace deal with the details

	public ColonyComponent(String name, Surroundings surroundings, Mod mod, EntityFactory entities) {
		this.name = name;
		this.surroundings = surroundings;
		this.mod = mod;
		this.entityFactory = entities;
	}

	public Population getPopulation() {
		return population;
	}

	public Workplaces getWorkplaces() {
		return workplaces;
	}

	/**
	 * Called when the colony is founded
	 */
	public void found(Player player) {
		this.player = player;
		warehouse = new Warehouse(mod.getWares().getAll());
		buildings = new SimpleColonyBuildings(localBus);
		constructInitialBuildings();

		workplaces = new Workplaces(mod.getProfessions(), buildings, surroundings);
		output = new ColonyProduction();
		localBus.subscribe(output);


		setupFoodConsumption();
	}

	private void setupFoodConsumption() {
		Production foodConsumption = new ProductionBuilder()
				.consume(mod.getWares().findByName("Corn"))
				.with(population)
				.create();

		output.addProduction(foodConsumption);
	}

	private void constructInitialBuildings() {
		for (BuildingType buildingType : mod.getBuildings().getInitialBuildings()) {
			buildings.construct(buildingType);
		}
	}

	/**
	 * Returns a collection of jobs that a settler can take on in this colony.
	 *
	 * @return
	 */
	public List<Job> getAvailableJobs() {

		List<Job> result = new ArrayList<Job>();
		for (Ware ware : workplaces.getProducibleWares()) {
			result.add(mod.getProfessions().findProducerFor(ware));
		}

		return result;
	}

	@Override
	public ComponentType getType() {
		return COMPONENT_TYPE;
	}

	public void addColonist(Entity colonist, Job job, Workplace workplace) {
		population.add(colonist);
		changeJob(colonist, job, workplace);
	}

	/**
	 * Add the given colonist to the colony and make him work the given job.
	 *
	 * @param colonist the colonist to add
	 * @param job      the job we want the colonist to do
	 */
	public void addColonist(Entity colonist, Job job) {
		Workplace workplace = workplaces.getWorkplaceFor(job.getProduces());
		addColonist(colonist, job, workplace);
	}

	/**
	 * Adds the given colonist to the colony. This will automatically pick an appropriate job and workplace for the
	 * colonist.
	 *
	 * @param colonist the colonist to add.
	 */
	public void addColonist(Entity colonist) {
		Ware ware = pickBestGoodToProduce(colonist);
		Job job = mod.getProfessions().findProducerFor(ware);
		addColonist(colonist, job);
	}

	public void changeJob(Entity colonist, Job job) {
		Workplace workplace = workplaces.getWorkplaceFor(job.getProduces());
		changeJob(colonist, job, workplace);
	}

	public void changeJob(Entity colonist, Job job, Workplace workplace) {
		ColonistComponent colonistComponent = colonist.getComponent(ColonistComponent.class);

		output.unemploy(colonistComponent);

		colonistComponent.setJob(job);
		Production production = new ProductionBuilder()
				.with(colonistComponent)
				.at(workplace)
				.produce(job.getProduces())
				.consume(job.getConsumes())
				.create();
		output.addProduction(production);

		localBus.fire(new JobAssignedEvent(colonist));
		setChanged();
		notifyObservers();
	}

	/**
	 * TODO this should probably go into output or something like that.
	 *
	 * @param colonist
	 * @return
	 */
	private Ware pickBestGoodToProduce(Entity colonist) {
		// TODO Need to take into account how full buildings are and what tiles are accessible; also, if there's enough
		// food or raw materials, etc
		return mod.getWares().getAll().get(0);
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
		return population.size();
	}

	public Surroundings getSurroundings() {
		return surroundings;
	}

	public Colonists getOutsideColonists() {
		List<Entity> entities = surroundings.getCenter().getEntities();

		Colonists result = new ColonistsImpl();
		for (Entity entity : entities) {
			if (!entity.hasComponent(ComponentType.fromClass(ColonistComponent.class)))
				continue;

			if (!entity.getComponent(ControllableComponent.class).isActive())
				continue;

			result.add(entity);
		}

		return result;
	}

	public void beginTurn() {
		output.produce(getWarehouse());
	}

	public ColonyProduction getProduction() {
		return output;
	}

	public ColonyBuildings getBuildings() {
		return buildings;
	}

	/**
	 * Returns the player owning this colony.
	 *
	 * @return
	 */
	@Override
	public Player getPlayer() {
		return player;
	}
}

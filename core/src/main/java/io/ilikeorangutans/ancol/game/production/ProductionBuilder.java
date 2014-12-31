package io.ilikeorangutans.ancol.game.production;

import io.ilikeorangutans.ancol.game.colony.building.Building;
import io.ilikeorangutans.ancol.game.production.requirement.BuildingRequirement;
import io.ilikeorangutans.ancol.game.production.requirement.Requirement;
import io.ilikeorangutans.ancol.game.production.requirement.TileAccessRequirement;
import io.ilikeorangutans.ancol.game.production.worker.Worker;
import io.ilikeorangutans.ancol.game.ware.WareType;
import io.ilikeorangutans.ancol.map.tile.Tile;

import java.util.ArrayList;
import java.util.List;

/**
 * Builder for productions.
 */
public class ProductionBuilder {
	private final List<Requirement> requirements = new ArrayList<Requirement>();
	private final List<Modifier> modifiers = new ArrayList<Modifier>();
	private final List<Worker> workers = new ArrayList<Worker>();
	private WareType output;
	private WareType input;

	public ProductionBuilder consume(WareType input) {
		this.input = input;
		return this;
	}

	public ProductionBuilder produce(WareType output) {
		this.output = output;
		return this;
	}

	public ProductionBuilder in(Building building) {
		modifiers.add(building);
		requirements.add(new BuildingRequirement(building));
		return this;
	}

	public ProductionBuilder on(Tile tile) {
		requirements.add(new TileAccessRequirement(tile));
		return this;
	}

	public ProductionBuilder with(Worker worker) {
		workers.add(worker);
		return this;
	}

	public Production create() {
		if (workers.isEmpty())
			throw new IllegalStateException("Cannot create production without workers");

		if (output == null)
			throw new IllegalStateException("Cannot create production without output");

		Production production = new Production(input, output, workers);

		for (Modifier modifier : modifiers) {
			production.addModifier(modifier);
		}

		return production;
	}
}

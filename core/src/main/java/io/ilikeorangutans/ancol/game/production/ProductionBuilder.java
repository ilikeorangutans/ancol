package io.ilikeorangutans.ancol.game.production;

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
	private WareType output;

	private List<Requirement> requirements = new ArrayList<Requirement>();

	private List<Worker> workers = new ArrayList<Worker>();
	private WareType input;

	public ProductionBuilder consume(WareType input) {
		this.input = input;
		return this;
	}

	public ProductionBuilder produce(WareType output) {
		this.output = output;
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

		return new Production(input, output, workers);
	}
}

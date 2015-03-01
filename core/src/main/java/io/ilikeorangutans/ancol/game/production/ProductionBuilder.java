package io.ilikeorangutans.ancol.game.production;

import io.ilikeorangutans.ancol.game.colony.building.Building;
import io.ilikeorangutans.ancol.game.production.worker.FixedWorker;
import io.ilikeorangutans.ancol.game.production.worker.Worker;
import io.ilikeorangutans.ancol.game.ware.Ware;
import io.ilikeorangutans.ancol.map.tile.Tile;

import java.util.ArrayList;
import java.util.List;

/**
 * Builder for productions.
 */
public class ProductionBuilder {

	private final List<Modifier> modifiers = new ArrayList<Modifier>();
	private Worker worker;
	private Ware output;
	private Ware input;
	private Workplace workplace;

	public ProductionBuilder at(Workplace workplace) {
		this.workplace = workplace;
		return this;
	}

	public ProductionBuilder consume(Ware input) {
		this.input = input;
		return this;
	}

	public ProductionBuilder produce(Ware output) {
		this.output = output;
		return this;
	}

	public ProductionBuilder withFixedOutput(int output) {
		with(new FixedWorker(output));
		return this;
	}

	public ProductionBuilder in(Building building) {
		modifiers.add(building.getModifier());

		produce(building.getOutput());
		return this;
	}

	@Deprecated
	public ProductionBuilder on(Tile tile) {
		return this;
	}

	public ProductionBuilder with(Worker worker) {
		this.worker = worker;
		return this;
	}

	public Production create() {
		if (worker == null)
			throw new IllegalStateException("Cannot create production without workers");

		Production production = new Production(input, output, worker, workplace);

		for (Modifier modifier : modifiers) {
			production.addModifier(modifier);
		}

		return production;
	}

}

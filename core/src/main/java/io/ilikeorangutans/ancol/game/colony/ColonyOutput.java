package io.ilikeorangutans.ancol.game.colony;

import io.ilikeorangutans.ancol.game.production.Production;
import io.ilikeorangutans.ancol.game.production.ProductionBuilder;
import io.ilikeorangutans.ancol.game.production.worker.Worker;
import io.ilikeorangutans.ancol.game.ware.RecordingWares;
import io.ilikeorangutans.ancol.game.ware.WareType;
import io.ilikeorangutans.ancol.game.ware.Wares;
import io.ilikeorangutans.ancol.map.tile.Tile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * The productive output of the colony. Calculates how much of what good gets produced and consumed.
 */
public class ColonyOutput {

	private final List<Production> productions = new ArrayList<Production>();

	public void addWorkedTile(WareType type, Tile tile, Worker worker) {
		Production production = new ProductionBuilder().produce(type).on(tile).with(worker).create();
		addProduction(production);
	}

	/**
	 * Performs a simulated production run and returns a record of what was produced.
	 *
	 * @param wares start amount of wares for the simulation
	 * @return instance that has how much was produced and consumed
	 */
	public RecordingWares simulate(Wares wares) {
		RecordingWares output = new RecordingWares(wares);

		produce(output);

		return output;
	}

	/**
	 * Performs the actual production using the given wares as a starting point.
	 *
	 * @param wares wares to retrieve consumed goods from and store produced goods into.
	 */
	public void produce(Wares wares) {
		for (Production production : productions) {
			if (!production.requirementsFulfilled()) {
				continue;
			}

			production.produce(wares);
		}
	}

	/**
	 * Add a production instance.
	 *
	 * @param production to be added
	 */
	public void addProduction(Production production) {
		productions.add(production);

		// Sort by whether a given production requires inputs or not.
		Collections.sort(productions, new Comparator<Production>() {
			@Override
			public int compare(Production o1, Production o2) {
				boolean o2IsInputToO1 = o1.requiresInput() && o2.getOutput().equals(o1.getInput());
				if (o2IsInputToO1) {
					return 1;
				}

				boolean o1IsInputToO2 = o2.requiresInput() && o1.getOutput().equals(o2.getInput());
				if (o1IsInputToO2) {
					return -1;
				}

				if (!o1.requiresInput() && !o2.requiresInput())
					return 0;

				if (o1.requiresInput())
					return 1;

				return -1;
			}
		});
	}
}

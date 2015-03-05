package io.ilikeorangutans.ancol.game.colony;

import io.ilikeorangutans.ancol.game.colony.population.ColonistLeftEvent;
import io.ilikeorangutans.ancol.game.production.Production;
import io.ilikeorangutans.ancol.game.production.Workplace;
import io.ilikeorangutans.ancol.game.production.chain.ProductionChain;
import io.ilikeorangutans.ancol.game.production.worker.Worker;
import io.ilikeorangutans.ancol.game.ware.RecordingWares;
import io.ilikeorangutans.ancol.game.ware.Wares;
import io.ilikeorangutans.bus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * The productive output of the colony. Calculates how much of what good gets produced and consumed.
 */
public class ColonyProduction {

	private final ProductionChain chain;

	private final List<Production> productions = new ArrayList<Production>();

	public ColonyProduction() {
		chain = new ProductionChain();
	}

	/**
	 * Performs a simulated production run and returns a record of what was produced.
	 *
	 * @param wares start amount of wares for the simulation
	 * @return instance that isCapableOf how much was produced and consumed
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
		chain.produce(wares);
	}

	/**
	 * Add a production instance.
	 *
	 * @param production to be added
	 */
	public void addProduction(Production production) {
		productions.add(production);
		chain.add(production);
	}

	@Subscribe
	public void onColonistLeft(ColonistLeftEvent event) {
		System.out.println("ColonyProduction.onColonistLeft TODO: IMPLEMENT ME!");
	}

	private Production getProductionForWorker(Worker colonist) {
		for (Production p : productions) {
			if (p.employsWorker(colonist))
				return p;
		}
		return null;
	}

	/**
	 * Unemploys the given worker. If the worker is associated with a production, that production will be removed.
	 *
	 * @param worker
	 */
	public void unemploy(Worker worker) {
		Production production = getProductionForWorker(worker);
		if (production == null) {
			return;
		}

		productions.remove(production);
		chain.remove(production);

		//TODO: Fire event!
	}

	public Production getProductionAt(Workplace workplace) {
		for (Production production : productions) {
			if (workplace.equals(production.getWorkplace()))
				return production;
		}
		return null;
	}
}

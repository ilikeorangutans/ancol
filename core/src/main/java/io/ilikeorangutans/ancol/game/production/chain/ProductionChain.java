package io.ilikeorangutans.ancol.game.production.chain;

import io.ilikeorangutans.ancol.game.production.Production;
import io.ilikeorangutans.ancol.game.ware.Wares;

import java.util.*;

/**
 *
 */
public class ProductionChain implements Iterable<Production> {

	/**
	 * Sorts productions based on their inputs and outputs. Productions with output and without input will be first,
	 * productions with only input will be last. All productions in between will be sorted so that if one outputs what
	 * another inputs, they will be in this order.
	 */
	private static final Comparator<Production> COMPARATOR = new Comparator<Production>() {
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
	};

	private final List<Production> productions = new ArrayList<Production>();

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

	public void add(Production production) {
		productions.add(production);
		Collections.sort(productions, COMPARATOR);
	}

	@Override
	public Iterator<Production> iterator() {
		return productions.iterator();
	}

	public void remove(Production production) {
		productions.remove(production);
		Collections.sort(productions, COMPARATOR);
	}
}

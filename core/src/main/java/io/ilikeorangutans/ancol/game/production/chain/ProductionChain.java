package io.ilikeorangutans.ancol.game.production.chain;

import io.ilikeorangutans.ancol.game.ware.Wares;

import java.util.*;

/**
 *
 */
public class ProductionChain implements Iterable<Link> {

	/**
	 * Sorts links based on their inputs and outputs. Productions with output and without input will be first,
	 * links with only input will be last. All links in between will be sorted so that if one outputs what
	 * another inputs, they will be in this order.
	 */
	private static final Comparator<Link> COMPARATOR = new Comparator<Link>() {
		@Override
		public int compare(Link o1, Link o2) {
			boolean o2IsInputToO1 = o1.requiresInput() && o2.getOutput() != null && o2.getOutput().equals(o1.getInput());
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

	private final List<Link> links = new ArrayList<Link>();

	/**
	 * Performs the actual production using the given wares as a starting point.
	 *
	 * @param wares wares to retrieve consumed goods from and store produced goods into.
	 */
	public void produce(Wares wares) {
		for (Link link : links) {
			link.produce(wares);
		}
	}

	public void add(Link link) {
		links.add(link);
		Collections.sort(links, COMPARATOR);
	}

	@Override
	public Iterator<Link> iterator() {
		return links.iterator();
	}

	public void remove(Link link) {
		links.remove(link);
		Collections.sort(links, COMPARATOR);
	}
}

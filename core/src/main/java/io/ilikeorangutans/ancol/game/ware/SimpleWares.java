package io.ilikeorangutans.ancol.game.ware;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class SimpleWares implements Wares {

	private final Map<Ware, Stored> wares = new LinkedHashMap<Ware, Stored>();

	/**
	 * Copy constructor.
	 *
	 * @param wares
	 */
	public SimpleWares(Wares wares) {
		for (Stored stored : wares.getWares()) {
			store(stored.getWare(), stored.getAmount());
		}
	}

	public SimpleWares(List<Ware> wares) {
		for (Ware ware : wares) {
			store(ware, 0);
		}
	}

	@Override
	public String toString() {
		return "SimpleWares{" +
				"wares=" + wares +
				'}';
	}

	@Override
	public void store(Ware type, int amount) {
		if (type == null)
			throw new NullPointerException("Type cannot be null");

		if (!wares.containsKey(type)) {
			wares.put(type, new Stored(type));
		}

		wares.get(type).store(amount);
	}

	@Override
	public Collection<Stored> getWares() {
		return wares.values();
	}

	@Override
	public int retrieve(Ware ware, int amount) {
		int result = Math.min(amount, getAmount(ware));
		store(ware, -result);

		return result;
	}

	@Override
	public int getAmount(Ware type) {
		if (wares.containsKey(type))
			return wares.get(type).getAmount();

		return 0;
	}
}

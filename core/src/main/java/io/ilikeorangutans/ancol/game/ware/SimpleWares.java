package io.ilikeorangutans.ancol.game.ware;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 */
public class SimpleWares implements Wares {

	private final Map<WareType, Stored> wares = new LinkedHashMap<WareType, Stored>();

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

	public SimpleWares() {
	}

	@Override
	public String toString() {
		return "SimpleWares{" +
				"wares=" + wares +
				'}';
	}

	@Override
	public void store(WareType type, int amount) {
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
	public int retrieve(WareType ware, int amount) {
		int result = Math.min(amount, getAmount(ware));
		store(ware, -result);

		return result;
	}

	@Override
	public int getAmount(WareType type) {
		if (wares.containsKey(type))
			return wares.get(type).getAmount();

		return 0;
	}
}
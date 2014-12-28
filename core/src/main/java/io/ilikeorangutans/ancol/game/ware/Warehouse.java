package io.ilikeorangutans.ancol.game.ware;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class Warehouse {

	private final Map<Ware, Stored> wares = new HashMap<Ware, Stored>();

	private int maxPerWare = 100;

	public void setMaxPerWare(int maxPerWare) {
		this.maxPerWare = maxPerWare;
	}

	public int store(Ware ware, int amount) {
		if (!wares.containsKey(ware))
			wares.put(ware, new Stored(ware));

		wares.get(ware).store(amount);

		return wares.get(ware).getAmount();
	}

	public Collection<Stored> getWares() {
		return wares.values();
	}

	public void retrieve(Ware ware, int amount) {
		if (!wares.containsKey(ware) || wares.get(ware).getAmount() > amount) {
			throw new IllegalArgumentException("Cannot retrieve " + amount + " units of " + ware.getName() + " from warehouse, only " + wares.get(ware).getAmount() + " present");
		}

		wares.get(ware).store(-amount);
	}

}

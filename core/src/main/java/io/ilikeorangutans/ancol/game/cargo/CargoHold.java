package io.ilikeorangutans.ancol.game.cargo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Multiple holds that can hold cargo. Each hold can hold only one type of a Transportable at a time.
 */
public class CargoHold {

	public static final int HOLD_CAPACITY = 100;
	private final int holds;

	private final List<Cargo> cargo = new ArrayList<Cargo>();

	public CargoHold(int holds) {
		this.holds = holds;
	}

	/**
	 * Returns the number of cargo holds.
	 *
	 * @return
	 */
	public int getNumHolds() {
		return holds;
	}

	/**
	 * Returns the cargo loaded by holds.
	 *
	 * @return returns a read-only view on the cargo.
	 */
	public List<Cargo> getCargo() {
		return Collections.unmodifiableList(cargo);
	}

	/**
	 * Unloads the cargo in the specified hold. After unloading the cargo the specified hold will be empty.
	 *
	 * @param hold     which hold to unload, zero based index
	 * @param quantity how much to unload
	 * @return the cargo or null if no cargo is in the specified bay
	 */
	public Transportable unload(int hold, int quantity) {
		if (cargo.size() >= hold)
			return null;

		Cargo c = cargo.get(hold);

		return null;
	}

	public int getCapacity() {
		return holds * HOLD_CAPACITY;
	}

	public int getAvailableSpace() {
		int res = 0;

		for (Cargo c : cargo) {
			res += c.getConsumedSpace();
		}

		return getCapacity() - res;
	}

	/**
	 * Loads the given cargo.
	 *
	 * @param t        the actual cargo
	 * @param quantity how much we would like to load.
	 * @return how much of the cargo was loaded.
	 */
	public int load(Transportable t, int quantity) {
		int required = t.getRequiredSpace() * quantity;

		if (getAvailableSpace() <= 0)
			return 0;

		if (required > getCapacity())
			return 0;

		// TODO: this doesn't deal with partially filled holds!

		cargo.add(new CargoInHold(t, quantity));

		return quantity;
	}

	private class CargoInHold implements Cargo {
		Transportable transportable;
		int quantity;

		public CargoInHold(Transportable t, int quantity) {
			transportable = t;
			this.quantity = quantity;
		}

		@Override
		public Transportable getTransportable() {
			return transportable;
		}

		@Override
		public int getQuantity() {
			return quantity;
		}

		@Override
		public int getConsumedSpace() {
			return quantity * transportable.getRequiredSpace();
		}
	}
}

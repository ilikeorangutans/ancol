package io.ilikeorangutans.ancol.game.ware;

import java.util.Collection;

/**
 * An amount of wares.
 */
public interface Wares {

	/**
	 * Adds the given amount of a ware to the storage.
	 *
	 * @param ware
	 * @param amount
	 */
	void store(Ware ware, int amount);

	Collection<Stored> getWares();

	/**
	 * Retrieves a specific amount of the given ware type.
	 *
	 * @param ware
	 * @param amount The requested amount
	 * @return the amount that was actually withdrawn. Depending on the store levels, this might return 0.
	 */
	int retrieve(Ware ware, int amount);

	/**
	 * Returns how much of a given ware is available.
	 *
	 * @param type
	 * @return
	 */
	int getAmount(Ware type);
}

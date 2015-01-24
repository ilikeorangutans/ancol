package io.ilikeorangutans.ancol.game.cargo;

/**
 * An amount of something that can be transported.
 *
 * @see {@link io.ilikeorangutans.ancol.game.cargo.Transportable}
 */
public interface Cargo {

	/**
	 * The transported thing.
	 *
	 * @return
	 */
	Transportable getTransportable();

	/**
	 * How much of it.
	 *
	 * @return
	 */
	int getQuantity();

	/**
	 * Return how much space this piece of cargo requires.
	 *
	 * @return space required,
	 */
	int getConsumedSpace();
}

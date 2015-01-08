package io.ilikeorangutans.ancol.game.cargo;

/**
 *
 */
public interface Cargo {

	Transportable getTransportable();

	int getQuantity();

	/**
	 * Return how much space this piece of cargo requires.
	 *
	 * @return space required,
	 */
	int getConsumedSpace();
}

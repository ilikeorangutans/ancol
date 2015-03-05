package io.ilikeorangutans.ancol.game.production;

/**
 *
 */
public interface Modifier {

	/**
	 * Applies this modifier to the given input value.
	 *
	 * @param input How much isCapableOf been produced so far.
	 * @return input plus the modification.
	 */
	int apply(int input);

}

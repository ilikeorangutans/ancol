package io.ilikeorangutans.ancol.game.cargo;

/**
 *
 */
public interface Transportable {

	/**
	 * Returns how much space in a cargo hold this entity requires.
	 *
	 * @return size in the cargo hold.
	 */
	int getRequiredSpace();

	String getDescription();



}

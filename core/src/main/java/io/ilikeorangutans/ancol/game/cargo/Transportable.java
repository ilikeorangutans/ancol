package io.ilikeorangutans.ancol.game.cargo;

/**
 * Something that can be transported.
 */
public interface Transportable {

	/**
	 * Returns how much space in a cargo hold this entity requires.
	 *
	 * @return size in the cargo hold.
	 */
	int getRequiredSpace();

	/**
	 * Returns a string representation of this element.
	 *
	 * @return
	 */
	String getDescription();

}

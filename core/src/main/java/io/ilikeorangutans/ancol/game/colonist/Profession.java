package io.ilikeorangutans.ancol.game.colonist;

import io.ilikeorangutans.ancol.game.ware.Ware;

/**
 * Describes the profession a
 */
public class Profession {

	private final String name;

	/**
	 * Base productivity for this profession. This is the minimum that a colonist will produce.
	 */
	private final int baseProductivity;

	public Profession(String name, int baseProductivity) {
		this.name = name;
		this.baseProductivity = baseProductivity;
	}

	public int getBaseProductivity() {
		return baseProductivity;
	}

	@Override
	public String toString() {
		return "Profession{" +
				"name='" + name + '\'' +
				'}';
	}

	public String getName() {
		return name;
	}

	/**
	 * @return how much of a given ware type this profession can produce.
	 */
	public int calculateOutput(Ware ware) {
		return 3;
	}

}

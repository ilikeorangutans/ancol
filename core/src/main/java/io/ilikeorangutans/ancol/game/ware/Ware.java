package io.ilikeorangutans.ancol.game.ware;

/**
 *
 */
public class Ware {
	private final String name;

	public Ware(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Ware ware = (Ware) o;

		if (!name.equals(ware.name)) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}
}

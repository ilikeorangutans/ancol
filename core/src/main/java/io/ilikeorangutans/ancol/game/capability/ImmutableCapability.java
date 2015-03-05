package io.ilikeorangutans.ancol.game.capability;

/**
 *
 */
public class ImmutableCapability implements Capability {

	private final String name;

	public ImmutableCapability(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ImmutableCapability that = (ImmutableCapability) o;

		if (!name.equals(that.name)) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override

	public String toString() {
		return "ImmutableCapability{" +
				"name='" + name + '\'' +
				'}';
	}
}

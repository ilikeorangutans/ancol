package io.ilikeorangutans.ancol.game.ware;

import io.ilikeorangutans.ancol.game.cargo.Transportable;

/**
 *
 */
public class Ware implements Transportable {

	private final int id;
	private final WareType wareType;
	private final String name;
	private boolean notStorable;
	private boolean harvested;
	private boolean manufactured;

	public Ware(int id, WareType wareType, String name, boolean storable) {
		this.id = id;
		this.wareType = wareType;
		this.notStorable = !storable;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public boolean isHarvested() {
		return harvested;
	}

	public boolean isManufactured() {
		return manufactured;
	}

	public WareType getWareType() {
		return wareType;
	}

	public boolean isStorable() {
		return !notStorable;
	}

	@Override
	public String toString() {
		return "Ware{" +
				"wareType=" + wareType +
				", name='" + name + '\'' +
				", storable=" + !notStorable +
				'}';
	}

	public String getName() {
		return name;
	}

	@Override
	public int getRequiredSpace() {
		return 1;
	}

	@Override
	public String getDescription() {
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

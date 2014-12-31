package io.ilikeorangutans.ancol.game.colony.building;

import io.ilikeorangutans.ancol.game.ware.WareType;


/**
 *
 */
public enum Blueprint {

	TownHall(1, null, null, WareType.LibertyBells),
	LumberMill(3, null, WareType.Lumber, WareType.Hammers),
	CarpentersShop(1, LumberMill, WareType.Lumber, WareType.Hammers);

	private final int minPopulation;
	private final Blueprint upgradesTo;
	private final WareType consumes;
	private final WareType produces;

	Blueprint(int minPopulation, Blueprint upgradesTo, WareType consumes, WareType produces) {
		this.minPopulation = minPopulation;
		this.upgradesTo = upgradesTo;
		this.consumes = consumes;
		this.produces = produces;
	}

	public int getMinimumPopulation() {
		return minPopulation;
	}

	public WareType getConsumes() {
		return consumes;
	}

	public WareType getProduces() {
		return produces;
	}

	public Blueprint getUpgradesTo() {
		return upgradesTo;
	}

	public Building build() {
		int multBonus = 0;
		int addBonus = 0;

		switch (this) {
			case TownHall:
				addBonus = 1;
				break;
			case LumberMill:
				multBonus = 2;
				break;
		}

		ProductionBuilding building = new ProductionBuilding(this, addBonus, multBonus);

		return building;
	}

}

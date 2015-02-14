package io.ilikeorangutans.ancol.game.colony.building;

import io.ilikeorangutans.ancol.game.ware.Ware;

/**
 *
 */
public class ProductionBuilding implements Building {

	private int additiveBonus = 0;
	private float multiplicativeBonus = 1;

	private BuildingType buildingType;

	public ProductionBuilding(BuildingType buildingType, int additiveBonus, int multiplicativeBonus) {
		this.buildingType = buildingType;

		this.additiveBonus = additiveBonus;
		this.multiplicativeBonus = multiplicativeBonus;
	}


	@Override
	public int apply(int input) {

		if (additiveBonus > 0) {
			// TODO: implement. Don't know any building that does additive bonuses right now.
		}

		if (multiplicativeBonus != 1) {
			// TODO: need to check the rounding behaviour
			input = Math.round(multiplicativeBonus * input);
		}

		return input;
	}

	@Override
	public String getName() {
		return buildingType.getName();
	}

	@Override
	public Ware getOutput() {
		return null;
	}

	@Override
	public BuildingType getBuildingType() {
		return buildingType;
	}
}

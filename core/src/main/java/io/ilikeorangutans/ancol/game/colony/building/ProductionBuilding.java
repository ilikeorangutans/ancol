package io.ilikeorangutans.ancol.game.colony.building;

/**
 *
 */
public class ProductionBuilding implements Building {


	private final int additiveBonus;
	private final int multiplicativeBonus;
	private Blueprint blueprint;

	public ProductionBuilding(Blueprint blueprint, int additiveBonus, int multiplicativeBonus) {
		this.blueprint = blueprint;
		this.additiveBonus = additiveBonus;
		this.multiplicativeBonus = multiplicativeBonus;
	}

	@Override
	public int apply(int input) {
		return 0;
	}

	@Override
	public String getName() {
		return blueprint.name();
	}

	@Override
	public Blueprint getBlueprint() {
		return blueprint;
	}
}

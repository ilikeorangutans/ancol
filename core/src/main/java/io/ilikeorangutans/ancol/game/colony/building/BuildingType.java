package io.ilikeorangutans.ancol.game.colony.building;

import io.ilikeorangutans.ancol.game.ware.AvailableWares;
import io.ilikeorangutans.ancol.game.ware.Ware;

/**
 *
 */
public class BuildingType {

	/**
	 * used to load from JSON.
	 */
	private String upgrade;
	private String input;
	private Ware inputWare;
	private String name;
	private transient BuildingType upgradeBuildingType;
	private String output;
	private Ware outputWare;
	private boolean initial;
	private int costHammers;
	private int costTools;
	private int minPopulation;

	public String getUpgradeString() {
		return upgrade;
	}

	public String getOutputString() {
		return output;
	}

	public String getInputString() {
		return input;
	}

	public int getCostHammers() {
		return costHammers;
	}

	public int getCostTools() {
		return costTools;
	}

	public int getMinPopulation() {
		return minPopulation;
	}

	public boolean isInitial() {
		return initial;
	}

	public void setInitial(boolean initial) {
		this.initial = initial;
	}

	public Ware getOutput() {
		return outputWare;
	}

	public void setOutput(Ware outputWare) {
		this.outputWare = outputWare;
	}

	public BuildingType getUpgrade() {
		return upgradeBuildingType;
	}

	public void setUpgrade(BuildingType upgrade) {
		this.upgradeBuildingType = upgrade;
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Ware getInput() {
		return inputWare;
	}

	public void setInput(Ware ware) {
		inputWare = ware;
	}

	@Override
	public String toString() {
		return "BuildingType{" +
				"name='" + name + '\'' +
				", upgrade=" + upgrade +
				", outputWare=" + outputWare +
				", initial=" + initial +
				", costHammers=" + costHammers +
				", costTools=" + costTools +
				", minPopulation=" + minPopulation +
				'}';
	}
	
	public void postProcess(AvailableBuildings availableBuildings, AvailableWares wares) {
		outputWare = wares.findByName(output);
		upgradeBuildingType = availableBuildings.findByName(upgrade);
		inputWare = wares.findByName(input);
	}
}

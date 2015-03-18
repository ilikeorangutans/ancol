package io.ilikeorangutans.ancol.game.colony.building;

import io.ilikeorangutans.ancol.game.ware.AvailableWares;
import io.ilikeorangutans.ancol.game.ware.Ware;

import static com.google.common.base.Strings.isNullOrEmpty;

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
	private int workplaces;

	public int getWorkplaces() {
		return workplaces;
	}

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
				"upgrade='" + upgrade + '\'' +
				", input='" + input + '\'' +
				", inputWare=" + inputWare +
				", name='" + name + '\'' +
				", upgradeBuildingType=" + upgradeBuildingType +
				", output='" + output + '\'' +
				", outputWare=" + outputWare +
				", initial=" + initial +
				", costHammers=" + costHammers +
				", costTools=" + costTools +
				", minPopulation=" + minPopulation +
				", workplaces=" + workplaces +
				'}';
	}

	public void postProcess(AvailableBuildings availableBuildings, AvailableWares wares) {
		if (!isNullOrEmpty(output)) outputWare = wares.findByName(output);
		if (!isNullOrEmpty(upgrade)) upgradeBuildingType = availableBuildings.findByName(upgrade);
		if (!isNullOrEmpty(input)) inputWare = wares.findByName(input);
	}
}

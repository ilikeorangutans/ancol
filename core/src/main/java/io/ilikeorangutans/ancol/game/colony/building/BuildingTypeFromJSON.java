package io.ilikeorangutans.ancol.game.colony.building;

import io.ilikeorangutans.ancol.game.colonist.AvailableProfessions;
import io.ilikeorangutans.ancol.game.colonist.Job;
import io.ilikeorangutans.ancol.game.ware.AvailableWares;
import io.ilikeorangutans.ancol.game.ware.Ware;

import static com.google.common.base.Strings.isNullOrEmpty;

/**
 *
 */
public class BuildingTypeFromJSON implements BuildingType {

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
	private Job job;

	@Override
	public int getWorkplaces() {
		return workplaces;
	}

	@Override
	public int getCostHammers() {
		return costHammers;
	}

	@Override
	public int getCostTools() {
		return costTools;
	}

	@Override
	public int getMinPopulation() {
		return minPopulation;
	}

	@Override
	public boolean isInitial() {
		return initial;
	}

	@Override
	public Ware getOutput() {
		return outputWare;
	}

	public void setOutput(Ware outputWare) {
		this.outputWare = outputWare;
	}

	@Override
	public BuildingType getUpgrade() {
		return upgradeBuildingType;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Ware getInput() {
		return inputWare;
	}

	@Override
	public Job getJob() {
		return job;
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

	public void postProcess(AvailableBuildings availableBuildings, AvailableWares wares, AvailableProfessions professions) {
		if (!isNullOrEmpty(output)) outputWare = wares.findByName(output);
		if (!isNullOrEmpty(upgrade)) upgradeBuildingType = availableBuildings.findByName(upgrade);
		if (!isNullOrEmpty(input)) inputWare = wares.findByName(input);

		if (outputWare != null) {
			job = professions.findJobThatProduces(outputWare);
		}
	}
}

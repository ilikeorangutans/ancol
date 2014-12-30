package io.ilikeorangutans.ancol.game.production;

import io.ilikeorangutans.ancol.game.production.worker.Worker;
import io.ilikeorangutans.ancol.game.ware.WareType;
import io.ilikeorangutans.ancol.game.ware.Wares;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Production {

	private final List<Worker> workers = new ArrayList<Worker>();

	private final WareType output;
	private final WareType input;

	Production(WareType input, WareType output, List<Worker> workers) {
		this.output = output;
		this.input = input;
		this.workers.addAll(workers);
	}

	public void addWorker(Worker worker) {
		workers.add(worker);
	}

	public WareType getOutput() {
		return output;
	}

	public boolean requirementsFulfilled() {
		return true;
	}

	/**
	 * @return The wares required to fulfill this production.
	 */
	public WareType getInput() {
		return input;
	}

	public int calculateConsumption() {
		if (!requiresInput())
			return 0;

		return calculateRawOutput();
	}

	/**
	 * Calculates the production output excluding output-only bonuses.
	 *
	 * @return
	 */
	private int calculateRawOutput() {
		int sum = 0;

		for (Worker worker : workers) {
			// TODO: add per worker bonuses here (e.g. improved production facilities or based on founding fathers)
			sum += worker.getOutput(output);
		}

		return sum;
	}

	public int calculateMaxOutput() {
		// TODO: apply bonuses
		return calculateRawOutput();
	}

	@Override
	public String toString() {
		return "Production{" +
				"workers=" + workers +
				", output=" + output +
				", input=" + input +
				'}';
	}

	public void produce(Wares wares) {
		int rawMaterialAvailable = 0;
		if (requiresInput())
			rawMaterialAvailable = wares.retrieve(input, calculateConsumption());

		int produced = calculateEffectiveOutput(rawMaterialAvailable);

		wares.store(output, produced);
	}

	/**
	 * @param rawMaterialAvailable
	 * @return the number of produced goods including all bonuses.
	 */
	private int calculateEffectiveOutput(int rawMaterialAvailable) {
		int rawOutput = calculateRawOutput();

		if (requiresInput()) {
			rawOutput = Math.min(rawOutput, rawMaterialAvailable);
		}

		// TODO: add output bonuses here (e.g. factories which add 50% output)
		return rawOutput;

	}

	public boolean requiresInput() {
		return input != null;
	}
}

package io.ilikeorangutans.ancol.game.production;

import io.ilikeorangutans.ancol.game.production.worker.Worker;
import io.ilikeorangutans.ancol.game.ware.Ware;
import io.ilikeorangutans.ancol.game.ware.Wares;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Production {

	private final List<Worker> workers = new ArrayList<Worker>();
	/**
	 * Holds a list of modifiers that will be applied on a per worker basis.
	 */
	private final List<Modifier> modifiers = new ArrayList<Modifier>();
	private final Ware output;
	private final Ware input;
	private final Workplace workplace;

	Production(Ware input, Ware output, List<Worker> workers, Workplace workplace) {
		this.output = output;
		this.input = input;
		this.workplace = workplace;
		this.workers.addAll(workers);
	}

	public void addWorker(Worker worker) {
		workers.add(worker);
	}

	public void addModifier(Modifier modifier) {
		modifiers.add(modifier);
	}

	public Ware getOutput() {
		return output;
	}

	public boolean requirementsFulfilled() {
		return true;
	}

	/**
	 * @return The wares required to fulfill this production.
	 */
	public Ware getInput() {
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
			sum += worker.calculateOutput(output);
		}

		return sum;
	}

	int calculateMaxOutput() {
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
	 * @param rawMaterialAvailable how much of the required raw material is available.
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

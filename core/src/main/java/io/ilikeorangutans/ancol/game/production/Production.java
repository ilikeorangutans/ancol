package io.ilikeorangutans.ancol.game.production;

import io.ilikeorangutans.ancol.game.production.chain.Link;
import io.ilikeorangutans.ancol.game.production.worker.Worker;
import io.ilikeorangutans.ancol.game.ware.Ware;
import io.ilikeorangutans.ancol.game.ware.Wares;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Production implements Link {

	/**
	 * TODO: this will be a single field instead of a collection
	 */
	private final Worker worker;
	/**
	 * Holds a list of modifiers that will be applied on a per worker basis.
	 */
	private final List<Modifier> modifiers = new ArrayList<Modifier>();
	private final Ware output;
	private final Ware input;
	private final Workplace workplace;

	public Production(Ware input, Ware output, Worker worker, Workplace workplace) {
		this.output = output;
		this.input = input;
		this.workplace = workplace;
		this.worker = worker;
	}

	public Workplace getWorkplace() {
		return workplace;
	}

	public void addModifier(Modifier modifier) {
		modifiers.add(modifier);
	}

	@Override
	public Ware getOutput() {
		return output;
	}

	public boolean requirementsFulfilled() {
		return true;
	}

	/**
	 * @return The wares required to fulfill this production.
	 */
	@Override
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

		// TODO: add per worker bonuses here (e.g. improved production facilities or based on founding fathers)
		sum += worker.calculateOutput(output);

		return sum;
	}

	int calculateMaxOutput() {
		// TODO: apply bonuses
		return calculateRawOutput();
	}

	@Override
	public String toString() {
		return "Production{" +
				"worker=" + worker +
				", output=" + output +
				", input=" + input +
				'}';
	}

	@Override
	public void produce(Wares wares) {
		int rawMaterialAvailable = 0;
		if (requiresInput())
			rawMaterialAvailable = wares.retrieve(input, calculateConsumption());

		int produced = calculateEffectiveOutput(rawMaterialAvailable);

		if (hasOutput())
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

	@Override
	public boolean requiresInput() {
		return input != null;
	}

	public boolean hasOutput() {
		return output != null;
	}

	public boolean employsWorker(Worker w) {
		return worker.equals(w);
	}

	public Worker getWorker() {
		return worker;
	}
}

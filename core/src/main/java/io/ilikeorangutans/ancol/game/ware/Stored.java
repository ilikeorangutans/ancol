package io.ilikeorangutans.ancol.game.ware;

import java.util.Observable;

/**
 *
 */
public class Stored extends Observable {

	private final Ware ware;

	private int amount;

	public Stored(Ware ware) {
		this(ware, 0);
	}

	public Stored(Ware ware, int amount) {
		this.ware = ware;
		this.amount = amount;
	}

	public Ware getWare() {
		return ware;
	}

	public int getAmount() {
		return amount;
	}

	public void store(int amount) {
		this.amount += amount;
	}

	@Override
	public String toString() {
		return "Stored{" +
				"ware=" + ware +
				", amount=" + amount +
				'}';
	}
}

package io.ilikeorangutans.ancol.game.ware;

/**
 *
 */
public class Stored {

	private final WareType ware;

	private int amount;

	public Stored(WareType ware) {
		this(ware, 0);
	}

	public Stored(WareType ware, int amount) {
		this.ware = ware;
		this.amount = amount;
	}

	public WareType getWare() {
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

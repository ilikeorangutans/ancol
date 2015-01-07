package io.ilikeorangutans.ancol.game.actionpoint;

/**
 *
 */
public class ActionPoints {

	private final int maxPoints;

	private int availablePoints;

	public ActionPoints(int maxPoints) {
		this.maxPoints = maxPoints;
	}

	public int getMaxPoints() {
		return maxPoints;
	}

	public int getAvailablePoints() {
		return availablePoints;
	}

	/**
	 * Refills the action points.
	 */
	public void replenish() {
		availablePoints = maxPoints;
	}

	/**
	 * Checks whether there are enough points for the given action.
	 *
	 * @param forAction number of action points requested.
	 * @return true if enough action points are available.
	 */
	public boolean hasEnough(int forAction) {
		return availablePoints >= forAction;
	}

	/**
	 * Consumes the given amount of action points.
	 *
	 * @param amount
	 * @return the number of action points consumed.
	 */
	public int consume(int amount) {
		if (!hasEnough(amount))
			throw new IllegalArgumentException("Cannot consume " + amount + " action points, only " + availablePoints + " available");

		availablePoints = availablePoints - amount;

		return amount;
	}
}

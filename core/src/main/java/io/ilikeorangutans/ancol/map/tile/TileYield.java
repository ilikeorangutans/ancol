package io.ilikeorangutans.ancol.map.tile;

import io.ilikeorangutans.ancol.game.ware.AvailableWares;
import io.ilikeorangutans.ancol.game.ware.Ware;

/**
 * Describes the output of a specific ware type of a tile.
 */
public class TileYield {

	private int amount;
	private String ware;
	private Ware wareObj;

	@Override
	public String toString() {
		return "TileYield{" +
				"ware=" + wareObj +
				", amount=" + amount +
				'}';
	}

	public Ware getWare() {
		return wareObj;
	}

	public int getAmount() {
		return amount;
	}

	public void postProcess(AvailableWares wares) {
		wareObj = wares.findByName(ware);
	}
}

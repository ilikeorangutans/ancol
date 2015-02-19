package io.ilikeorangutans.ancol.map.tile;

import io.ilikeorangutans.ancol.game.ware.AvailableWares;
import io.ilikeorangutans.ancol.game.ware.Ware;

/**
 *
 */
public class TileProduction {
	private int amount;

	private String ware;
	private Ware wareObj;

	@Override
	public String toString() {
		return "TileProduction{" +
				"ware=" + wareObj +
				", amount=" + amount +
				'}';
	}

	public Ware getWare() {
		return wareObj;
	}

	public void setWare(Ware wareObj) {
		this.wareObj = wareObj;
	}

	public int getAmount() {
		return amount;
	}

	public String getWareString() {
		return ware;
	}

	public void postProcess(AvailableWares wares) {
		wareObj = wares.findByName(ware);
	}
}

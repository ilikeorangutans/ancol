package io.ilikeorangutans.ancol.game.ware;


/**
 *
 */
public class Warehouse extends SimpleWares {

	private int maxPerWare = 100;

	public Warehouse() {
		for (WareType type : WareType.getStorableTypes()) {
			store(type, 0);
		}
	}

	public void setMaxPerWare(int maxPerWare) {
		this.maxPerWare = maxPerWare;
	}

}

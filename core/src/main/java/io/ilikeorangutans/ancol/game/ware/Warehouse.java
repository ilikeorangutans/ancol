package io.ilikeorangutans.ancol.game.ware;


import java.util.List;

/**
 *
 */
public class Warehouse extends SimpleWares {

	private int maxPerWare = 100;

	public Warehouse(List<Ware> wares) {
		for (Ware type : wares) {
			store(type, 0);
		}
	}

	public void setMaxPerWare(int maxPerWare) {
		this.maxPerWare = maxPerWare;
	}

}

package io.ilikeorangutans.ancol.game.ware;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class WaresRepository {

	private List<Ware> wares = new ArrayList<Ware>();

	public WaresRepository() {
		this.wares.add(new Ware("Lumber"));
		this.wares.add(new Ware("Corn"));
	}

	public List<Ware> getWares() {
		return wares;
	}

}

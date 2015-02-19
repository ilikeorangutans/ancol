package io.ilikeorangutans.ancol.map.tile;

import io.ilikeorangutans.ancol.game.ware.AvailableWares;

import java.util.Collections;
import java.util.List;

/**
 *
 */
public class TileType {

	private final String name;
	private final int id;
	private Role role;
	private boolean settlements;
	private List<TileProduction> produces;

	public TileType(String name, int id) {
		this.name = name;
		this.id = id;
	}

	@Override
	public String toString() {
		return "TileType{" +
				"name='" + name + '\'' +
				", id=" + id +
				", role=" + role +
				", settlements=" + settlements +
				", produces=" + produces +
				'}';
	}

	public List<TileProduction> getProduces() {
		return produces;
	}

	/**
	 * Returns true if settlements can be founded on this tile.
	 *
	 * @return
	 */
	public boolean isSettlements() {
		return settlements;
	}

	public Role getRole() {
		return role;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public void postProduce(AvailableWares wares) {
		if (produces == null) {
			produces = Collections.emptyList();
			return;
		}

		for (TileProduction production : produces) {
			production.postProcess(wares);
		}
	}

	/**
	 * What role does this tile have.
	 */
	public enum Role {
		Unexplored, Land, Ocean, Lake, TradeRoute
	}

}

package io.ilikeorangutans.ancol.map.tile;

import io.ilikeorangutans.ancol.game.ware.AvailableWares;
import io.ilikeorangutans.ancol.game.ware.Ware;

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
	private List<TileYield> yield;

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
				", yield=" + yield +
				'}';
	}

	public List<TileYield> getYield() {
		return yield;
	}

	/**
	 * Returns true if settlements can be founded on this tile.
	 *
	 * @return
	 */
	public boolean canBeSettled() {
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
		if (yield == null) {
			yield = Collections.emptyList();
			return;
		}

		for (TileYield tileYield : yield) {
			tileYield.postProcess(wares);
		}
	}

	public boolean produces(Ware ware) {
		for (TileYield tileYield : yield) {
			if (tileYield.getWare().equals(ware))
				return true;
		}
		return false;
	}

	/**
	 * What role does this tile have.
	 */
	public enum Role {
		Unexplored, Land, Ocean, Lake, TradeRoute
	}

}

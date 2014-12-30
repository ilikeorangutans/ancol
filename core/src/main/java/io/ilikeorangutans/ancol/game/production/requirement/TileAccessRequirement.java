package io.ilikeorangutans.ancol.game.production.requirement;

import io.ilikeorangutans.ancol.map.tile.Tile;

/**
 *
 */
public class TileAccessRequirement implements Requirement {

	private final Tile tile;

	public TileAccessRequirement(Tile tile) {
		this.tile = tile;
	}

	@Override
	public boolean isFulfilled() {
		// TODO: here we'd check the tile for access
		return true;
	}
}

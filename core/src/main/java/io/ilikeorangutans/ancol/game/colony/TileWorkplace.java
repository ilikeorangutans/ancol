package io.ilikeorangutans.ancol.game.colony;

import io.ilikeorangutans.ancol.game.production.Modifier;
import io.ilikeorangutans.ancol.game.production.Workplace;
import io.ilikeorangutans.ancol.map.tile.GameTile;
import io.ilikeorangutans.ancol.map.tile.Tile;

/**
 *
 */
public class TileWorkplace implements Workplace {
	private final GameTile tile;

	public TileWorkplace(GameTile tile) {
		this.tile = tile;
	}

	@Override
	public Modifier getModifier() {
		return new Modifier() {
			@Override
			public int apply(int input) {
				System.out.println("TileWorkplace.apply TODO: properly calculate modifier");
				return input;
			}
		};
	}

	@Override
	public String toString() {
		return "TileWorkplace{" +
				"tile=" + tile +
				'}';
	}

	public Tile getTile() {
		return tile;
	}
}

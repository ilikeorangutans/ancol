package io.ilikeorangutans.ancol.game.colony;

import io.ilikeorangutans.ancol.game.production.Modifier;
import io.ilikeorangutans.ancol.game.production.Workplace;
import io.ilikeorangutans.ancol.map.tile.Tile;
import io.ilikeorangutans.ecs.Entity;

/**
 *
 */
public class TileWorkplace implements Workplace {
	private final Tile tile;
	private final Entity entity;
	private final Entity colonist;

	public TileWorkplace(Tile tile, Entity entity, Entity colonist) {
		this.tile = tile;
		this.entity = entity;
		this.colonist = colonist;
	}

	@Override
	public Modifier getModifier() {
		return null;
	}

	public Entity getColonist() {
		return colonist;
	}

	public Tile getTile() {

		return tile;
	}
}

package io.ilikeorangutans.ancol.game.colony;

import io.ilikeorangutans.ancol.game.colonist.AvailableProfessions;
import io.ilikeorangutans.ancol.game.colonist.Job;
import io.ilikeorangutans.ancol.game.production.AbstractWorkplace;
import io.ilikeorangutans.ancol.game.production.Modifier;
import io.ilikeorangutans.ancol.map.tile.GameTile;
import io.ilikeorangutans.ancol.map.tile.Tile;
import io.ilikeorangutans.ancol.map.tile.TileYield;

import java.util.HashSet;
import java.util.Set;

/**
 *
 */
public class TileWorkplace extends AbstractWorkplace {
	private final AvailableProfessions professions;
	private final GameTile tile;

	public TileWorkplace(AvailableProfessions professions, GameTile tile) {
		super(1);
		this.professions = professions;
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
	public Set<Job> getAvailableJobs() {
		Set<Job> jobs = new HashSet<Job>();
		for (TileYield yield : getTile().getType().getYield()) {
			jobs.add(professions.findJobThatProduces(yield.getWare()));
		}

		return jobs;
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

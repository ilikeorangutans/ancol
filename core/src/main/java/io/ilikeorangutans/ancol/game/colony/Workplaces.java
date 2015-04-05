package io.ilikeorangutans.ancol.game.colony;

import io.ilikeorangutans.ancol.Point;
import io.ilikeorangutans.ancol.game.colonist.AvailableProfessions;
import io.ilikeorangutans.ancol.game.colony.building.Building;
import io.ilikeorangutans.ancol.game.colony.building.ColonyBuildings;
import io.ilikeorangutans.ancol.game.production.Workplace;
import io.ilikeorangutans.ancol.game.ware.Ware;
import io.ilikeorangutans.ancol.map.surrounding.Surroundings;
import io.ilikeorangutans.ancol.map.tile.GameTile;
import io.ilikeorangutans.ancol.map.tile.TileYield;

import java.util.*;

/**
 *
 */
public class Workplaces {
	private final List<Workplace> workplaces = new ArrayList<Workplace>();
	private final AvailableProfessions professions;
	private ColonyBuildings buildings;
	private Surroundings surroundings;
	private TileWorkplace[] tileWorkplaces = new TileWorkplace[9];

	public Workplaces(AvailableProfessions professions, ColonyBuildings buildings, Surroundings surroundings) {
		this.professions = professions;
		this.buildings = buildings;
		this.surroundings = surroundings;
	}

	/**
	 * Returns all wares that can be produced with the available workplaces.
	 * <p/>
	 * TODO: does not take already occupied tiles or buildings into account
	 *
	 * @return
	 */
	public Collection<Ware> getProducibleWares() {
		Set<Ware> result = new HashSet<Ware>();

		for (GameTile tile : surroundings.getAllWithoutCenter()) {
			for (TileYield tileYield : tile.getType().getYield()) {
				result.add(tileYield.getWare());
			}
		}

		for (Building building : buildings) {
			if (!building.hasAvailableWorkplaces())
				continue;

			if (building.getOutput() != null)
				result.add(building.getOutput());
		}

		return result;
	}

	/**
	 * Returns a workplace instance that produces the given ware.
	 *
	 * @param ware
	 * @return
	 */
	public Workplace getWorkplaceFor(Ware ware) {
		if (ware.isManufactured()) {
			return buildings.findByOutput(ware);
		} else {
			GameTile tile = findTileThatProduces(ware);
			return getForTile(tile);
		}
	}

	private GameTile findTileThatProduces(Ware ware) {
		for (GameTile tile : surroundings.getAllWithoutCenter()) {
			if (tile.getType().produces(ware)) {
				// TODO: don't pick first but "best" tile.
				return tile;
			}
		}

		throw new IllegalArgumentException("Cannot find tile that produces " + ware.getName());
	}

	public TileWorkplace getForTile(GameTile tile) {
		int index = getTileIndex(tile.getPoint());
		if (tileWorkplaces[index] != null)
			return tileWorkplaces[index];

		TileWorkplace tileWorkplace = new TileWorkplace(professions, tile);
		tileWorkplaces[index] = tileWorkplace;

		return tileWorkplace;
	}

	/**
	 * Translates the given point to an index for the surrounding tiles array.
	 *
	 * @param p
	 * @return
	 */
	private int getTileIndex(Point p) {
		Point start = surroundings.getTile(Surroundings.Selector.NW).getPoint();
		int diff = p.y - start.y;
		int offset = diff * 3;
		int index = p.x - start.x + offset;

		return index;
	}

}

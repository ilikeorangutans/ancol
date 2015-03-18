package io.ilikeorangutans.ancol.game.colony;

import io.ilikeorangutans.ancol.Point;
import io.ilikeorangutans.ancol.game.colony.building.Building;
import io.ilikeorangutans.ancol.game.colony.building.ColonyBuildings;
import io.ilikeorangutans.ancol.game.player.Player;
import io.ilikeorangutans.ancol.game.player.PlayerOwnedComponent;
import io.ilikeorangutans.ancol.game.production.Workplace;
import io.ilikeorangutans.ancol.game.ware.Ware;
import io.ilikeorangutans.ancol.map.PositionComponent;
import io.ilikeorangutans.ancol.map.surrounding.Surroundings;
import io.ilikeorangutans.ancol.map.tile.GameTile;
import io.ilikeorangutans.ancol.map.tile.TileYield;
import io.ilikeorangutans.ecs.Entity;
import io.ilikeorangutans.ecs.EntityFactory;

import java.util.*;

/**
 *
 */
public class Workplaces {
	private final List<Workplace> workplaces = new ArrayList<Workplace>();
	private EntityFactory entityFactory;
	private ColonyBuildings buildings;
	private Surroundings surroundings;
	private Player player;
	private TileWorkplace[] tileWorkplaces = new TileWorkplace[9];

	public Workplaces(EntityFactory entityFactory, ColonyBuildings buildings, Surroundings surroundings, Player player) {
		this.entityFactory = entityFactory;
		this.buildings = buildings;
		this.surroundings = surroundings;
		this.player = player;
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
		// TODO: actually check what we are producing (might not be a tile based resource)
		GameTile tile = findTileThatProduces(ware);
		return getForTile(tile);
	}

	private GameTile findTileThatProduces(Ware ware) {
		List<GameTile> candidates = new ArrayList<GameTile>(10);
		for (GameTile tile : surroundings.getAllWithoutCenter()) {
			if (tile.getType().produces(ware)) {
				// TODO: don't pick first but "best" tile.
				return tile;
			}
		}

		throw new IllegalArgumentException("Cannot find tile that produces " + ware.getName());
	}

	/**
	 * Finds the best workplace to produce the given ware
	 *
	 * @return
	 */
	private Workplace createWorkplaceForTile(Entity colonist, GameTile tile) {
		// Create an entity to record that we are working this tile
		Entity entity = entityFactory.create(
				new PlayerOwnedComponent(player),
				new PositionComponent(tile.getPoint()),
				new WorkedByPlayerComponent(player));

		TileWorkplace tileWorkplace = new TileWorkplace(tile);
		return tileWorkplace;
	}

	public TileWorkplace getForTile(GameTile tile) {
		int index = getTileIndex(tile.getPoint());
		if (tileWorkplaces[index] != null)
			return tileWorkplaces[index];

		TileWorkplace tileWorkplace = new TileWorkplace(tile);
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

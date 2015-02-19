package io.ilikeorangutans.ancol.map.surrounding;

import io.ilikeorangutans.ancol.Point;
import io.ilikeorangutans.ancol.map.GameMap;
import io.ilikeorangutans.ancol.map.PositionComponent;
import io.ilikeorangutans.ancol.map.tile.GameTile;
import io.ilikeorangutans.ecs.ComponentType;
import io.ilikeorangutans.ecs.Entities;
import io.ilikeorangutans.ecs.Entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 */
public class PointSurroundings implements Surroundings {

	private final GameMap map;
	private final Point point;
	private final Entities entities;

	public PointSurroundings(Point location, GameMap map, Entities entities) {
		this.map = map;
		this.point = location;
		this.entities = entities;
	}

	@Override
	public GameTileImpl getTile(Selector where) {
		return new GameTileImpl(where.apply(point), map.getTileAt(where.apply(point)), map);
	}

	@Override
	public List<Entity> getEntities(Selector where) {
		List<Entity> ents = entities.getEntityByType(ComponentType.fromClass(PositionComponent.class));
		List<Entity> result = new ArrayList<Entity>();
		Point location = where.apply(point);

		for (Entity ent : ents) {
			PositionComponent pc = ent.getComponent(PositionComponent.class);
			if (pc.getPoint().equals(location)) {
				result.add(ent);
			}
		}

		return result;
	}

	@Override
	public List<GameTile> getAllWithoutCenter() {
		List<GameTile> result = new ArrayList<GameTile>();

		for (Selector selector : Selector.values()) {
			if (selector == Selector.Center)
				continue;

			Point p = selector.apply(point);
			result.add(new GameTileImpl(p, map.getTileAt(p), map));
		}

		return result;
	}

	@Override
	public Iterator<GameTile> iterator() {

		List<GameTile> result = new ArrayList<GameTile>();

		for (Selector selector : Selector.values()) {
			Point p = selector.apply(point);
			result.add(new GameTileImpl(p, map.getTileAt(p), map));
		}

		return result.iterator();
	}
}

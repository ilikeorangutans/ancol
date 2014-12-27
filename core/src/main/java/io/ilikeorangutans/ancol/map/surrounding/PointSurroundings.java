package io.ilikeorangutans.ancol.map.surrounding;

import io.ilikeorangutans.ancol.Point;
import io.ilikeorangutans.ancol.map.Map;
import io.ilikeorangutans.ancol.map.PositionComponent;
import io.ilikeorangutans.ancol.map.tile.Tile;
import io.ilikeorangutans.ecs.ComponentType;
import io.ilikeorangutans.ecs.Entities;
import io.ilikeorangutans.ecs.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class PointSurroundings implements Surroundings {

	private final Map map;
	private final Point point;
	private final Entities entities;

	public PointSurroundings(Point location, Map map, Entities entities) {
		this.map = map;
		this.point = location;
		this.entities = entities;
	}

	@Override
	public Tile getTile(Selector where) {
		return map.getTileAt(where.apply(point));
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
}

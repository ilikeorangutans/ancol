package io.ilikeorangutans.ancol.move;

import io.ilikeorangutans.ancol.Point;
import io.ilikeorangutans.ancol.game.ability.Abilities;
import io.ilikeorangutans.ancol.game.ability.Ability;
import io.ilikeorangutans.ancol.game.ability.ImmutableAbility;
import io.ilikeorangutans.ancol.map.GameMap;
import io.ilikeorangutans.ancol.map.tile.GameTile;
import io.ilikeorangutans.ancol.map.tile.TileType;
import io.ilikeorangutans.ancol.path.Movable;
import io.ilikeorangutans.ancol.path.Path;
import io.ilikeorangutans.ecs.Component;
import io.ilikeorangutans.ecs.ComponentType;

/**
 *
 */
public class MovableComponent implements Component, Movable {

	public static final Ability TRAVERSE_WATER = new ImmutableAbility("traverse-water");
	public static final Ability TRAVERSE_LAND = new ImmutableAbility("traverse-land");

	private static final ComponentType COMPONENT_TYPE = ComponentType.fromClass(MovableComponent.class);
	private final GameMap map;
	private final Abilities abilities;
	private Path path;

	public MovableComponent(GameMap map, Abilities abilities) {
		this.map = map;
		this.abilities = abilities;
	}

	public static ComponentType getComponentType() {
		return COMPONENT_TYPE;
	}

	public Point getDestination() {
		return path == null || path.isEmpty() ? null : path.getDestination();
	}

	@Override
	public ComponentType getType() {
		return COMPONENT_TYPE;
	}

	public boolean hasDestination() {
		return getDestination() != null;
	}

	public Path getPath() {
		return path;
	}

	public void setPath(Path path) {
		this.path = path;
	}

	@Override
	public boolean canAccess(Point p) {
		GameTile tile = map.getTileAt(p);

		TileType.Role role = tile.getType().getRole();

		boolean unexplored = role == TileType.Role.Unexplored;
		if (unexplored)
			// All units can "enter" unexplored terrain for path finding purposes. They might find unaccessible terrain
			// though.
			return true;


		// TODO: Entities can
		// influence pathfinding and accessibility, for example a ship (with empty cargo holds) will allow a land unit
		// to "enter" a sea tile, and vice versa, a colony will allow a ship to enter a land tile.

		boolean land = role == TileType.Role.Land;
		Ability required = TRAVERSE_WATER;
		if (land) {
			required = TRAVERSE_LAND;
		}

		// TODO: check if tile has a colony; if so, check if we can enter it. if it's our own colony, we're fine. If
		// it's an enemy colony, we need a special capability.

		// TODO: check if another player's entity is on this tile.

		return abilities.has(required);
	}

	@Override
	public float getCost(Point p) {
		return 1;
	}

	@Deprecated
	public enum MovableType {
		Land,
		Sea
	}
}

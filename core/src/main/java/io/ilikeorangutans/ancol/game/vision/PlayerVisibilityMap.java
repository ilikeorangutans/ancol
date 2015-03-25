package io.ilikeorangutans.ancol.game.vision;

import io.ilikeorangutans.ancol.Point;
import io.ilikeorangutans.ancol.game.player.Player;
import io.ilikeorangutans.ancol.game.player.PlayerOwnedComponent;
import io.ilikeorangutans.ancol.map.GameMap;
import io.ilikeorangutans.ancol.map.Map;
import io.ilikeorangutans.ancol.map.PositionComponent;
import io.ilikeorangutans.ancol.map.surrounding.GameTileImpl;
import io.ilikeorangutans.ancol.map.surrounding.ImmutablePointSurroundings;
import io.ilikeorangutans.ancol.map.tile.GameTile;
import io.ilikeorangutans.ancol.map.tile.Tile;
import io.ilikeorangutans.ancol.map.tile.TileImpl;
import io.ilikeorangutans.ancol.map.tile.TileType;
import io.ilikeorangutans.ancol.move.MovedEvent;
import io.ilikeorangutans.bus.Subscribe;
import io.ilikeorangutans.ecs.ComponentType;
import io.ilikeorangutans.ecs.Entities;
import io.ilikeorangutans.ecs.Entity;
import io.ilikeorangutans.ecs.event.EntityCreatedEvent;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;

/**
 * Wrapper around a map that will check visibility of tiles.
 */
public class PlayerVisibilityMap implements GameMap {

	private final Map delegate;
	private final Player player;
	private final TileType unexplored;
	private final BitSet visibility;
	private final Entities entities;
	private TileImpl unexploredTile;

	public PlayerVisibilityMap(Map delegate, Player player, TileType unexplored, Entities entities) {
		this.delegate = delegate;
		this.player = player;
		this.unexplored = unexplored;
		this.entities = entities;
		visibility = new BitSet(getWidth() * getHeight());
		visibility.clear();

		unexploredTile = new TileImpl(unexplored);
	}

	@Override
	public int getWidth() {
		return delegate.getWidth();
	}

	@Override
	public int getHeight() {
		return delegate.getHeight();
	}

	@Override
	public Tile getTileAt(int x, int y) {
		if (!isVisible(x, y)) {
			return unexploredTile;
		}

		return delegate.getTileAt(x, y);
	}

	private boolean isVisible(int x, int y) {
		if (x < 0 || y < 0) {
			System.out.println("PlayerVisibilityMap.isVisible invalid index " + x + "/" + y );
			return false;
		}
		return visibility.get(y * getWidth() + x);
	}

	@Override
	public GameTile getTileAt(Point p) {
		return new GameTileImpl(p, getTileAt(p.x, p.y), this);
	}

	@Override
	public List<Entity> getEntitiesAt(Point p) {
		final boolean isUnexplored = getTileAt(p).getType().equals(unexplored);
		if (isUnexplored)
			return Collections.emptyList();

		// TODO: iterating over ALL entities with a position. Inefficient. :(

		List<Entity> ents = entities.getEntityByType(ComponentType.fromClass(PositionComponent.class));
		List<Entity> result = new ArrayList<Entity>();
		for (Entity e : ents) {
			boolean onPoint = e.getComponent(PositionComponent.class).getPoint().equals(p);
			if (onPoint)
				result.add(e);
		}
		return result;
	}


	@Subscribe
	public void onMoved(MovedEvent event) {
		updateVisibility(event.entity);
	}

	@Subscribe
	public void onEntityCreated(EntityCreatedEvent event) {
		updateVisibility(event.entity);
	}

	// TODO: Add a handler for entity changed owner

	public void updateVisibility(Entity entity) {
		if (!entity.hasComponent(ComponentType.fromClasses(PlayerOwnedComponent.class)))
			return;
		final boolean ownedByPlayer = entity.getComponent(PlayerOwnedComponent.class).getPlayer().equals(player);
		if (!ownedByPlayer)
			return;

		if (!entity.hasComponent(ComponentType.fromClasses(VisionComponent.class)))
			return;

		VisionComponent vc = entity.getComponent(VisionComponent.class);
		int radius = vc.getRadius();
		PositionComponent pc = entity.getComponent(PositionComponent.class);

		ImmutablePointSurroundings surroundings = new ImmutablePointSurroundings(pc.getPoint(), this, entities);
		vc.setSurroundings(surroundings);

		int xFrom = Math.max(0, pc.getX() - radius);
		int xTo = Math.min(getWidth(), xFrom + radius + radius + 1);
		int yFrom = Math.max(0, pc.getY() - radius);
		int yTo = Math.min(getHeight(), yFrom + radius + radius + 1);

		for (int y = yFrom; y < yTo; y++) {
			visibility.set(y * getWidth() + xFrom, y * getWidth() + xTo, true);
		}

	}
}

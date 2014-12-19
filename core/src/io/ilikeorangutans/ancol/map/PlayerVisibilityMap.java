package io.ilikeorangutans.ancol.map;

import io.ilikeorangutans.ancol.game.Player;
import io.ilikeorangutans.ancol.game.PlayerOwnedComponent;
import io.ilikeorangutans.ancol.game.vision.VisionComponent;
import io.ilikeorangutans.ancol.map.tile.Tile;
import io.ilikeorangutans.ancol.map.tile.TileType;
import io.ilikeorangutans.ancol.move.MovedEvent;
import io.ilikeorangutans.bus.Subscribe;
import io.ilikeorangutans.ecs.ComponentType;
import io.ilikeorangutans.ecs.Entity;
import io.ilikeorangutans.ecs.event.EntityCreatedEvent;

import java.util.BitSet;

/**
 * Wrapper around a map that will check visibility of tiles.
 */
public class PlayerVisibilityMap implements Map {

	private final Map delegate;
	private final Player player;

	private final BitSet visibility;
	private final Tile UNEXPLORED_TILE = new Tile(new TileType("unexplored", 0));

	public PlayerVisibilityMap(Map delegate, Player player) {
		this.delegate = delegate;
		this.player = player;
		visibility = new BitSet(getWidth() * getHeight());
		visibility.clear();
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
		boolean visible = visibility.get(y * getWidth() + x);

		if (!visible) {
			return UNEXPLORED_TILE;
		}

		return delegate.getTileAt(x, y);
	}

	@Subscribe
	public void onMoved(MovedEvent event) {
		updateVisibility(event.entity);
	}

	@Subscribe
	public void onEntityCreated(EntityCreatedEvent event) {
		updateVisibility(event.entity);
	}

	public void updateVisibility(Entity entity) {
		if (!entity.hasComponent(ComponentType.fromClasses(PlayerOwnedComponent.class)))
			return;
		final boolean ownedByPlayer = entity.getComponent(PlayerOwnedComponent.class).getPlayer().equals(player);
		if (!ownedByPlayer)
			return;


		if (!entity.hasComponent(ComponentType.fromClasses(VisionComponent.class)))
			throw new IllegalArgumentException("Cannot update visibility for " + entity + ", it does not have a VisionComponent.");

		VisionComponent vc = entity.getComponent(VisionComponent.class);
		int radius = vc.getRadius();
		PositionComponent pc = entity.getComponent(PositionComponent.class);


		int xFrom = Math.max(0, pc.getX() - radius);
		int xTo = Math.min(getWidth(), xFrom + radius + radius + 1);
		int yFrom = Math.max(0, pc.getY() - radius);
		int yTo = Math.min(getHeight(), yFrom + radius + radius + 1);

		for (int y = yFrom; y < yTo; y++) {
			visibility.set(y * getWidth() + xFrom, y * getWidth() + xTo, true);
		}

	}
}

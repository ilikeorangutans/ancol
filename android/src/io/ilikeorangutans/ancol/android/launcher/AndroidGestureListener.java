package io.ilikeorangutans.ancol.android.launcher;

import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import io.ilikeorangutans.ancol.Point;
import io.ilikeorangutans.ancol.input.action.AnColActions;
import io.ilikeorangutans.ancol.input.action.MoveAction;
import io.ilikeorangutans.ancol.map.PositionComponent;
import io.ilikeorangutans.ancol.map.viewport.ScreenToTile;
import io.ilikeorangutans.ancol.map.viewport.ScrollEvent;
import io.ilikeorangutans.ancol.select.EntitySelectedEvent;
import io.ilikeorangutans.ancol.select.SelectEvent;
import io.ilikeorangutans.bus.Emitter;
import io.ilikeorangutans.bus.EventBus;
import io.ilikeorangutans.bus.Subscribe;
import io.ilikeorangutans.ecs.Entity;

/**
 *
 */
public class AndroidGestureListener implements GestureDetector.GestureListener {

	public static final String TAG = AndroidGestureListener.class.getName();

	private final ScreenToTile screenToTile;
	private final AnColActions actions;
	private final Emitter emitter;
	private boolean draggingSelection = false;
	private boolean panning = false;
	private Entity selected;

	public AndroidGestureListener(Emitter emitter, ScreenToTile screenToTile, AnColActions actions) {
		this.emitter = emitter;

		((EventBus) emitter).subscribe(this); // UGH

		this.screenToTile = screenToTile;
		this.actions = actions;
	}

	@Subscribe
	public void onEntitySelected(EntitySelectedEvent event) {
		selected = event.entity;
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {

		final Point destination = screenToTile.screenToTile((int) x, (int) y);

		emitter.fire(new SelectEvent(destination.x, destination.y));

		return true;
	}

	@Override
	public boolean longPress(float x, float y) {
		final Point destination = screenToTile.screenToTile((int) x, (int) y);

		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		final Point destination = screenToTile.screenToTile((int) x, (int) y);

		boolean wasPanningBefore = panning;
		panning = true;

		boolean dragStartedOnSelectedEntity = !wasPanningBefore && selected != null && selected.getComponent(PositionComponent.class).getPoint().equals(destination);

		if (dragStartedOnSelectedEntity) {
			draggingSelection = true;
			return true;
		}

		if (draggingSelection)
			return true;

		emitter.fire(new ScrollEvent((int) -deltaX, (int) -deltaY));

		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		final Point destination = screenToTile.screenToTile((int) x, (int) y);

		if (draggingSelection) {
			MoveAction moveAction = actions.getMoveAction();
			moveAction.setDestination(destination);
			moveAction.perform();
		}
		draggingSelection = false;

		panning = false;
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
		return false;
	}
}

package io.ilikeorangutans.ancol.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import io.ilikeorangutans.ancol.Point;
import io.ilikeorangutans.ancol.game.cmd.IdleCommand;
import io.ilikeorangutans.ancol.game.cmd.event.CommandEvent;
import io.ilikeorangutans.ancol.game.event.SimulateEntityEvent;
import io.ilikeorangutans.ancol.input.action.AnColActions;
import io.ilikeorangutans.ancol.input.action.MoveAction;
import io.ilikeorangutans.ancol.map.ScreenToTile;
import io.ilikeorangutans.ancol.map.ScrollEvent;
import io.ilikeorangutans.ancol.path.PathFinder;
import io.ilikeorangutans.ancol.select.EntitySelectedEvent;
import io.ilikeorangutans.ancol.select.SelectEvent;
import io.ilikeorangutans.bus.EventBus;
import io.ilikeorangutans.bus.Subscribe;
import io.ilikeorangutans.ecs.Entity;

/**
 *
 */
public class AnColInputProcessor implements InputProcessor {

	private final EventBus bus;
	private final ScreenToTile screenToTile;
	private final AnColActions actions;
	private PathFinder pathFinder;
	private int lastX, lastY;
	private Entity selectedEntity;
	private boolean dragging = false;

	public AnColInputProcessor(EventBus bus, ScreenToTile screenToTile, PathFinder pathFinder, AnColActions actions) {
		this.bus = bus;
		this.screenToTile = screenToTile;
		this.pathFinder = pathFinder;


		this.actions = actions;
	}

	@Subscribe
	public void onSelectedEntity(EntitySelectedEvent event) {
		selectedEntity = event.entity;
	}

	@Override
	public boolean keyDown(int keycode) {

		switch (keycode) {
			case Input.Keys.LEFT:
				bus.fire(new ScrollEvent(-30, 0));
				break;
			case Input.Keys.RIGHT:
				bus.fire(new ScrollEvent(30, 0));
				break;
			case Input.Keys.UP:
				bus.fire(new ScrollEvent(0, -30));
				break;
			case Input.Keys.DOWN:
				bus.fire(new ScrollEvent(0, 30));
				break;
			case Input.Keys.B:
				actions.getBuildColonyAction().perform();
				break;
			case Input.Keys.P:
				actions.getImproveTileAction().perform();
				break;
			case Input.Keys.SPACE:
				bus.fire(new CommandEvent(new IdleCommand()));
				break;
			case Input.Keys.ENTER:
				bus.fire(new SimulateEntityEvent(selectedEntity));
				break;

		}

		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		if (character == 'q') {
			Gdx.app.exit();
			return true;
		}

		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		final boolean wasDragging = dragging;
		dragging = false;

		final Point destination = screenToTile.screenToTile(screenX, screenY);


		if (button == Input.Buttons.RIGHT && !wasDragging) {
			MoveAction moveAction = actions.getMoveAction();
			moveAction.setDestination(destination);
			moveAction.perform();
		} else if (button == Input.Buttons.LEFT) {
			bus.fire(new SelectEvent(destination.x, destination.y));
			return true;
		}

		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {

		dragging = true;

		if (Gdx.input.isButtonPressed(1) || Gdx.input.isTouched(1)) {
			bus.fire(new ScrollEvent(lastX - screenX, lastY - screenY));

			lastX = screenX;
			lastY = screenY;
			return true;
		}

		return false;
	}


	@Override
	public boolean mouseMoved(int screenX, int screenY) {

		lastX = screenX;
		lastY = screenY;

		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}

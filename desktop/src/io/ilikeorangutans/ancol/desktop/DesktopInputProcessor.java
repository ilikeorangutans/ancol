package io.ilikeorangutans.ancol.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import io.ilikeorangutans.ancol.Point;
import io.ilikeorangutans.ancol.RequiresScreenToMap;
import io.ilikeorangutans.ancol.map.ScreenToMap;
import io.ilikeorangutans.ancol.map.ScreenToTile;
import io.ilikeorangutans.ancol.map.ScrollEvent;
import io.ilikeorangutans.ancol.move.MoveEvent;
import io.ilikeorangutans.ancol.select.SelectEvent;
import io.ilikeorangutans.bus.EventBus;

/**
 * Created by jakob on 14-11-18.
 */
class DesktopInputProcessor implements InputProcessor, RequiresScreenToMap {
    private final EventBus bus;
    private int lastX, lastY;
    private ScreenToMap screenToMap;

    public DesktopInputProcessor(EventBus bus) {
        this.bus = bus;
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
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        if (character == 'q')
            Gdx.app.exit();
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Point p = ((ScreenToTile) screenToMap).screenToTile((int) screenX, (int) screenY);
        if (button == 0) {
            bus.fire(new SelectEvent(p.x, p.y));
        } else if (button == 1) {
            bus.fire(new MoveEvent(p));
        }

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

        if (Gdx.input.isButtonPressed(1)) {
            bus.fire(new ScrollEvent(lastX - screenX, lastY - screenY));

            lastX = screenX;
            lastY = screenY;
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

    @Override
    public void setScreenToMap(ScreenToMap screenToMap) {
        this.screenToMap = screenToMap;
    }
}

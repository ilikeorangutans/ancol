package io.ilikeorangutans.ancol.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import io.ilikeorangutans.ancol.AnCol;
import io.ilikeorangutans.ancol.map.ScrollEvent;
import io.ilikeorangutans.bus.EventBus;
import io.ilikeorangutans.bus.SimpleEventBus;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        final EventBus bus = new SimpleEventBus();
        InputProcessor ip = new InputProcessor() {
            private int lastX, lastY;

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
        };
        new LwjglApplication(new AnCol(bus, ip), config);
    }
}

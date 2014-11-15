package io.ilikeorangutans.ancol.android.launcher;

import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import io.ilikeorangutans.ancol.map.ScrollEvent;
import io.ilikeorangutans.bus.EventBus;

/**
 *
 */
public class AnColGestureListener implements GestureDetector.GestureListener {

    private static final String TAG = AnColGestureListener.class.getName();

    private EventBus bus;

    public AnColGestureListener(EventBus bus) {
        this.bus = bus;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {

        bus.fire(new ScrollEvent((int) -deltaX, (int) -deltaY));

        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
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

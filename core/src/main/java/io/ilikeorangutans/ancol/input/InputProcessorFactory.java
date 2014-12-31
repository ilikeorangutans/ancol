package io.ilikeorangutans.ancol.input;

import com.badlogic.gdx.InputProcessor;
import io.ilikeorangutans.ancol.input.action.AnColActions;
import io.ilikeorangutans.ancol.map.viewport.ScreenToTile;
import io.ilikeorangutans.bus.Emitter;

/**
 *
 */
public interface InputProcessorFactory {

	InputProcessor create(Emitter emitter, ScreenToTile screenToTile, AnColActions actions);
}

package io.ilikeorangutans.ancol.desktop;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import io.ilikeorangutans.ancol.AnCol;
import io.ilikeorangutans.ancol.input.DesktopInputProcessor;
import io.ilikeorangutans.ancol.input.InputProcessorFactory;
import io.ilikeorangutans.ancol.input.action.AnColActions;
import io.ilikeorangutans.ancol.map.viewport.ScreenToTile;
import io.ilikeorangutans.bus.Emitter;

public class DesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1280;
		config.height = 1024;

		new LwjglApplication(new AnCol(new InputProcessorFactory() {

			@Override
			public InputProcessor create(Emitter emitter, ScreenToTile screenToTile, AnColActions actions) {
				return new DesktopInputProcessor(emitter, screenToTile, actions);
			}
		}), config);
	}

}

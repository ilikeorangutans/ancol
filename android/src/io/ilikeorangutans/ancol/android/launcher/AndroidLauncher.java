package io.ilikeorangutans.ancol.android.launcher;

import android.os.Bundle;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.input.GestureDetector;
import io.ilikeorangutans.ancol.AnCol;
import io.ilikeorangutans.ancol.input.InputProcessorFactory;
import io.ilikeorangutans.ancol.input.action.AnColActions;
import io.ilikeorangutans.ancol.map.viewport.ScreenToTile;
import io.ilikeorangutans.bus.Emitter;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		config.useAccelerometer = false;
		config.useCompass = false;

		initialize(new AnCol(new InputProcessorFactory() {
			@Override
			public InputProcessor create(Emitter emitter, ScreenToTile screenToTile, AnColActions actions) {
				return new GestureDetector(new AndroidGestureListener(emitter, screenToTile, actions));
			}
		}), config);
	}

}

package io.ilikeorangutans.ancol.android.launcher;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.input.GestureDetector;
import io.ilikeorangutans.ancol.AnCol;
import io.ilikeorangutans.bus.EventBus;
import io.ilikeorangutans.bus.SimpleEventBus;

public class AndroidLauncher extends AndroidApplication {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

        config.useAccelerometer = false;
        config.useCompass = false;

        EventBus bus = new SimpleEventBus();

        AnColGestureListener anColGestureListener = new AnColGestureListener(bus);
        anColGestureListener.setEventBus(bus);

        initialize(new AnCol(bus, new GestureDetector(anColGestureListener)), config);
    }

}

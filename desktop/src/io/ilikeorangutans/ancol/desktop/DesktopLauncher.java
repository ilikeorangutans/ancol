package io.ilikeorangutans.ancol.desktop;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import io.ilikeorangutans.ancol.AnCol;
import io.ilikeorangutans.bus.EventBus;
import io.ilikeorangutans.bus.SimpleEventBus;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        final EventBus bus = new SimpleEventBus();
        InputProcessor ip = new DesktopInputProcessor(bus);
        new LwjglApplication(new AnCol(bus, ip), config);
    }

}

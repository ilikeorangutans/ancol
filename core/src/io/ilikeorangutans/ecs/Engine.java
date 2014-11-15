package io.ilikeorangutans.ecs;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Engine {

    private final List<System> systems = new ArrayList<System>();

    public void step(float deltaTime) {
        for (System s : systems) {
            s.step(deltaTime);
        }
    }

}

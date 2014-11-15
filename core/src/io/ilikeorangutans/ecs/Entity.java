package io.ilikeorangutans.ecs;

import java.util.*;

/**
 *
 */
public class Entity {

    private final List<Component> components;
    private final Set<ComponentType> types = new HashSet<ComponentType>();
    private boolean alive = true;

    public Entity(Component... components) {
        this.components = Arrays.asList(components);
        for (Component c : components) {
            types.add(ComponentType.fromComponent(c));
        }
    }

    public boolean hasComponent(ComponentType componentType) {
        return types.contains(componentType);
    }

    /**
     * Returns true if this entity is still in use. If this returns false, the entity should be removed from the
     * simulation.
     *
     * @return
     */
    public boolean isAlive() {
        return alive;
    }

    public void kill() {
        alive = false;
    }
}

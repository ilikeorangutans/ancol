package io.ilikeorangutans.ecs;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class Entity {

    private final Map<ComponentType, Component> components = new HashMap<ComponentType, Component>();
    private boolean alive = true;

    public Entity(Component... components) {
        for (Component c : components) {
            this.components.put(ComponentType.fromComponent(c), c);
        }
    }

    public boolean hasComponent(ComponentType componentType) {
        return components.containsKey(componentType);
    }

    public <T extends Component> T getComponent(Class<T> type) {
        final ComponentType componentType = ComponentType.fromClass(type);

        if (!hasComponent(componentType))
            throw new IllegalArgumentException("Entity does not have component of type " + type.getName());

        return (T) components.get(componentType);
    }

    /**
     * @return Returns true if this entity is still in use. If this returns false, the entity should be removed from the simulation.
     */
    public boolean isAlive() {
        return alive;
    }

    public void kill() {
        alive = false;
    }
}

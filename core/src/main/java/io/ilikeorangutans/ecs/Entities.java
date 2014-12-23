package io.ilikeorangutans.ecs;

import java.util.List;

/**
 *
 */
public interface Entities {
    List<Component> getComponentsByType(ComponentType componentType);

    List<Entity> getEntityByType(ComponentType... types);
}

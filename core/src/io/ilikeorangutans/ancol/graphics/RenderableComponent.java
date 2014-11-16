package io.ilikeorangutans.ancol.graphics;

import io.ilikeorangutans.ecs.Component;
import io.ilikeorangutans.ecs.ComponentType;

/**
 *
 */
public class RenderableComponent implements Component {

    private static final ComponentType COMPONENT_TYPE = ComponentType.fromClass(RenderableComponent.class);

    @Override
    public ComponentType getType() {
        return COMPONENT_TYPE;
    }

}

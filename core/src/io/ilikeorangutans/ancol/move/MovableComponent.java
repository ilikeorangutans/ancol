package io.ilikeorangutans.ancol.move;

import io.ilikeorangutans.ancol.Point;
import io.ilikeorangutans.ecs.Component;
import io.ilikeorangutans.ecs.ComponentType;

/**
 *
 */
public class MovableComponent implements Component {

    private static final ComponentType COMPONENT_TYPE = ComponentType.fromClass(MovableComponent.class)[0];

    private Point destination;

    public static ComponentType getComponentType() {
        return COMPONENT_TYPE;
    }

    public Point getDestination() {
        return destination;
    }

    public void setDestination(Point destination) {
        this.destination = destination;
    }

    @Override
    public ComponentType getType() {
        return COMPONENT_TYPE;
    }

    public boolean hasDestination() {
        return destination != null;
    }
}

package io.ilikeorangutans.ancol.select;

import io.ilikeorangutans.ecs.Component;
import io.ilikeorangutans.ecs.ComponentType;

/**
 *
 */
public class SelectableComponent implements Component {

    private static final ComponentType COMPONENT_TYPE = ComponentType.fromClasses(SelectableComponent.class)[0];

    private boolean selected;

    public SelectableComponent() {
    }

    public SelectableComponent(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public ComponentType getType() {
        return COMPONENT_TYPE;
    }
}

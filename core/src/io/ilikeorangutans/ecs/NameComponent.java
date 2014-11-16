package io.ilikeorangutans.ecs;

/**
 *
 */
public class NameComponent implements Component {

    private static final ComponentType COMPONENT_TYPE = ComponentType.fromClass(NameComponent.class);

    private final String name;

    public NameComponent(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public ComponentType getType() {
        return COMPONENT_TYPE;
    }
}

package io.ilikeorangutans.ecs;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class ComponentType {

    private static final Map<String, ComponentType> types = new HashMap<String, ComponentType>();

    public static ComponentType fromComponent(Component c) {
        return fromClass(c.getClass());
    }

    public static ComponentType fromClass(Class<?> c) {
        final String type = componentClassToType(c);

        if (types.containsKey(type))
            return types.get(type);

        ComponentType ct = new ComponentType(type);
        types.put(type, ct);

        return ct;
    }

    private final String type;

    private static String componentClassToType(Class<?> clazz) {
        if (!clazz.isAssignableFrom(Component.class))
            throw new IllegalArgumentException("No component passed to create component type.");

        return clazz.getName();
    }

    private ComponentType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ComponentType that = (ComponentType) o;

        if (!type.equals(that.type)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return type.hashCode();
    }

    @Override
    public String toString() {
        return "ComponentType{" +
                "type='" + type + '\'' +
                '}';
    }
}

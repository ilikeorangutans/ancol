package io.ilikeorangutans.ecs;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class ComponentType {

	private static final Map<String, ComponentType> types = new HashMap<String, ComponentType>();
	private final String type;

	private ComponentType(String type) {
		this.type = type;
	}

	public static ComponentType fromComponent(Component c) {
		return fromClass(c.getClass());
	}

	public static ComponentType[] fromClasses(Class<? extends Component>... classes) {
		ComponentType[] result = new ComponentType[classes.length];
		int i = 0;

		for (Class<? extends Component> c : classes) {
			result[i] = fromClass(c);
			i++;
		}

		return result;
	}

	private static String componentClassToType(Class<?> clazz) {
		if (!Component.class.isAssignableFrom(clazz))
			throw new IllegalArgumentException("No component passed to create component type.");

		return clazz.getName();
	}

	public static ComponentType fromClass(Class<? extends Component> componentClass) {
		final String type = componentClassToType(componentClass);

		ComponentType ct;
		if (types.containsKey(type)) {
			ct = types.get(type);
		} else {
			ct = new ComponentType(type);
			types.put(type, ct);
		}
		return ct;
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

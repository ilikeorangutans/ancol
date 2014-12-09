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
		return fromClass(c.getClass())[0];
	}

	public static ComponentType[] fromClass(Class<?>... classes) {

		ComponentType[] result = new ComponentType[classes.length];
		int i = 0;

		for (Class<?> c : classes) {


			final String type = componentClassToType(c);

			ComponentType ct;
			if (types.containsKey(type)) {
				ct = types.get(type);
			} else {
				ct = new ComponentType(type);
				types.put(type, ct);
			}

			result[i] = ct;
			i++;

		}

		return result;

	}

	private static String componentClassToType(Class<?> clazz) {
		if (!Component.class.isAssignableFrom(clazz))
			throw new IllegalArgumentException("No component passed to create component type.");

		return clazz.getName();
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

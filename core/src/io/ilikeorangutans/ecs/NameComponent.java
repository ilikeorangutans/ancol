package io.ilikeorangutans.ecs;

/**
 *
 */
public class NameComponent implements Component {

	private static final ComponentType COMPONENT_TYPE = ComponentType.fromClasses(NameComponent.class)[0];

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

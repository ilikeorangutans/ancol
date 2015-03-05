package io.ilikeorangutans.ancol.game.capability;

import io.ilikeorangutans.ecs.Component;
import io.ilikeorangutans.ecs.ComponentType;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class CapabilitiesComponent implements Capabilities, Component {

	private static final ComponentType COMPONENT_TYPE = ComponentType.fromClass(CapabilitiesComponent.class);

	private final List<Capability> capabilities = new ArrayList<Capability>();

	@Override
	public ComponentType getType() {
		return COMPONENT_TYPE;
	}

	public void remove(Capability capability) {
		capabilities.remove(capability);
	}

	@Override
	public boolean isCapableOf(Capability capability) {
		return capabilities.contains(capability);
	}

	public CapabilitiesComponent add(Capability... caps) {
		for (Capability cap : caps) {
			if (!capabilities.contains(cap))
				capabilities.add(cap);
		}
		return this;
	}
}

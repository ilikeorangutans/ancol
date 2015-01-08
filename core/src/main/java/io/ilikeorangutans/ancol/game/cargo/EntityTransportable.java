package io.ilikeorangutans.ancol.game.cargo;

import io.ilikeorangutans.ancol.game.cmd.ControllableComponent;
import io.ilikeorangutans.ancol.game.colonist.ColonistComponent;
import io.ilikeorangutans.ancol.graphics.RenderableComponent;
import io.ilikeorangutans.ecs.ComponentType;
import io.ilikeorangutans.ecs.Entity;

/**
 * Makes an entity transportable.
 */
public class EntityTransportable implements Transportable {

	private final int requiredHolds;
	private final Entity entity;

	public EntityTransportable(Entity entity) {
		if (!entity.hasComponent(ComponentType.fromClass(TransportableComponent.class)))
			throw new IllegalArgumentException("Given entity does not have the transportable component.");

		this.entity = entity;
		this.requiredHolds = entity.getComponent(TransportableComponent.class).getNumHolds();

		entity.getComponent(ControllableComponent.class).setActive(false);
		entity.getComponent(RenderableComponent.class).setVisible(false);
	}

	@Override
	public int getRequiredSpace() {
		return requiredHolds * CargoHold.HOLD_CAPACITY;
	}

	@Override
	public String getDescription() {
		if (entity.hasComponent(ComponentType.fromClass(ColonistComponent.class))) {
			return entity.getComponent(ColonistComponent.class).getProfession().getName();
		}

		return "Unknown Entity";
	}
}

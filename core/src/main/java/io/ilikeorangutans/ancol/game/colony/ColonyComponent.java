package io.ilikeorangutans.ancol.game.colony;

import io.ilikeorangutans.ancol.game.cmd.ControllableComponent;
import io.ilikeorangutans.ancol.game.colonist.ColonistComponent;
import io.ilikeorangutans.ancol.graphics.RenderableComponent;
import io.ilikeorangutans.ancol.map.surrounding.Surroundings;
import io.ilikeorangutans.ecs.Component;
import io.ilikeorangutans.ecs.ComponentType;
import io.ilikeorangutans.ecs.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class ColonyComponent implements Component {
	public static final ComponentType COMPONENT_TYPE = ComponentType.fromClass(ColonyComponent.class);
	private final List<Entity> colonists = new ArrayList<Entity>();
	private final Surroundings surroundings;
	private String name;

	public ColonyComponent(String name, Surroundings surroundings) {
		this.name = name;
		this.surroundings = surroundings;
	}

	public static ComponentType getComponentType() {
		return COMPONENT_TYPE;
	}

	@Override
	public ComponentType getType() {
		return COMPONENT_TYPE;
	}

	public void addColonist(Entity entity) {
		entity.getComponent(RenderableComponent.class).setVisible(false);
		entity.getComponent(ControllableComponent.class).clearCommands();

		colonists.add(entity);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSize() {
		return colonists.size();
	}

	public List<Entity> getColonists() {
		return colonists;
	}

	public Surroundings getSurroundings() {
		return surroundings;
	}

	public List<Entity> getOutsideColonists() {

		List<Entity> entities = surroundings.getEntities(Surroundings.Selector.Center);
		List<Entity> result = new ArrayList<Entity>();

		for (Entity entity : entities) {
			if (!entity.hasComponent(ComponentType.fromClass(ColonistComponent.class)))
				continue;

			if (isColonistEmployed(entity))
				continue;

			result.add(entity);
		}

		return result;
	}

	public boolean isColonistEmployed(Entity entity) {
		return colonists.contains(entity);
	}
}

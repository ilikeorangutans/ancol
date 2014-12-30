package io.ilikeorangutans.ecs;

import io.ilikeorangutans.bus.Emitter;
import io.ilikeorangutans.ecs.event.EntityCreatedEvent;

import java.util.*;

/**
 * Global repository for entities.
 * <p/>
 * TODO: extract interface which will allow to create "filtering" instances of Entities. For example, an Entities class
 * that will only return Entity's that are visible to a given player.
 */
public class SimpleEntities implements Entities, EntityFactory {

	private final Comparator<ComponentType> sortByNumberOfComponents = new SortByNumberOfEntities();
	private final Emitter emitter;
	private Map<ComponentType, List<Component>> componentsByType = new HashMap<ComponentType, List<Component>>();
	private Map<ComponentType, List<Entity>> entitiesByType = new HashMap<ComponentType, List<Entity>>();

	public SimpleEntities(Emitter emitter) {
		this.emitter = emitter;
	}

	@Override
	public Entity create(Component... components) {
		Entity e = new Entity(emitter, components);

		for (Component c : components) {
			final ComponentType type = ComponentType.fromComponent(c);

			if (type == null)
				throw new NullPointerException("Component has null type");

			if (!componentsByType.containsKey(type)) {
				componentsByType.put(type, new ArrayList<Component>());
				entitiesByType.put(type, new ArrayList<Entity>());
			}

			if (c instanceof EntityAwareComponent) {
				((EntityAwareComponent) c).setEntity(e);
			}

			componentsByType.get(type).add(c);
			entitiesByType.get(type).add(e);
		}

		emitter.fire(new EntityCreatedEvent(e));

		return e;
	}

	private int countEntitiesByType(ComponentType type) {
		if (!entitiesByType.containsKey(type))
			return 0;

		return entitiesByType.get(type).size();
	}

	@Override
	public List<Component> getComponentsByType(ComponentType componentType) {
		if (countEntitiesByType(componentType) == 0)
			return Collections.emptyList();

		return componentsByType.get(componentType);
	}

	@Override
	public List<Entity> getEntityByType(ComponentType... types) {
		if (types == null || types.length == 0)
			return Collections.emptyList();

		Arrays.sort(types, sortByNumberOfComponents);

		if (!entitiesByType.containsKey(types[0]))
			return Collections.emptyList();

		final List<Entity> result = entitiesByType.get(types[0]);
		// Filter out all the entities that don't have the remaining requested types:
		for (ListIterator<Entity> li = result.listIterator(); li.hasNext(); ) {
			Entity cur = li.next();

			for (int i = 1; i < types.length; i++) {

				if (!cur.hasComponent(types[i])) {
					li.remove();
				}
			}
		}

		return result;
	}

	private final class SortByNumberOfEntities implements Comparator<ComponentType> {

		@Override
		public int compare(ComponentType componentType, ComponentType t1) {
			int a = countEntitiesByType(componentType);
			int b = countEntitiesByType(t1);
			return b - a;
		}

	}
}

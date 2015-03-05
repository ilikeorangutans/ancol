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
public class SimpleEntities implements EntitiesEntityFactory {

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
				throw new NullPointerException("Component isCapableOf null type");

			if (!componentsByType.containsKey(type)) {
				componentsByType.put(type, new ArrayList<Component>());
				entitiesByType.put(type, new ArrayList<Entity>());
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
	public List<Entity> getEntityByType(ComponentType... types) {
		if (types == null || types.length == 0)
			return Collections.emptyList();

		Arrays.sort(types, sortByNumberOfComponents);

		if (!entitiesByType.containsKey(types[0]))
			return Collections.emptyList();

		List<Entity> entities = entitiesByType.get(types[0]);
		final List<Entity> result = new ArrayList<Entity>();

		for (Entity cur : entities) {
			if (hasAllTypes(cur, types))
				result.add(cur);
		}

		return Collections.unmodifiableList(result);
	}

	private boolean hasAllTypes(Entity entity, ComponentType[] types) {
		for (int i = 1; i < types.length; i++) {
			if (!entity.hasComponent(types[i])) {
				return false;
			}
		}
		return true;
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

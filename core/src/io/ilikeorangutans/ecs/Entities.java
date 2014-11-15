package io.ilikeorangutans.ecs;

import java.util.*;

/**
 * Global repository for entities.
 */
public class Entities {

    private final Comparator<ComponentType> sortByNumberOfComponents = new SortByNumberOfEntities();

    private Map<ComponentType, List<Component>> componentsByType = new HashMap<ComponentType, List<Component>>();
    private Map<ComponentType, List<Entity>> entitiesByType = new HashMap<ComponentType, List<Entity>>();

    public Entity create(Component... components) {
        Entity e = new Entity();

        for (Component c : components) {
            final ComponentType type = ComponentType.fromComponent(c);

            if (!componentsByType.containsKey(type)) {
                componentsByType.put(type, new ArrayList<Component>());
                entitiesByType.put(type, new ArrayList<Entity>());
            }

            componentsByType.get(type).add(c);
            entitiesByType.get(type).add(e);
        }

        return e;
    }

    private int countEntitiesByType(ComponentType type) {
        if (!entitiesByType.containsKey(type))
            return 0;

        return entitiesByType.get(type).size();
    }

    public List<Component> getComponentsByType(ComponentType componentType) {
        if (countEntitiesByType(componentType) == 0)
            return Collections.emptyList();

        return componentsByType.get(componentType);
    }

    public List<Entity> getEntityByType(ComponentType... types) {
        if (types == null || types.length == 0)
            return Collections.emptyList();

        Arrays.sort(types, sortByNumberOfComponents);

        final List<Entity> result = entitiesByType.get(types[0]);

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

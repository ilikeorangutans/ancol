package io.ilikeorangutans.ecs;

import java.util.List;

/**
 * Repository to access Entities
 */
public interface Entities {
	List<Entity> getEntityByType(ComponentType... types);
}

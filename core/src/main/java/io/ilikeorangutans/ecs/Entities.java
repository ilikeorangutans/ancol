package io.ilikeorangutans.ecs;

import java.util.List;

/**
 *
 */
public interface Entities {
	List<Entity> getEntityByType(ComponentType... types);
}

package io.ilikeorangutans.ecs;

/**
 *
 */
public interface EntityFactory {
	Entity create(Component... components);
}

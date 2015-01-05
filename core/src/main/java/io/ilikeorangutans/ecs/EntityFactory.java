package io.ilikeorangutans.ecs;

/**
 * Factory for {@link }Entity}s.
 */
public interface EntityFactory {
	/**
	 * Creates a new Entity with the given types.
	 *
	 * @param components requested component types.
	 * @return the newly created entity.
	 */
	Entity create(Component... components);
}

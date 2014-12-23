package io.ilikeorangutans.ecs;

/**
 * A component that requires a reference to its containing entity.
 */
public interface EntityAwareComponent extends Component {

	void setEntity(Entity entity);

}

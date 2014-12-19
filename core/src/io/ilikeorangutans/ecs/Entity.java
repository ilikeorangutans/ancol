package io.ilikeorangutans.ecs;

import io.ilikeorangutans.bus.Emitter;
import io.ilikeorangutans.ecs.event.EntityUpdatedEvent;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class Entity {

	private final Map<ComponentType, Component> components = new HashMap<ComponentType, Component>();
	private final Emitter emitter;
	private boolean alive = true;


	public Entity(Emitter emitter, Component... components) {
		this.emitter = emitter;
		for (Component c : components) {
			this.components.put(ComponentType.fromComponent(c), c);
		}
	}

	public boolean hasComponent(ComponentType... componentType) {
		return components.containsKey(componentType[0]);
	}

	public <T extends Component> T getComponent(Class<T> type) {
		final ComponentType componentType = ComponentType.fromClasses(type)[0];

		if (!hasComponent(componentType))
			throw new IllegalArgumentException("Entity does not have component of type " + type.getName());

		return (T) components.get(componentType);
	}

	@Override
	public String toString() {
		return "Entity{" +
				"components=" + components +
				", alive=" + alive +
				'}';
	}

	/**
	 * @return Returns true if this entity is still in use. If this returns false, the entity should be removed from the simulation.
	 */
	public boolean isAlive() {
		return alive;
	}

	public void kill() {
		alive = false;
	}

	/**
	 * Fires off an event notifying listeners that this entity has been modified.
	 */
	public void updated() {
		emitter.fire(new EntityUpdatedEvent(this));
	}
}

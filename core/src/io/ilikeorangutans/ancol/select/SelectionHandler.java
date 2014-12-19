package io.ilikeorangutans.ancol.select;

import io.ilikeorangutans.ancol.map.PositionComponent;
import io.ilikeorangutans.bus.Emitter;
import io.ilikeorangutans.bus.Subscribe;
import io.ilikeorangutans.ecs.ComponentType;
import io.ilikeorangutans.ecs.Entities;
import io.ilikeorangutans.ecs.Entity;

import java.util.List;

/**
 *
 */
public class SelectionHandler {

	private final Entities entities;

	private final Emitter emitter;
	private Entity previouslySelected;

	public SelectionHandler(Entities entities, Emitter emitter) {
		this.entities = entities;
		this.emitter = emitter;
	}

	@Subscribe
	public void onSelectEvent(SelectEvent selectEvent) {

		List<Entity> selectable = entities.getEntityByType(ComponentType.fromClasses(PositionComponent.class, SelectableComponent.class));

		for (Entity e : selectable) {
			PositionComponent pc = e.getComponent(PositionComponent.class);

			if (pc.getX() == selectEvent.x && pc.getY() == selectEvent.y) {

				selectEntity(e);

				return;
			}
		}
	}

	private void selectEntity(Entity e) {

		if (previouslySelected != null) {
			previouslySelected.getComponent(SelectableComponent.class).setSelected(false);
		}
		previouslySelected = e;
		SelectableComponent sc = e.getComponent(SelectableComponent.class);
		sc.setSelected(!sc.isSelected());

		emitter.fire(new EntitySelectedEvent(e));
	}

}

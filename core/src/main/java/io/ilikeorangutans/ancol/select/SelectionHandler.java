package io.ilikeorangutans.ancol.select;

import io.ilikeorangutans.ancol.Point;
import io.ilikeorangutans.ancol.graphics.RenderableComponent;
import io.ilikeorangutans.ancol.map.PositionComponent;
import io.ilikeorangutans.bus.Emitter;
import io.ilikeorangutans.bus.Subscribe;
import io.ilikeorangutans.ecs.ComponentType;
import io.ilikeorangutans.ecs.Entities;
import io.ilikeorangutans.ecs.Entity;

import java.util.ArrayList;
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

		Point p = new Point(selectEvent.x, selectEvent.y);
		List<Entity> selectable = getSelectablesAtPoint(p);

		if (selectable.isEmpty())
			return;

		if (selectable.size() > 1) {
			emitter.fire(new MultipleSelectOptionsEvent(selectable));
		} else {
			selectEntity(selectable.get(0));
		}
	}

	private List<Entity> getSelectablesAtPoint(Point p) {
		List<Entity> list = entities.getEntityByType(ComponentType.fromClasses(PositionComponent.class, SelectableComponent.class, RenderableComponent.class));
		List<Entity> result = new ArrayList<Entity>();

		for (Entity e : list) {
			RenderableComponent rc = e.getComponent(RenderableComponent.class);
			if (!rc.isVisible())
				continue;

			PositionComponent pc = e.getComponent(PositionComponent.class);
			if (pc.getX() == p.x && pc.getY() == p.y) {
				result.add(e);
			}
		}

		return result;
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

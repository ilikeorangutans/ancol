package io.ilikeorangutans.ancol.input.action;

import io.ilikeorangutans.ancol.map.PositionComponent;
import io.ilikeorangutans.ancol.map.viewport.CenterViewEvent;
import io.ilikeorangutans.ancol.select.EntitySelectedEvent;
import io.ilikeorangutans.bus.Emitter;
import io.ilikeorangutans.bus.Subscribe;
import io.ilikeorangutans.ecs.Entity;

/**
 *
 */
public class CenterViewAction extends Action {

	private final Emitter emitter;
	private Entity entity;

	public CenterViewAction(Emitter emitter) {
		this.emitter = emitter;
	}

	@Subscribe
	public void onEntitySelected(EntitySelectedEvent event) {
		entity = event.entity;


		setEnabled(entity != null);
	}

	@Override
	public void doPerform() {
		emitter.fire(new CenterViewEvent(entity.getComponent(PositionComponent.class).getPoint()));
	}
}

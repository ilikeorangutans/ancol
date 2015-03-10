package io.ilikeorangutans.ancol.input.action;

import io.ilikeorangutans.ancol.game.ability.AbilitiesComponent;
import io.ilikeorangutans.ancol.game.ability.Ability;
import io.ilikeorangutans.ancol.game.ability.BuildColonyAbility;
import io.ilikeorangutans.ancol.game.cmd.BuildColonyCommand;
import io.ilikeorangutans.ancol.game.cmd.event.CommandEvent;
import io.ilikeorangutans.ancol.move.MovedEvent;
import io.ilikeorangutans.ancol.select.event.EntitySelectedEvent;
import io.ilikeorangutans.bus.Emitter;
import io.ilikeorangutans.bus.Subscribe;
import io.ilikeorangutans.ecs.ComponentType;
import io.ilikeorangutans.ecs.Entity;

/**
 *
 */
public class BuildColonyAction extends Action {

	private final Emitter emitter;
	private final Ability ability = BuildColonyAbility.INSTANCE;
	private Entity entity;

	public BuildColonyAction(Emitter emitter) {
		this.emitter = emitter;
	}

	@Subscribe
	public void onEntitySelected(EntitySelectedEvent event) {
		updateState(event.entity);
	}

	private void updateState(Entity entity) {
		this.entity = entity;
		setEnabled(false);

		if (this.entity == null)
			return;

		if (!this.entity.hasComponent(ComponentType.fromClass(AbilitiesComponent.class)))
			return;

		if (!this.entity.getComponent(AbilitiesComponent.class).has(ability)) {
			return;
		}

		setEnabled(ability.canBeUsedBy(this.entity));
	}

	@Subscribe
	public void onEntityMoved(MovedEvent event) {
		updateState(event.entity);
	}

	@Override
	public void doPerform() {
		emitter.fire(new CommandEvent(new BuildColonyCommand(entity)));
	}
}

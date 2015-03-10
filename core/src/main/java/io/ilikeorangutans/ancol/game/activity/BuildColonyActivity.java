package io.ilikeorangutans.ancol.game.activity;

import io.ilikeorangutans.ancol.game.actionpoint.ActionPoints;
import io.ilikeorangutans.ancol.game.activity.event.CannotPerformEvent;
import io.ilikeorangutans.ancol.game.ability.BuildColonyAbility;
import io.ilikeorangutans.ancol.game.ability.AbilitiesComponent;
import io.ilikeorangutans.ancol.game.colony.BuildColonyEvent;
import io.ilikeorangutans.bus.Emitter;
import io.ilikeorangutans.ecs.Entity;

/**
 *
 */
public class BuildColonyActivity implements Activity {

	private final Entity builder;
	private boolean complete = false;

	public BuildColonyActivity(Entity builder) {
		this.builder = builder;
	}

	@Override
	public String getName() {
		return "build colony";
	}

	@Override
	public void perform(Emitter emitter, ActionPoints actionPoints) {
		if (!builder.getComponent(AbilitiesComponent.class).has(BuildColonyAbility.INSTANCE)) {
			complete = true;
			emitter.fire(new CannotPerformEvent(builder, this));
			return;
		}

		if (actionPoints.consume(actionPoints.getAvailablePoints()) > 0) {
			complete = true;
		}

		emitter.fire(new BuildColonyEvent(builder));
	}

	@Override
	public boolean isComplete() {
		return complete;
	}

}

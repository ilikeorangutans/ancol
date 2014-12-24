package io.ilikeorangutans.ancol.game.activity;

import io.ilikeorangutans.ancol.game.actionpoint.ActionPoints;
import io.ilikeorangutans.ancol.game.activity.event.ActivityCompleteEvent;
import io.ilikeorangutans.bus.Emitter;
import io.ilikeorangutans.ecs.ComponentType;
import io.ilikeorangutans.ecs.Entity;
import io.ilikeorangutans.ecs.EntityAwareComponent;

/**
 * Performs action point calculations for entities.
 */
public class ActivityComponent implements EntityAwareComponent {

	public static final ComponentType COMPONENT_TYPE = ComponentType.fromClasses(ActivityComponent.class)[0];

	private final ActionPoints actionPoints;

	private Activity activity;
	/**
	 * TODO: Entity could be passed into #step() from ActivitySystem...
	 */
	private Entity entity;

	public ActivityComponent(int maxPointsPerTurn) {

		actionPoints = new ActionPoints(maxPointsPerTurn);
	}

	@Override
	public ComponentType getType() {
		return COMPONENT_TYPE;
	}

	/**
	 * Performs a step with the current activity.
	 *
	 * @param emitter
	 */
	public void step(Emitter emitter) {

		activity.perform(emitter, actionPoints);

		if (activity.isComplete()) {
			activity = null;
			emitter.fire(new ActivityCompleteEvent(entity, activity));
		}

		entity.updated();
	}

	public boolean hasActivity() {
		if (IdleActivity.IDLE_ACTIVITY.equals(activity))
			return false;

		return activity != null;
	}

	/**
	 * Refills the components action points.
	 */
	public void replenish() {
		actionPoints.replenish();
		entity.updated();
	}

	public int getPointsLeft() {
		return actionPoints.getAvailablePoints();
	}

	@Override
	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	/**
	 * @return true if an activity can be performed.
	 */
	public boolean canPerform() {
		return actionPoints.getAvailablePoints() > 0;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

}

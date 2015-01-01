package io.ilikeorangutans.ancol.game.activity;

import io.ilikeorangutans.ancol.game.actionpoint.ActionPoints;
import io.ilikeorangutans.ancol.game.activity.event.ActivityCompleteEvent;
import io.ilikeorangutans.bus.Emitter;
import io.ilikeorangutans.ecs.Component;
import io.ilikeorangutans.ecs.ComponentType;
import io.ilikeorangutans.ecs.Entity;

/**
 * Performs action point calculations for entities.
 */
public class ActivityComponent implements Component {

	public static final ComponentType COMPONENT_TYPE = ComponentType.fromClass(ActivityComponent.class);

	private final ActionPoints actionPoints;

	private Activity activity;

	/**
	 * TODO: Entity could be passed into #beginTurn() from ActivitySystem...
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
	 * Performs a beginTurn with the current activity.
	 *
	 * @param emitter
	 */
	public void step(Emitter emitter) {
		activity.perform(emitter, actionPoints);

		if (activity.isComplete()) {
			emitter.fire(new ActivityCompleteEvent(entity, activity));
			activity = null;
		}
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
	}

	public int getPointsLeft() {
		return actionPoints.getAvailablePoints();
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

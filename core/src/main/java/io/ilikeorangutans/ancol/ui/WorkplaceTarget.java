package io.ilikeorangutans.ancol.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import io.ilikeorangutans.ancol.game.colonist.Job;
import io.ilikeorangutans.ancol.game.colony.ColonyComponent;
import io.ilikeorangutans.ancol.game.production.Workplace;
import io.ilikeorangutans.ecs.Entity;

import java.util.Set;

/**
 *
 */
public class WorkplaceTarget extends DragAndDrop.Target {

	private final Actor actor;
	private final ColonyComponent colony;
	private final Workplace workplace;

	public WorkplaceTarget(Actor actor, ColonyComponent colony, Workplace workplace) {
		super(actor);
		this.actor = actor;
		this.colony = colony;
		this.workplace = workplace;
	}

	@Override
	public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
		if (!workplace.hasAvailableWorkplaces())
			return false;

		System.out.println("WorkplaceTarget.drag Check if worker can actually do the job");

		return true;
	}

	@Override
	public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
		System.out.println("BuildingTarget.drop on " + workplace);

		Set<Job> jobs = workplace.getAvailableJobs();
		for (Job job : jobs) {
			System.out.println("  - " + job.getName() + " " + job.getConsumes() + " -> " + job.getProduces());
		}

		Entity colonist = (Entity) payload.getObject();
		if (jobs.size() == 1) {
			colony.changeJob(colonist, jobs.iterator().next(), workplace);
		} else {
			// show job selection screen
		}
	}
}

package io.ilikeorangutans.ancol.ui.colony;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import io.ilikeorangutans.ancol.game.colonist.ColonistComponent;
import io.ilikeorangutans.ancol.game.colonist.Job;
import io.ilikeorangutans.ancol.game.colony.ColonyComponent;
import io.ilikeorangutans.ancol.game.production.Workplace;
import io.ilikeorangutans.ancol.ui.JobSelect;
import io.ilikeorangutans.ecs.ComponentType;
import io.ilikeorangutans.ecs.Entity;

import java.util.Set;


/**
 * Drag and drop target for workplaces.
 */
public class WorkplaceTarget extends DragAndDrop.Target implements JobSelectListener {

	private final Actor actor;
	private final ColonyComponent colony;
	private final Workplace workplace;
	private JobSelect jobSelect;
	private Entity colonist;

	public WorkplaceTarget(Actor actor, ColonyComponent colony, Workplace workplace, JobSelect jobSelect) {
		super(actor);
		this.actor = actor;
		this.colony = colony;
		this.workplace = workplace;
		this.jobSelect = jobSelect;
		jobSelect.addJobSelectListener(this);
	}

	@Override
	public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
		if (!workplace.hasAvailableWorkplaces())
			return false;

		if (!(payload.getObject() instanceof Entity)) {
			return false;
		}

		Entity entity = (Entity) payload.getObject();
		if (!entity.hasComponent(ComponentType.fromClass(ColonistComponent.class)))
			return false;

		// TODO: check if the colonist can actually perform the given job (converts e.g. can't act as priests etc)

		return true;
	}

	@Override
	public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
		Set<Job> jobs = workplace.getAvailableJobs();
		colonist = (Entity) payload.getObject();
		Job job;
		if (jobs.size() > 1) {
			jobSelect.select(jobs);
			return;
		} else {
			job = jobs.iterator().next();
		}
		colony.changeJob(colonist, job, workplace);
	}

	@Override
	public void jobSelected(Job job) {
		if (job == null)
			return;

		colony.changeJob(colonist, job, workplace);
	}
}

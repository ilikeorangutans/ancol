package io.ilikeorangutans.ancol.game.colonist;

import com.badlogic.gdx.Gdx;
import io.ilikeorangutans.ancol.game.ware.AvailableWares;
import io.ilikeorangutans.ancol.game.ware.Ware;

import java.util.List;

/**
 *
 */
public class AvailableProfessions {

	/**
	 * Holds all jobs that a colonist can take on.
	 */
	private List<Job> jobs;

	public List<Job> getJobs() {
		return jobs;
	}

	public Job findProducerFor(Ware ware) {
		for (Job job : jobs) {
			if (job.getProduces().equals(ware)) {
				return job;
			}
		}
		Gdx.app.log(AvailableProfessions.class.getName(), "No job found that produces " + ware);
		return null;
	}

	public void postProcess(AvailableWares wares) {
		for (Job job : jobs) {
			job.postProcess(wares);
		}
	}

	public Job findJobThatProduces(Ware ware) {
		for (Job job : jobs) {
			if (job.getProduces().equals(ware)) {
				return job;
			}
		}

		throw new IllegalArgumentException("Can't find job that produces " + ware);
	}
}

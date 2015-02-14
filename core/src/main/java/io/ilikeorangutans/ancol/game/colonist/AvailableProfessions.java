package io.ilikeorangutans.ancol.game.colonist;

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
		throw new IllegalArgumentException("No job found that produces " + ware.getName());
	}
}

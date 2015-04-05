package io.ilikeorangutans.ancol.game.production;

import io.ilikeorangutans.ancol.game.colonist.Job;
import io.ilikeorangutans.ancol.game.production.worker.Worker;

import java.util.Set;

/**
 *
 */
public interface Workplace {

	Modifier getModifier();

	boolean hasAvailableWorkplaces();

	int getTotalWorkplaces();

	int getAvailableWorkplaces();

	int getNumberOfWorkers();

	void addWorker(Worker worker);

	void removeWorker(Worker worker);

	Set<Job> getAvailableJobs();

}

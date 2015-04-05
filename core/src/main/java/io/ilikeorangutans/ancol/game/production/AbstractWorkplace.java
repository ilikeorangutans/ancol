package io.ilikeorangutans.ancol.game.production;

import io.ilikeorangutans.ancol.game.production.worker.Worker;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 */
public abstract class AbstractWorkplace implements Workplace {

	private final Set<Worker> workers = new LinkedHashSet<Worker>();

	private final int maxWorkplaces;

	protected AbstractWorkplace(int maxWorkplaces) {
		this.maxWorkplaces = maxWorkplaces;
	}

	@Override
	public boolean hasAvailableWorkplaces() {
		return getAvailableWorkplaces() > 0;
	}

	@Override
	public int getTotalWorkplaces() {
		return maxWorkplaces;
	}

	@Override
	public int getAvailableWorkplaces() {
		return getTotalWorkplaces() - workers.size();
	}

	@Override
	public int getNumberOfWorkers() {
		return workers.size();
	}

	@Override
	public void addWorker(Worker worker) {
		workers.add(worker);
	}

	@Override
	public void removeWorker(Worker worker) {
		workers.remove(worker);
	}

}

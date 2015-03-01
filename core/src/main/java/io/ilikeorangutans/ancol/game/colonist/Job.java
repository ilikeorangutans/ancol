package io.ilikeorangutans.ancol.game.colonist;

import io.ilikeorangutans.ancol.game.ware.AvailableWares;
import io.ilikeorangutans.ancol.game.ware.Ware;

import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * Describes the job a colonist can perform. A job always produces
 */
public class Job {

	private String name;
	private String produces;
	private String consumes;
	private Ware producesWare;
	private Ware consumesWare;

	public void setProducesWare(Ware producesWare) {
		this.producesWare = producesWare;
	}

	@Override
	public String toString() {
		return "Job{" +
				"name='" + name + '\'' +
				", produces=" + producesWare.getName() +
				'}';
	}

	public String getName() {
		return name;
	}

	public Ware getProduces() {
		return producesWare;
	}

	public Ware getConsumes() {
		return consumesWare;
	}

	public void postProcess(AvailableWares wares) {
		if (!isNullOrEmpty(produces))
			producesWare = wares.findByName(produces);
		if (!isNullOrEmpty(consumes))
			consumesWare = wares.findByName(consumes);
	}
}

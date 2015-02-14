package io.ilikeorangutans.ancol.game.colonist;

import io.ilikeorangutans.ancol.game.ware.Ware;

/**
 * Describes the job a colonist can perform. A job always produces
 */
public class Job {

	private String name;
	private String produces;
	private Ware ware;

	public Job() {
	}

	public void setWare(Ware ware) {
		this.ware = ware;
	}

	@Override
	public String toString() {
		return "Job{" +
				"name='" + name + '\'' +
				", produces=" + ware.getName() +
				'}';
	}

	public String getName() {
		return name;
	}

	public String getProducesString() {
		return produces;
	}

	public Ware getProduces() {
		return ware;
	}

}

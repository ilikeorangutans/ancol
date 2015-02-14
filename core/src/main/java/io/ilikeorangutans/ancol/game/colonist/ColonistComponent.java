package io.ilikeorangutans.ancol.game.colonist;

import io.ilikeorangutans.ancol.game.production.worker.Worker;
import io.ilikeorangutans.ancol.game.ware.Ware;
import io.ilikeorangutans.ecs.Component;
import io.ilikeorangutans.ecs.ComponentType;

/**
 *
 */
public class ColonistComponent implements Component, Worker {

	/**
	 * Profession this colonist was trained in.
	 */
	private Profession profession; //  this could be some kind of object describing bonuses?
	private String job; // job a colonist holds in a colony; might store that within the colony as opposed to here.
	private String equipment; // equipment the unit carries like tools, weapons, (horses? unit can carry horses and weapons... ?)

	public ColonistComponent(Profession profession) {
		this.profession = profession;
	}

	@Override
	public ComponentType getType() {
		return null;
	}

	public Profession getProfession() {
		return profession;
	}

	public void setProfession(Profession profession) {
		this.profession = profession;
	}

	@Override
	public int calculateOutput(Ware type) {
		return profession.calculateOutput(type);
	}
}

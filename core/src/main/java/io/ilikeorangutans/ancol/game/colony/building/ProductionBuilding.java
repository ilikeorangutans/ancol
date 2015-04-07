package io.ilikeorangutans.ancol.game.colony.building;

import com.google.common.collect.Sets;
import io.ilikeorangutans.ancol.game.colonist.Colonists;
import io.ilikeorangutans.ancol.game.colonist.Job;
import io.ilikeorangutans.ancol.game.colony.ColonistsImpl;
import io.ilikeorangutans.ancol.game.production.AbstractWorkplace;
import io.ilikeorangutans.ancol.game.production.Modifier;
import io.ilikeorangutans.ancol.game.ware.Ware;
import io.ilikeorangutans.ecs.Entity;

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

/**
 *
 */
public class ProductionBuilding extends AbstractWorkplace implements Building {

	private final Colonists colonists = new ColonistsImpl();

	private int additiveBonus = 0;
	private float multiplicativeBonus = 1;

	private BuildingType buildingType;
	private final Set<Job> jobs;

	public ProductionBuilding(BuildingType buildingType, int additiveBonus, int multiplicativeBonus) {
		super(buildingType.getWorkplaces());
		this.buildingType = buildingType;

		this.additiveBonus = additiveBonus;
		this.multiplicativeBonus = multiplicativeBonus;

		jobs = Collections.unmodifiableSet(Sets.newHashSet(buildingType.getJob()));
	}

	@Override
	public String getName() {
		return buildingType.getName();
	}

	@Override
	public Ware getOutput() {
		return buildingType.getOutput();
	}

	@Override
	public BuildingType getBuildingType() {
		return buildingType;
	}

	@Override
	public Modifier getModifier() {
		return null;
	}

	@Override
	public Set<Job> getAvailableJobs() {
		return jobs;
	}

	@Override
	public String toString() {
		return "ProductionBuilding{" +
				"buildingType=" + buildingType +
				'}';
	}

	@Override
	public int size() {
		return colonists.size();
	}

	@Override
	public void add(Entity colonist) {
		colonists.add(colonist);
	}

	@Override
	public void remove(Entity colonist) {
		colonists.remove(colonist);
	}

	@Override
	public Iterator<Entity> iterator() {
		return colonists.iterator();
	}
}

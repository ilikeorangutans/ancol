package io.ilikeorangutans.ancol.game.colony;

import com.google.common.collect.Lists;
import io.ilikeorangutans.ancol.game.colonist.Job;
import io.ilikeorangutans.ancol.game.production.AbstractWorkplace;
import io.ilikeorangutans.ancol.game.production.Modifier;
import io.ilikeorangutans.ancol.game.production.Production;
import io.ilikeorangutans.ancol.game.production.worker.FixedWorker;
import io.ilikeorangutans.ancol.game.ware.RecordingWares;
import io.ilikeorangutans.ancol.game.ware.SimpleWares;
import io.ilikeorangutans.ancol.game.ware.Ware;
import io.ilikeorangutans.ancol.game.ware.WareType;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 *
 */
public class ColonyProductionTest {

	private ColonyProduction production;
	private Ware food;
	private SimpleWares wares;
	private Ware ore;
	private Ware tools;

	@Before
	public void setUp() {
		production = new ColonyProduction();
		food = new Ware(0, WareType.Food, "Food", true);
		ore = new Ware(1, WareType.Construction, "Ore", true);
		tools = new Ware(2, WareType.Construction, "Tools", true);
		wares = new SimpleWares(Lists.newArrayList(food, ore, tools));
	}

	@Test
	public void testEmptyState() {
		RecordingWares recordingWares = production.simulate(wares);

		assertThat(recordingWares.getProduced(food), is(0));
		assertThat(recordingWares.getConsumed(food), is(0));
	}

	@Test
	public void testSimpleProduction() {
		production.addProduction(new Production(ore, tools, new FixedWorker(3), new AbstractWorkplace(1) {
			@Override
			public Modifier getModifier() {
				return null;
			}

			@Override
			public Set<Job> getAvailableJobs() {
				return null;
			}
		}));

		RecordingWares recordingWares = production.simulate(wares);

		assertThat(recordingWares.getConsumed(ore), is(3));
		assertThat(recordingWares.getProduced(ore), is(0));
		assertThat(recordingWares.getProduced(tools), is(0));
	}

}
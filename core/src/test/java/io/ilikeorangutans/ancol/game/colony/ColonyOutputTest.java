package io.ilikeorangutans.ancol.game.colony;

import io.ilikeorangutans.ancol.game.production.ProductionBuilder;
import io.ilikeorangutans.ancol.game.production.worker.FixedWorker;
import io.ilikeorangutans.ancol.game.ware.RecordingWares;
import io.ilikeorangutans.ancol.game.ware.SimpleWares;
import io.ilikeorangutans.ancol.game.ware.WareType;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ColonyOutputTest {

	/**
	 * Productions need to be called in their respective order of dependency. For example, mine ore, produce tools,
	 * produce muskets.
	 */
	@Test
	public void testProductionsShouldBeOrderedByTheirDependencies() {
		ColonyOutput output = new ColonyOutput();

		output.addProduction(new ProductionBuilder().consume(WareType.Tools).produce(WareType.Muskets).with(new FixedWorker(3)).create());
		output.addProduction(new ProductionBuilder().consume(WareType.Ore).produce(WareType.Tools).with(new FixedWorker(4)).create());
		output.addProduction(new ProductionBuilder().produce(WareType.Ore).with(new FixedWorker(5)).create());

		RecordingWares wares = new RecordingWares(new SimpleWares());
		output.produce(wares);

		assertThat(wares.getAmount(WareType.Ore), is(1));
		assertThat(wares.getAmount(WareType.Tools), is(1));
		assertThat(wares.getAmount(WareType.Muskets), is(3));

		assertThat(wares.getProduced(WareType.Ore), is(5));
		assertThat(wares.getProduced(WareType.Tools), is(4));
		assertThat(wares.getProduced(WareType.Muskets), is(3));
	}

}
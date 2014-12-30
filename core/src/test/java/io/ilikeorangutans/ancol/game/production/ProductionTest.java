package io.ilikeorangutans.ancol.game.production;

import io.ilikeorangutans.ancol.game.production.worker.FixedWorker;
import io.ilikeorangutans.ancol.game.ware.SimpleWares;
import io.ilikeorangutans.ancol.game.ware.Wares;
import org.junit.Test;

import static io.ilikeorangutans.ancol.game.ware.WareType.*;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ProductionTest {

	private Wares wares = new SimpleWares();

	@Test
	public void testProductionWithoutInputShouldProduce() {
		Production production = new ProductionBuilder().produce(Food).with(new FixedWorker(3)).create();

		assertThat(production.requiresInput(), is(false));
		assertThat(production.calculateConsumption(), is(0));
		assertThat(production.calculateMaxOutput(), is(3));

		production.produce(wares);

		assertThat(wares.getAmount(Food), is(3));
	}

	@Test
	public void testProductionRequiringMoreInputThanThereIsShouldOnlyConsumeWhatIsAvailable() {
		wares.store(Sugar, 2);
		Production production = new ProductionBuilder().consume(Sugar).produce(Rum).with(new FixedWorker(3)).create();

		assertThat(production.requiresInput(), is(true));
		assertThat(production.calculateConsumption(), is(3));
		assertThat(production.calculateMaxOutput(), is(3));

		production.produce(wares);

		assertThat(wares.getAmount(Rum), is(2));
		assertThat(wares.getAmount(Sugar), is(0));
	}

	@Test
	public void testProductionShouldNotConsumeMoreThanItNeeds() {
		wares.store(Sugar, 4);
		Production production = new ProductionBuilder().consume(Sugar).produce(Rum).with(new FixedWorker(3)).create();

		assertThat(production.requiresInput(), is(true));
		assertThat(production.calculateConsumption(), is(3));
		assertThat(production.calculateMaxOutput(), is(3));

		production.produce(wares);

		assertThat(wares.getAmount(Rum), is(3));
		assertThat(wares.getAmount(Sugar), is(1));
	}

}
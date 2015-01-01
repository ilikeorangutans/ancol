package io.ilikeorangutans.ancol.game.player;

import io.ilikeorangutans.ancol.game.activity.ActivityComponent;
import io.ilikeorangutans.ancol.game.event.SimulateEntityEvent;
import io.ilikeorangutans.bus.EventBus;
import io.ilikeorangutans.bus.SimpleEventBus;
import io.ilikeorangutans.bus.Subscribe;
import io.ilikeorangutans.ecs.Entity;
import io.ilikeorangutans.ecs.SimpleEntities;
import org.junit.Assert;
import org.junit.Test;

public class NextUnitPickerTest {

	private EventBus bus = new SimpleEventBus();
	private Player player = new Player(1, "test");
	private SimpleEntities entities = new SimpleEntities(bus);

	@Test
	public void testPickerShouldPickFirstEntityWithoutActivity() {
		System.out.println("NextUnitPickerTest.testPickerShouldPickFirstEntityWithoutActivity");
		bus.subscribe(new TestSimulateListener(true));

		Entity e1 = entities.create(new PlayerOwnedComponent(player), new ActivityComponent(2));
		Entity e2 = entities.create(new PlayerOwnedComponent(player), new ActivityComponent(2));

		NextUnitPicker picker = new NextUnitPicker(bus, player, entities);

		picker.onActionPointsConsumed(null);
	}

	public class TestSimulateListener {

		private final boolean expected;
		private boolean called;

		public TestSimulateListener(boolean expected) {
			this.expected = expected;
		}

		@Subscribe
		public void onSimulateEntity(SimulateEntityEvent e) {
			System.out.println("TestSimulateListener.onSimulateEntity");
			System.out.println("e = [" + e + "]");
			called = true;
		}

		public void verify() {
			if (called != expected)
				Assert.fail("BAM");
		}
	}

}
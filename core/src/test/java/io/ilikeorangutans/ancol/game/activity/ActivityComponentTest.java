package io.ilikeorangutans.ancol.game.activity;

import io.ilikeorangutans.ancol.game.activity.event.ActivityCompleteEvent;
import io.ilikeorangutans.bus.EventBus;
import io.ilikeorangutans.bus.SimpleEventBus;
import io.ilikeorangutans.bus.Subscribe;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class ActivityComponentTest {


	private EventBus bus = new SimpleEventBus();

	@Test
	public void testPerformingActivityShouldConsumePointsAndFireEvent() {
		ActivityComponent ac = new ActivityComponent(2);
		ac.replenish();

		assertThat(ac.canPerform(), is(true));
		assertThat(ac.getPointsLeft(), is(2));

		ac.setActivity(new TestActivity(2));

		assertThat(ac.canPerform(), is(true));

		TestListener listener = new TestListener(true);
		bus.subscribe(listener);

		ac.step(bus);

		assertThat(ac.getPointsLeft(), is(0));
		assertThat(ac.hasActivity(), is(false));
		listener.verify();
	}

	public class TestListener {

		private final boolean expected;
		private boolean called = false;

		public TestListener(boolean expected) {
			this.expected = expected;
		}

		@Subscribe
		public void onActivityComplete(ActivityCompleteEvent event) {
			called = true;
		}

		public void verify() {
			if (called != expected)
				fail((expected ? "Did" : "Didn't") + " expect event but " + (called ? "did get" : "didn't get") + " event");
		}
	}

}
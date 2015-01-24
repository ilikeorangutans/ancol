package io.ilikeorangutans.ancol.screen;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.ilikeorangutans.ancol.game.event.AllEntitiesSimulatedEvent;
import io.ilikeorangutans.ancol.game.player.Player;
import io.ilikeorangutans.ancol.game.player.event.BeginTurnEvent;
import io.ilikeorangutans.ancol.game.player.event.PickNextEntityEvent;
import io.ilikeorangutans.ancol.game.player.event.TurnConcludedEvent;
import io.ilikeorangutans.bus.Emitter;
import io.ilikeorangutans.bus.Subscribe;

/**
 *
 */
public class NextUnitOrTurnButtonState {

	private final Emitter emitter;
	private final Player player;
	private final TextButton button;
	private final ClickListener endTurnClickListener;
	private final ClickListener nextUnitClickListener;

	public NextUnitOrTurnButtonState(final Emitter emitter, Player player, final TextButton button) {
		this.emitter = emitter;
		this.player = player;
		this.button = button;

		endTurnClickListener = new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				emitter.fire(new TurnConcludedEvent());
				button.removeListener(endTurnClickListener); // necessary as we can't disable buttons
			}
		};
		nextUnitClickListener = new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				emitter.fire(new PickNextEntityEvent());
			}
		};

	}

	@Subscribe
	public void onBeginTurn(BeginTurnEvent event) {
		if (event.player.equals(player)) {
			button.setDisabled(false);
			button.setText("Next Unit");

			button.addListener(nextUnitClickListener);

		} else {
			button.setText("Turn: " + event.player.getName() + "...");
		}
	}

	@Subscribe
	public void onAllEntitiesSimulated(AllEntitiesSimulatedEvent event) {
		if (!event.player.equals(player))
			return;

		button.setDisabled(true);
		button.setText("End Turn");
		button.addListener(endTurnClickListener);
		button.removeCaptureListener(nextUnitClickListener);
	}

}

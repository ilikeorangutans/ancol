package io.ilikeorangutans.ancol.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.ilikeorangutans.ancol.game.activity.ActivityComponent;
import io.ilikeorangutans.ancol.game.cmd.ControllableComponent;
import io.ilikeorangutans.ancol.game.turn.BeginTurnEvent;
import io.ilikeorangutans.ancol.input.AnColInputProcessor;
import io.ilikeorangutans.ancol.input.action.AnColActions;
import io.ilikeorangutans.ancol.map.viewport.ScreenToTile;
import io.ilikeorangutans.ancol.path.DumbPathFinder;
import io.ilikeorangutans.ancol.select.EntitySelectedEvent;
import io.ilikeorangutans.bus.EventBus;
import io.ilikeorangutans.bus.Subscribe;
import io.ilikeorangutans.ecs.Entity;
import io.ilikeorangutans.ecs.NameComponent;
import io.ilikeorangutans.ecs.event.EntityUpdatedEvent;

/**
 *
 */
public class GameScreenUI {

	private final EventBus bus;

	private final AnColActions actions;

	private final Skin skin;
	private Stage stage;

	public GameScreenUI(EventBus bus, AnColActions actions, Skin skin) {
		this.bus = bus;
		this.actions = actions;
		this.skin = skin;
	}

	public void setupUI(Skin skin) {
		stage = new Stage(new ScreenViewport());
		Table table = new Table(skin);
		table.left().bottom();
		stage.addActor(table);

		TextButton tb;

		final TextButton tb2 = new TextButton("Current Player", skin, "default");
		tb2.setDisabled(true);

		bus.subscribe(new CurrentPlayerListener(tb2));
		table.add(tb2).pad(17);

		TextButton tb3 = new TextButton("Selected Unit (Points) (activity) (queue)", skin, "default");
		tb3.setDisabled(true);
		bus.subscribe(new SelectedUnitListener(tb3));
		table.add(tb3).pad(17);

		tb = new TextButton("Fortify", skin, "default");
		table.add(tb);

		tb = new TextButton("Colony", skin, "default");
		tb.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				actions.getBuildColonyAction().perform();
			}
		});
		table.add(tb);

		tb = new TextButton("Road", skin, "default");
		tb.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				actions.getBuildRoadAction().perform();
			}
		});
		table.add(tb);

		tb = new TextButton("Improve", skin, "default");
		tb.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				actions.getImproveTileAction().perform();
			}
		});
		table.add(tb);

		tb = new TextButton("End Turn", skin, "default");
		tb.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				actions.getEndTurnAction().perform();
			}
		});
		table.add(tb).pad(34);
	}

	public void setupInputProcessing(ScreenToTile screenToTile) {
		InputMultiplexer inputMultiplexer = new InputMultiplexer();
		AnColInputProcessor anColInputProcessor = new AnColInputProcessor(bus, screenToTile, new DumbPathFinder(), actions);
		bus.subscribe(anColInputProcessor);
		inputMultiplexer.addProcessor(stage);
		inputMultiplexer.addProcessor(anColInputProcessor);
		Gdx.input.setInputProcessor(inputMultiplexer);
	}

	public void render() {
		stage.act();
		stage.draw();
	}

	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	public void dispose() {
		stage.dispose();
	}

	public static class CurrentPlayerListener {
		private final TextButton tb2;

		public CurrentPlayerListener(TextButton tb2) {
			this.tb2 = tb2;
		}

		@Subscribe
		public void onBeginTurn(BeginTurnEvent bte) {
			tb2.setText(bte.player.getName());
		}
	}

	public static class SelectedUnitListener {
		private final TextButton tb2;
		private Entity selected;

		public SelectedUnitListener(TextButton tb2) {
			this.tb2 = tb2;
		}

		@Subscribe
		public void onUpdated(EntityUpdatedEvent e) {
			if (selected == e.entity)
				updateLabel();
		}

		@Subscribe
		public void onSelected(EntitySelectedEvent e) {
			selected = e.entity;
			updateLabel();
		}

		private void updateLabel() {
			if (selected == null) {
				tb2.setText("none (0) (nothing)");
			} else {
				NameComponent nc = selected.getComponent(NameComponent.class);
				ActivityComponent ac = selected.getComponent(ActivityComponent.class);
				ControllableComponent cc = selected.getComponent(ControllableComponent.class);

				tb2.setText(nc.getName() + " (" + ac.getPointsLeft() + ") (" + (ac.hasActivity() ? ac.getActivity().getName() : "idle") + ") (" + (cc.hasCommands() ? cc.getQueueLength() + " queued" : "-") + ")");
			}
		}
	}
}

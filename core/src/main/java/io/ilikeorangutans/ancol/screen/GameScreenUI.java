package io.ilikeorangutans.ancol.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.ilikeorangutans.ancol.game.activity.ActivityComponent;
import io.ilikeorangutans.ancol.game.cmd.ControllableComponent;
import io.ilikeorangutans.ancol.game.colonist.ColonistComponent;
import io.ilikeorangutans.ancol.game.colony.ColonyComponent;
import io.ilikeorangutans.ancol.game.colony.OpenColonyEvent;
import io.ilikeorangutans.ancol.game.turn.BeginTurnEvent;
import io.ilikeorangutans.ancol.input.InputProcessorFactory;
import io.ilikeorangutans.ancol.input.action.AnColActions;
import io.ilikeorangutans.ancol.map.viewport.ScreenToTile;
import io.ilikeorangutans.ancol.select.EntitySelectedEvent;
import io.ilikeorangutans.ancol.select.MultipleSelectOptionsEvent;
import io.ilikeorangutans.bus.EventBus;
import io.ilikeorangutans.bus.Subscribe;
import io.ilikeorangutans.ecs.ComponentType;
import io.ilikeorangutans.ecs.Entity;
import io.ilikeorangutans.ecs.event.EntityUpdatedEvent;

/**
 *
 */
public class GameScreenUI {

	private final EventBus bus;

	private final AnColActions actions;

	private Stage stage;
	private Skin skin;

	public GameScreenUI(EventBus bus, AnColActions actions) {
		this.bus = bus;
		this.actions = actions;
	}

	public void setupUI(Skin skin) {
		this.skin = skin;
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

	public void setupInputProcessing(InputProcessorFactory inputProcessorFactory, ScreenToTile screenToTile) {

		InputProcessor platformSpecific = inputProcessorFactory.create(bus, screenToTile, actions);
		bus.subscribe(platformSpecific);

		InputMultiplexer inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(stage);
		inputMultiplexer.addProcessor(platformSpecific);
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

	@Subscribe
	public void onOpenColony(final OpenColonyEvent event) {
		ColonyUI ui = new ColonyUI(stage, skin, event.colony);
		ui.setupAndShowUI();
	}

	@Subscribe
	public void onMultipleSelectOptions(MultipleSelectOptionsEvent e) {
		final Dialog d = new Dialog("Select", skin);

		EntityListItem[] items = new EntityListItem[e.selectable.size()];
		int i = 0;
		for (Entity entity : e.selectable) {
			items[i] = new EntityListItem(entity);
			i++;
		}

		final List<EntityListItem> list = new List<EntityListItem>(skin);
		list.setItems(items);
		list.setSelectedIndex(-1);
		list.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				bus.fire(new EntitySelectedEvent(list.getSelected().entity));

				d.hide();
				d.remove();
			}
		});

		ScrollPane scrollPane = new ScrollPane(list);
		d.getContentTable().add(scrollPane);
		d.getContentTable().pad(11);
		d.show(stage);
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
				tb2.setText("(nothing)");
			} else {
				StringBuilder sb = new StringBuilder();

				if (selected.hasComponent(ComponentType.fromClasses(ColonistComponent.class, ActivityComponent.class, ControllableComponent.class))) {
					ActivityComponent ac = selected.getComponent(ActivityComponent.class);
					ControllableComponent cc = selected.getComponent(ControllableComponent.class);
					ColonistComponent colonist = selected.getComponent(ColonistComponent.class);
					sb.append(colonist.getProfession().getName());
					sb.append(" (" + ac.getPointsLeft() + ") (" + (ac.hasActivity() ? ac.getActivity().getName() : "idle") + ") (" + (cc.hasCommands() ? cc.getQueueLength() + " queued" : "-") + ")");
				}

				if (selected.hasComponent(ComponentType.fromClass(ColonyComponent.class))) {
					ColonyComponent cc = selected.getComponent(ColonyComponent.class);
					sb.append(cc.getName());
					sb.append(" (Colony)");
				}

				tb2.setText(sb.toString());
			}
		}
	}

	private class EntityListItem {
		private final Entity entity;

		public EntityListItem(Entity entity) {
			this.entity = entity;
		}

		@Override
		public String toString() {
			if (entity.hasComponent(ComponentType.fromClass(ColonyComponent.class))) {
				return entity.getComponent(ColonyComponent.class).getName();
			} else {
				return entity.getComponent(ColonistComponent.class).getProfession().getName();
			}
		}
	}
}

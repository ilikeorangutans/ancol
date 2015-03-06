package io.ilikeorangutans.ancol.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.ilikeorangutans.ancol.game.activity.event.CannotPerformEvent;
import io.ilikeorangutans.ancol.game.colonist.ColonistComponent;
import io.ilikeorangutans.ancol.game.colony.ColonyComponent;
import io.ilikeorangutans.ancol.game.colony.event.OpenColonyEvent;
import io.ilikeorangutans.ancol.game.event.AllEntitiesSimulatedEvent;
import io.ilikeorangutans.ancol.game.mod.Mod;
import io.ilikeorangutans.ancol.game.player.Player;
import io.ilikeorangutans.ancol.game.player.event.BeginTurnEvent;
import io.ilikeorangutans.ancol.game.player.event.TurnConcludedEvent;
import io.ilikeorangutans.ancol.input.action.AnColActions;
import io.ilikeorangutans.ancol.map.viewport.ScreenToTile;
import io.ilikeorangutans.ancol.select.event.EntitySelectedEvent;
import io.ilikeorangutans.ancol.select.event.MultipleSelectOptionsEvent;
import io.ilikeorangutans.bus.EventBus;
import io.ilikeorangutans.bus.Subscribe;
import io.ilikeorangutans.ecs.ComponentType;
import io.ilikeorangutans.ecs.Entity;

/**
 *
 */
public class GameScreenUI {

	private final EventBus bus;

	private final Mod mod;
	private final AnColActions actions;
	private final Player player;
	private ScreenToTile screenToTile;

	private Stage stage;
	private Skin skin;

	public GameScreenUI(EventBus bus, Mod mod, AnColActions actions, Player player, ScreenToTile screenToTile) {
		this.bus = bus;
		this.mod = mod;
		this.actions = actions;
		this.player = player;
		this.screenToTile = screenToTile;
	}

	public void setupUI(Skin skin) {
		this.skin = skin;
		stage = new Stage(new ScreenViewport());
		Table table = new Table(skin);

		stage.addActor(table);

		TextButton tb;

		tb = new TextButton("END TURN", skin, "default");
		tb.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				bus.fire(new TurnConcludedEvent());
			}
		});

		table.add(tb).padLeft(22);
		table.bottom().center().pad(17).pack();


		setupSidebar(skin);
	}

	private void setupSidebar(final Skin skin) {
		SidebarUi sidebarUi = new SidebarUi(skin, stage, bus, actions, player, screenToTile);
		bus.subscribe(sidebarUi);
		sidebarUi.buildUI();
	}

	public InputProcessor getInputProcessor() {
		return stage;
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
		ColonyUI ui = new ColonyUI(stage, skin, mod, event.colony);
		ui.setupAndShowUI();
	}

	@Subscribe
	public void onAllEntitiesSimulated(AllEntitiesSimulatedEvent event) {
		if (!event.player.equals(player))
			return;
	}

	@Subscribe
	public void onCannotPerform(CannotPerformEvent event) {
		Dialog dialog = new Dialog("Cannot " + event.activity.getName(), skin);
		dialog.button("OK");
		dialog.show(stage);
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

	@Subscribe
	public void onBeginTurn(BeginTurnEvent event) {

		final Label label = new Label("Begin turn: " + event.player.getName(), skin);
		label.setFontScale(2.0F);
		label.setPosition(20, Gdx.graphics.getHeight() - 20 - label.getHeight());

		Timer timer = new Timer();
		timer.scheduleTask(new Timer.Task() {
			@Override
			public void run() {
				label.setVisible(false);
				label.remove();
			}
		}, 2);
		timer.start();

		stage.addActor(label);
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

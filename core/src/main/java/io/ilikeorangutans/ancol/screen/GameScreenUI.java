package io.ilikeorangutans.ancol.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.ilikeorangutans.ancol.Point;
import io.ilikeorangutans.ancol.game.activity.event.CannotPerformEvent;
import io.ilikeorangutans.ancol.game.cargo.Cargo;
import io.ilikeorangutans.ancol.game.cargo.CargoHoldComponent;
import io.ilikeorangutans.ancol.game.cargo.ShipComponent;
import io.ilikeorangutans.ancol.game.colonist.ColonistComponent;
import io.ilikeorangutans.ancol.game.colony.ColonyComponent;
import io.ilikeorangutans.ancol.game.colony.event.OpenColonyEvent;
import io.ilikeorangutans.ancol.game.event.AllEntitiesSimulatedEvent;
import io.ilikeorangutans.ancol.game.mod.Mod;
import io.ilikeorangutans.ancol.game.player.Player;
import io.ilikeorangutans.ancol.game.player.event.BeginTurnEvent;
import io.ilikeorangutans.ancol.game.player.event.TurnConcludedEvent;
import io.ilikeorangutans.ancol.input.MouseMoveEvent;
import io.ilikeorangutans.ancol.input.action.AnColActions;
import io.ilikeorangutans.ancol.map.GameMap;
import io.ilikeorangutans.ancol.map.tile.GameTile;
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
	private TextButton currentUnitButton;
	private Table cargoTable;
	private Label mousePosLabel;

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
		Table sidebar = new Table(skin);

		TextButton menuButton = new TextButton("Menu", skin);
		// TODO: this needs to be moved into it's own class listening to back keys as well
		menuButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				final Dialog dialog = new Dialog("Menu", skin);

				Table ct = dialog.getButtonTable();
				ct.add(new TextButton("Save", skin)).fillX();
				ct.row();
				ct.add(new TextButton("Load", skin)).fillX();
				ct.row();
				ct.add(new TextButton("Exit", skin)).fillX();

				ct.row().padTop(21);

				TextButton backButton = new TextButton("Back to Game", skin);
				backButton.addListener(new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						dialog.hide();
						dialog.remove();
					}
				});
				ct.add(backButton).fillX();

				dialog.show(stage);
			}
		});
		sidebar.add(menuButton).fillX().expandX();
		sidebar.row();

		sidebar.add(new Label("Map goes here", skin)).expandX().height(250).padBottom(11).colspan(2);
		sidebar.row();

		mousePosLabel = new Label("", skin);
		sidebar.add(mousePosLabel).center().fillX();
		sidebar.row();

		Label currentPlayerLabel = new Label("Current Player", skin);
		bus.subscribe(new CurrentPlayerListener(currentPlayerLabel));
		sidebar.add(currentPlayerLabel).center().colspan(2);
		sidebar.row();

		sidebar.add(new Label("XXX Year", skin));
		sidebar.add(new Label("XXX Gold", skin));
		sidebar.row();

		currentUnitButton = new TextButton("Selected", skin, "default");
		currentUnitButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				actions.getCenterViewAction().perform();
			}
		});
		sidebar.add(currentUnitButton).colspan(2).expandX();
		sidebar.row();

		cargoTable = new Table(skin);
		cargoTable.pad(11);
		sidebar.add(cargoTable);
		sidebar.row();

		TextButton nextOrEndTurnButton = new TextButton("Unit Needs Orders", skin);
		sidebar.add(nextOrEndTurnButton).bottom().fillX();
		NextUnitOrTurnButtonState nextUnitOrTurnButtonState = new NextUnitOrTurnButtonState(bus, player, nextOrEndTurnButton);
		bus.subscribe(nextUnitOrTurnButtonState);


		int width = 250;
		sidebar.align(Align.top);
		sidebar.setX(Gdx.graphics.getWidth() - width);
		sidebar.setHeight(Gdx.graphics.getHeight());
		sidebar.setWidth(width);
		//sidebar.debug();
		sidebar.setBackground("default-pane");
		stage.addActor(sidebar);
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
	public void onMouseMove(MouseMoveEvent event) {
		Point point = screenToTile.screenToTile(event.screenX, event.screenY);
		GameMap map = player.getMap();
		GameTile tile = map.getTileAt(point);

		StringBuilder sb = new StringBuilder();
		sb.append(point.x);
		sb.append('/');
		sb.append(point.y);
		sb.append(": ");
		sb.append(tile.getType().getName());
		mousePosLabel.setText(sb.toString());
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

	@Subscribe
	public void onEntitySelected(EntitySelectedEvent event) {
		Entity currentEntity = event.entity;

		// TODO: wouldn't it be better if we subscribed a listener to the current entity's cargo bay here? That way we
		// could react to changed cargo.
		updateCargoTable(currentEntity);

		currentUnitButton.setText(getEntityDescription(currentEntity));
	}

	private void updateCargoTable(Entity entity) {
		cargoTable.clear();

		if (entity == null || !entity.hasComponent(ComponentType.fromClass(CargoHoldComponent.class))) {
			return;
		}
		cargoTable.add(new Label("Cargo:", skin)).left().colspan(2);
		cargoTable.row();

		CargoHoldComponent chc = entity.getComponent(CargoHoldComponent.class);

		for (Cargo cargo : chc.getCargohold().getCargo()) {
			cargoTable.add(new Label(Integer.toString(cargo.getQuantity()), skin)).right().padRight(7);
			cargoTable.add(new Label(cargo.getTransportable().getDescription(), skin)).expandX().left();
			cargoTable.row();
		}
	}

	private String getEntityDescription(Entity entity) {

		if (entity == null)
			return "none";

		if (entity.hasComponent(ComponentType.fromClass(ColonistComponent.class))) {
			ColonistComponent colonist = entity.getComponent(ColonistComponent.class);
			return colonist.getProfession().getName();
		}

		if (entity.hasComponent(ComponentType.fromClass(ColonyComponent.class))) {
			ColonyComponent cc = entity.getComponent(ColonyComponent.class);
			return cc.getName();
		}

		if (entity.hasComponent(ComponentType.fromClass(ShipComponent.class))) {
			ShipComponent sc = entity.getComponent(ShipComponent.class);
			return sc.getName();
		}

		return "unknown";
	}

	public static class CurrentPlayerListener {
		private final Label tb2;

		public CurrentPlayerListener(Label tb2) {
			this.tb2 = tb2;
		}

		@Subscribe
		public void onBeginTurn(BeginTurnEvent bte) {
			tb2.setText(bte.player.getName());
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

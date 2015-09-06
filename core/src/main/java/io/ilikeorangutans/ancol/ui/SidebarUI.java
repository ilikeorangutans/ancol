package io.ilikeorangutans.ancol.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.ilikeorangutans.ancol.Point;
import io.ilikeorangutans.ancol.game.cargo.Cargo;
import io.ilikeorangutans.ancol.game.cargo.CargoHoldComponent;
import io.ilikeorangutans.ancol.game.cargo.ShipComponent;
import io.ilikeorangutans.ancol.game.colonist.ColonistComponent;
import io.ilikeorangutans.ancol.game.colony.ColonyComponent;
import io.ilikeorangutans.ancol.game.player.Player;
import io.ilikeorangutans.ancol.game.player.event.BeginTurnEvent;
import io.ilikeorangutans.ancol.input.MouseMoveEvent;
import io.ilikeorangutans.ancol.input.action.AnColActions;
import io.ilikeorangutans.ancol.map.GameMap;
import io.ilikeorangutans.ancol.map.tile.GameTile;
import io.ilikeorangutans.ancol.map.tile.TileYield;
import io.ilikeorangutans.ancol.map.viewport.ScreenToTile;
import io.ilikeorangutans.ancol.screen.NextUnitOrTurnButtonState;
import io.ilikeorangutans.ancol.select.event.EntitySelectedEvent;
import io.ilikeorangutans.bus.EventBus;
import io.ilikeorangutans.bus.Subscribe;
import io.ilikeorangutans.ecs.ComponentType;
import io.ilikeorangutans.ecs.Entity;

/**
 *
 */
public class SidebarUI {
	private final Skin skin;
	private final Stage stage;
	private final EventBus bus;
	private final AnColActions actions;
	private final Player player;
	private final ScreenToTile screenToTile;

	private Entity selectedEntity;
	private TextButton currentUnitButton;
	private Table cargoTable;
	private Label mousePosLabel;
	private Label currentPlayerLabel;

	public SidebarUI(Skin skin, Stage stage, EventBus bus, AnColActions actions, Player player, ScreenToTile screenToTile) {
		this.skin = skin;
		this.stage = stage;
		this.bus = bus;
		this.actions = actions;
		this.player = player;
		this.screenToTile = screenToTile;
	}

	public void buildUI() {
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

		currentPlayerLabel = new Label("Current Player", skin);
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
		sidebar.setX(Gdx.graphics.getWidth() - width);
		sidebar.setHeight(Gdx.graphics.getHeight());
		sidebar.setWidth(width);
		sidebar.setBackground("default-pane");
		stage.addActor(sidebar);
	}

	@Subscribe
	public void onBeginTurn(BeginTurnEvent event) {
		Player currentPlayer = event.player;
		currentPlayerLabel.setText(currentPlayer.getName());
	}

	@Subscribe
	public void onEntitySelected(EntitySelectedEvent event) {
		selectedEntity = event.entity;
		Entity currentEntity = event.entity;

		// TODO: wouldn't it be better if we subscribed a listener to the current entity's cargo bay here? That way we
		// could react to changed cargo.
		updateCargoTable(currentEntity);

		currentUnitButton.setText(getEntityDescription(currentEntity));

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

		sb.append("\n");

		for (TileYield yield : tile.getType().getYield()) {
			sb.append("  ");
			sb.append(yield.getWare().getName());
			sb.append(":");
			sb.append(yield.getAmount());
			sb.append("\n");
		}
		mousePosLabel.setText(sb.toString());
	}

}


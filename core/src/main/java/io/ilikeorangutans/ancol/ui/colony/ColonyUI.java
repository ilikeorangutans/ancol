package io.ilikeorangutans.ancol.ui.colony;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import io.ilikeorangutans.ancol.game.colonist.ColonistComponent;
import io.ilikeorangutans.ancol.game.colonist.Job;
import io.ilikeorangutans.ancol.game.colony.ColonistsImpl;
import io.ilikeorangutans.ancol.game.colony.ColonyComponent;
import io.ilikeorangutans.ancol.game.colony.building.Building;
import io.ilikeorangutans.ancol.game.colony.building.ColonyBuildings;
import io.ilikeorangutans.ancol.game.mod.Mod;
import io.ilikeorangutans.ancol.game.production.Workplace;
import io.ilikeorangutans.ancol.game.ware.Warehouse;
import io.ilikeorangutans.ancol.map.surrounding.Surroundings;
import io.ilikeorangutans.ancol.map.tile.GameTile;
import io.ilikeorangutans.ancol.ui.JobSelect;
import io.ilikeorangutans.ecs.Entity;

import java.util.Observable;
import java.util.Observer;

/**
 *
 */
public class ColonyUI implements Observer {
	private final Stage stage;
	private final Skin skin;
	private final Mod mod;
	private final TextureAtlas atlas;
	private final Entity entity;
	private final DragAndDrop dragAndDrop;

	public ColonyUI(Stage stage, Skin skin, Mod mod, TextureAtlas atlas, Entity colony) {
		this.stage = stage;
		this.skin = skin;
		this.mod = mod;
		this.atlas = atlas;
		this.entity = colony;
		dragAndDrop = new DragAndDrop();
	}

	public void setupAndShowUI() {
		final ColonyComponent colony = entity.getComponent(ColonyComponent.class);
		colony.addObserver(this);

		final Window window = new Window(colony.getName(), skin);
		window.setResizable(true);
		window.setModal(true);
		window.pad(22, 7, 7, 7);
		window.addListener(new InputListener() {
			@Override
			public boolean keyUp(InputEvent event, int keycode) {
				if (keycode == Input.Keys.ESCAPE) {
					closeWindow(window);
					return true;
				}
				return super.keyUp(event, keycode);
			}
		});
		window.setPosition((Gdx.graphics.getWidth() / 2) - 384, Gdx.graphics.getHeight());

		window.add(new Label("Buildings", skin)).expand();
		window.add(new Label("Surroundings", skin)).width(268);

		window.row().height(268);

		addBuildings(colony, window);
		addColonyMap(colony, window);

		window.row();

		addColonistTable(colony, window);
		window.add(new Label("(Production window goes here)", skin));

		window.row();

		addWares(colony, window);
		addButtons(colony, window);

		window.pack();
		stage.addActor(window);
	}

	private void addBuildings(ColonyComponent colony, Window window) {
		Table group = new Table();
		group.pad(3);

		ColonyBuildings buildings = colony.getBuildings();

		int counter = 1;
		for (Building b : buildings.getBuildings()) {
			BuildingUI buildingUI = new BuildingUI(atlas, colony, b, dragAndDrop, skin);
			dragAndDrop.addTarget(new WorkplaceTarget(buildingUI, colony, b, b, new JobSelectUI(skin, stage)));
			group.add(buildingUI);

			if (counter % 3 == 0)
				group.row();
			counter++;
		}

		window.add(group);
	}

	private void addWares(ColonyComponent colony, Window window) {
		Warehouse warehouse = colony.getWarehouse();
		WarehouseAndProductionUI warehouseAndProductionUI = new WarehouseAndProductionUI(skin, dragAndDrop, atlas, warehouse, colony.getProduction());
		colony.getLocalBus().subscribe(warehouseAndProductionUI);
		window.add(warehouseAndProductionUI).expandX();
	}

	private void addColonyMap(ColonyComponent colony, Window window) {
		Table surroundingTable = new Table(skin);
		surroundingTable.columnDefaults(0).width(80);
		surroundingTable.columnDefaults(1).width(80);
		surroundingTable.columnDefaults(2).width(80);

		int counter = 0;
		for (Surroundings.Selector selector : Surroundings.Selector.values()) {

			if (counter % 3 == 0) {
				surroundingTable.row().height(80);
			}

			GameTile tile = colony.getSurroundings().getTile(selector);
			Workplace workplace = colony.getWorkplaces().getForTile(tile);

			// TODO: add wrapper around GameTile that implements Colonists so we can keep track of who's there
			Label label = new Label(tile.getType().getName(), skin);
			JobSelect jobSelect = new JobSelectUI(skin, stage);
			dragAndDrop.addTarget(new WorkplaceTarget(label, colony, workplace, new ColonistsImpl(), jobSelect));
			surroundingTable.add(label);
			counter++;
		}

		window.add(surroundingTable).expand();
	}

	private void addColonistTable(final ColonyComponent colony, Window window) {
		HorizontalGroup group = new HorizontalGroup();
		group.fill();

		ColonistsUI populationUI = new ColonistsUI(colony.getPopulation(), atlas, dragAndDrop);
		group.addActor(populationUI);

		ColonistsUI outsideUI = new ColonistsUI(colony.getOutsideColonists(), atlas, dragAndDrop);
		group.addActor(outsideUI);

		window.add(group).fillX();
	}

	private void addButtons(final ColonyComponent colony, final Window window) {
		Table buttons = new Table(skin);

		window.add(buttons).expandX().expandY().bottom().right();
		TextButton renameButton = new TextButton("Rename", skin);
		renameButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent e, float x, float y) {
				Dialog d = new Dialog("Rename Colony", skin);
				final TextField textField = d.getContentTable().add(new TextField(colony.getName(), skin)).getActor();

				d.button(new TextButton("Rename", skin)).addListener(new ClickListener() {
					@Override
					public void clicked(InputEvent e, float x, float y) {
						colony.setName(textField.getText());
						window.setTitle(colony.getName());
						entity.updated();
					}
				});
				d.button(new TextButton("Cancel", skin));
				d.show(stage);
			}
		});
		buttons.add(renameButton).padBottom(11).fillX().width(100);
		buttons.row();
		TextButton closeButton = new TextButton("Close", skin);
		closeButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				closeWindow(window);
			}
		});
		buttons.add(closeButton).fillX();
	}

	private void closeWindow(Window window) {
		window.setVisible(false);
		window.remove();
	}

	@Override
	public void update(Observable o, Object arg) {
		refresh();
	}

	private void refresh() {

	}

	private class ColonistInColony {
		final Entity entity;

		public ColonistInColony(Entity entity) {
			this.entity = entity;
		}


		@Override
		public String toString() {

			ColonistComponent colonist = entity.getComponent(ColonistComponent.class);

			return colonist.getJob().getName() + " (" + colonist.getProfession().getName() + ")";
		}
	}

	private class JobSelection {
		final Job job;

		public JobSelection(Job job) {
			this.job = job;
		}

		@Override
		public String toString() {
			return job.getName();
		}
	}

	private class ColonistOutsideColony {

		final Entity colonist;

		public ColonistOutsideColony(Entity colonist) {
			this.colonist = colonist;
		}

		@Override
		public String toString() {
			return colonist.getComponent(ColonistComponent.class).getProfession().getName();
		}
	}

}

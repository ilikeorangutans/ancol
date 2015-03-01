package io.ilikeorangutans.ancol.game.rule;

import com.badlogic.gdx.Gdx;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.ilikeorangutans.ancol.game.colonist.AvailableProfessions;
import io.ilikeorangutans.ancol.game.colony.building.AvailableBuildings;
import io.ilikeorangutans.ancol.game.colony.building.BuildingType;
import io.ilikeorangutans.ancol.game.ware.AvailableWares;
import io.ilikeorangutans.ancol.map.tile.SimpleTileTypes;
import io.ilikeorangutans.ancol.map.tile.TileTypes;

import static com.google.common.base.Strings.isNullOrEmpty;

/**
 *
 */
public class Rules {

	private SimpleTileTypes tileTypes;
	private AvailableProfessions professions;

	private AvailableBuildings buildings;
	private AvailableWares wares;

	public AvailableWares getWares() {
		return wares;
	}


	public void loadFromJSON() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		wares = gson.fromJson(Gdx.files.internal("ancol/wares.json").reader(), AvailableWares.class);
		tileTypes = gson.fromJson(Gdx.files.internal("ancol/tiletypes.json").reader(), SimpleTileTypes.class);

		tileTypes.postProcess(wares);

		buildings = gson.fromJson(Gdx.files.internal("ancol/buildings.json").reader(), AvailableBuildings.class);

		for (BuildingType bt : buildings.getBuildings()) {
			if (!isNullOrEmpty(bt.getUpgradeString())) {
				bt.setUpgrade(buildings.findByName(bt.getUpgradeString()));
			}
			if (!isNullOrEmpty(bt.getInputString())) {
				bt.setInput(wares.findByName(bt.getInputString()));
			}
			if (!isNullOrEmpty(bt.getOutputString())) {
				bt.setOutput(wares.findByName(bt.getOutputString()));
			}
		}

		professions = gson.fromJson(Gdx.files.internal("ancol/professions.json").reader(), AvailableProfessions.class);

		professions.postProcess(wares);
	}

	public AvailableBuildings getBuildings() {
		return buildings;
	}

	public TileTypes getTileTypes() {
		return tileTypes;
	}

	public AvailableProfessions getProfessions() {
		return professions;
	}

}

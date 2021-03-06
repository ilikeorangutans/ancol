package io.ilikeorangutans.ancol.game.mod;

import com.badlogic.gdx.Gdx;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.ilikeorangutans.ancol.game.ability.AvailableAbilities;
import io.ilikeorangutans.ancol.game.colonist.AvailableProfessions;
import io.ilikeorangutans.ancol.game.colony.building.AvailableBuildings;
import io.ilikeorangutans.ancol.game.ware.AvailableWares;
import io.ilikeorangutans.ancol.map.tile.SimpleTileTypes;
import io.ilikeorangutans.ancol.map.tile.TileTypes;

/**
 *
 */
public class Mod {

	private SimpleTileTypes tileTypes;
	private AvailableProfessions professions;
	private AvailableBuildings buildings;
	private AvailableWares wares;
	private AvailableAbilities abilities = new AvailableAbilities();

	public AvailableAbilities getAbilities() {
		return abilities;
	}

	public AvailableWares getWares() {
		return wares;
	}


	public void loadFromJSON() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		wares = gson.fromJson(Gdx.files.internal("ancol/wares.json").reader(), AvailableWares.class);
		tileTypes = gson.fromJson(Gdx.files.internal("ancol/tiletypes.json").reader(), SimpleTileTypes.class);

		tileTypes.postProcess(wares);

		professions = gson.fromJson(Gdx.files.internal("ancol/professions.json").reader(), AvailableProfessions.class);
		professions.postProcess(wares);

		buildings = gson.fromJson(Gdx.files.internal("ancol/buildings.json").reader(), AvailableBuildings.class);
		buildings.postProcess(wares, professions);

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

package io.ilikeorangutans.ancol.game;

import com.badlogic.gdx.Gdx;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.ilikeorangutans.ancol.input.action.AnColActions;
import io.ilikeorangutans.ancol.map.Map;
import io.ilikeorangutans.ancol.map.RandomMap;
import io.ilikeorangutans.ancol.map.tile.SimpleTileTypes;
import io.ilikeorangutans.ancol.map.tile.TileTypes;
import io.ilikeorangutans.ancol.path.AStarPathFinder;
import io.ilikeorangutans.ancol.path.PathFinder;
import io.ilikeorangutans.bus.EventBus;

/**
 *
 */
public class Startup {

	private final EventBus bus;
	private TileTypes tileTypes;
	private PathFinder pathFinder;

	private Map map;
	private AnColActions actions;

	public Startup(EventBus bus) {
		this.bus = bus;
	}

	public Map getMap() {
		return map;
	}

	public PathFinder getPathFinder() {
		return pathFinder;
	}

	public AnColActions getActions() {
		return actions;
	}

	public void startSampleGame() {
		loadFromJSON();
		map = new RandomMap(tileTypes);
		pathFinder = new AStarPathFinder(map);
		actions = new AnColActions(bus, pathFinder);
	}

	public void loadFromJSON() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		tileTypes = gson.fromJson(Gdx.files.internal("ancol/tiletypes.json").reader(), SimpleTileTypes.class);
	}

	public TileTypes getTileTypes() {
		return tileTypes;
	}



}

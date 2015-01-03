package io.ilikeorangutans.ancol.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import io.ilikeorangutans.ancol.game.Startup;
import io.ilikeorangutans.ancol.game.activity.ActivityComponent;
import io.ilikeorangutans.ancol.game.activity.ActivitySystem;
import io.ilikeorangutans.ancol.game.cmd.CommandEventHandler;
import io.ilikeorangutans.ancol.game.cmd.ControllableComponent;
import io.ilikeorangutans.ancol.game.colonist.ColonistComponent;
import io.ilikeorangutans.ancol.game.colonist.Profession;
import io.ilikeorangutans.ancol.game.colony.ColonyHandler;
import io.ilikeorangutans.ancol.game.player.*;
import io.ilikeorangutans.ancol.game.vision.VisionComponent;
import io.ilikeorangutans.ancol.graphics.AnColRenderer;
import io.ilikeorangutans.ancol.graphics.RenderableComponent;
import io.ilikeorangutans.ancol.input.InputProcessorFactory;
import io.ilikeorangutans.ancol.map.Map;
import io.ilikeorangutans.ancol.map.PlayerVisibilityMap;
import io.ilikeorangutans.ancol.map.PositionComponent;
import io.ilikeorangutans.ancol.map.tile.TileTypes;
import io.ilikeorangutans.ancol.map.viewport.MapViewport;
import io.ilikeorangutans.ancol.move.MovableComponent;
import io.ilikeorangutans.ancol.select.SelectableComponent;
import io.ilikeorangutans.ancol.select.SelectionHandler;
import io.ilikeorangutans.bus.EventBus;
import io.ilikeorangutans.bus.SimpleEventBus;
import io.ilikeorangutans.ecs.Facade;
import io.ilikeorangutans.ecs.NameComponent;

/**
 *
 */
public class GameScreen implements Screen {

	private final Game game;
	private final GameScreenUI ui;
	private final EventBus bus;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Facade facade;
	private MapViewport viewport;
	private AnColRenderer renderer;

	public GameScreen(Game game, Skin skin, InputProcessorFactory inputProcessorFactory) {
		this.game = game;
		bus = new SimpleEventBus();

		Startup startup = new Startup(bus);
		startup.startSampleGame();

		TileTypes tileTypes = startup.getTileTypes();

		ui = new GameScreenUI(bus, startup.getActions());
		bus.subscribe(ui);
		ui.setupUI(skin);

		Player p1 = new Player(1, "player 1");
		Player p2 = new Player(2, "player 2");

		PlayerTurnSystem playerTurnSystem = new PlayerTurnSystem(bus);
		bus.subscribe(playerTurnSystem);

		playerTurnSystem.addPlayer(p1);
		playerTurnSystem.addPlayer(p2);

		Map playerMap = new PlayerVisibilityMap(startup.getMap(), p1, tileTypes.getTypeForId(0));
		bus.subscribe(playerMap);
		viewport = new MapViewport(bus, 30, 30, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 60, 60, playerMap);
		ui.setupInputProcessing(inputProcessorFactory, viewport);

		setupRendering();

		bus.subscribe(viewport);

		facade = new Facade(bus);
		facade.init();

		SimplePlayerEntities p1Entities = new SimplePlayerEntities(facade.getEntities(), p1);

		SelectionHandler selectionHandler = new SelectionHandler(facade.getEntities(), bus);
		bus.subscribe(selectionHandler);

		CommandEventHandler commandManager = new CommandEventHandler(bus);
		bus.subscribe(commandManager);

		ActivitySystem actionPointSystem = new ActivitySystem(bus, facade.getEntities());
		bus.subscribe(actionPointSystem);

		ColonyHandler colonyHandler = new ColonyHandler(bus, facade.getEntities(), facade.getEntities(), playerMap);
		bus.subscribe(colonyHandler);

		NextUnitPicker nextUnitPicker = new NextUnitPicker(bus, p1, p1Entities);
		bus.subscribe(nextUnitPicker);

		renderer = new AnColRenderer(batch, viewport, playerMap, facade.getEntities());

		setupSampleEntities(p1, playerMap);

		playerTurnSystem.start();
	}

	private void setupSampleEntities(Player p1, Map map) {
		Profession profession = new Profession("Free Colonist");
		facade.getEntities().create(
				new PositionComponent(11, 10),
				new RenderableComponent(1),
				new NameComponent("test entity 1"),
				new SelectableComponent(),
				new MovableComponent(map),
				new PlayerOwnedComponent(p1),
				new ControllableComponent(),
				new ActivityComponent(2),
				new VisionComponent(1),
				new ColonistComponent(profession));
		facade.getEntities().create(
				new PositionComponent(6, 5),
				new RenderableComponent(1),
				new NameComponent("test entity 2"),
				new SelectableComponent(),
				new MovableComponent(map),
				new PlayerOwnedComponent(p1),
				new ControllableComponent(),
				new ActivityComponent(2),
				new VisionComponent(1),
				new ColonistComponent(profession));
		facade.getEntities().create(
				new PositionComponent(5, 3),
				new RenderableComponent(1),
				new NameComponent("test entity 3"),
				new SelectableComponent(),
				new MovableComponent(map),
				new PlayerOwnedComponent(p1),
				new ControllableComponent(),
				new ActivityComponent(2),
				new VisionComponent(2),
				new ColonistComponent(new Profession("Seasoned Scout")));
	}

	private void setupRendering() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false);
		batch = new SpriteBatch();
	}

	@Override
	public void render(float delta) {

		bus.processQueue();

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		batch.setProjectionMatrix(camera.combined);
		renderer.render();

		ui.render();
	}

	@Override
	public void resize(int width, int height) {
		ui.resize(width, height);
		viewport.resize(width, height);
	}

	@Override
	public void show() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
		ui.dispose();
		batch.dispose();
	}

}

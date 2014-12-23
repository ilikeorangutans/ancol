package io.ilikeorangutans.ancol.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import io.ilikeorangutans.ancol.game.Player;
import io.ilikeorangutans.ancol.game.PlayerOwnedComponent;
import io.ilikeorangutans.ancol.game.activity.ActivityComponent;
import io.ilikeorangutans.ancol.game.activity.ActivitySystem;
import io.ilikeorangutans.ancol.game.cmd.CommandEventHandler;
import io.ilikeorangutans.ancol.game.cmd.ControllableComponent;
import io.ilikeorangutans.ancol.game.turn.PlayerTurnSystem;
import io.ilikeorangutans.ancol.game.vision.VisionComponent;
import io.ilikeorangutans.ancol.graphics.AnColRenderer;
import io.ilikeorangutans.ancol.graphics.RenderableComponent;
import io.ilikeorangutans.ancol.input.action.AnColActions;
import io.ilikeorangutans.ancol.map.Map;
import io.ilikeorangutans.ancol.map.PlayerVisibilityMap;
import io.ilikeorangutans.ancol.map.PositionComponent;
import io.ilikeorangutans.ancol.map.RandomMap;
import io.ilikeorangutans.ancol.map.tile.TileTypes;
import io.ilikeorangutans.ancol.map.viewport.MapViewport;
import io.ilikeorangutans.ancol.move.MovableComponent;
import io.ilikeorangutans.ancol.path.AStarPathFinder;
import io.ilikeorangutans.ancol.path.PathFinder;
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

	public GameScreen(Game game, Skin skin) {
		this.game = game;
		bus = new SimpleEventBus();

		TileTypes tileTypes = new TileTypes();
		Map map = new RandomMap(tileTypes);
		PathFinder pathFinder = new AStarPathFinder(map);

		AnColActions actions = new AnColActions(bus, pathFinder);
		ui = new GameScreenUI(bus, actions, skin);
		ui.setupUI(skin);

		Player p1 = new Player(1, "player 1");
		Player p2 = new Player(2, "player 2");

		PlayerTurnSystem playerTurnSystem = new PlayerTurnSystem(bus);
		bus.subscribe(playerTurnSystem);

		playerTurnSystem.addPlayer(p1);
		playerTurnSystem.addPlayer(p2);

		Map playerMap = new PlayerVisibilityMap(map, p1, tileTypes.getTypeForId(0));
		bus.subscribe(playerMap);
		viewport = new MapViewport(bus, 30, 30, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 60, 60, playerMap);
		ui.setupInputProcessing(viewport);

		setupRendering();

		bus.subscribe(viewport);

		facade = new Facade(bus);
		facade.init();

		SelectionHandler selectionHandler = new SelectionHandler(facade.getEntities(), bus);
		bus.subscribe(selectionHandler);

		CommandEventHandler commandManager = new CommandEventHandler(bus);
		bus.subscribe(commandManager);

		ActivitySystem actionPointSystem = new ActivitySystem(bus, facade.getEntities());
		bus.subscribe(actionPointSystem);

		renderer = new AnColRenderer(batch, viewport, playerMap, facade.getEntities());

		setupSampleEntities(p1, playerMap);

		playerTurnSystem.start();
	}

	private void setupSampleEntities(Player p1, Map map) {
		facade.getEntities().create(
				new PositionComponent(11, 10),
				new RenderableComponent(),
				new NameComponent("test entity 1"),
				new SelectableComponent(),
				new MovableComponent(map),
				new PlayerOwnedComponent(p1),
				new ControllableComponent(),
				new ActivityComponent(2),
				new VisionComponent(1));
		facade.getEntities().create(
				new PositionComponent(6, 5),
				new RenderableComponent(),
				new NameComponent("test entity 2"),
				new SelectableComponent(),
				new MovableComponent(map),
				new PlayerOwnedComponent(p1),
				new ControllableComponent(),
				new ActivityComponent(2),
				new VisionComponent(1));
		facade.getEntities().create(
				new PositionComponent(5, 3),
				new RenderableComponent(),
				new NameComponent("test entity 3"),
				new SelectableComponent(),
				new MovableComponent(map),
				new PlayerOwnedComponent(p1),
				new ControllableComponent(),
				new ActivityComponent(2),
				new VisionComponent(1));
	}

	private void setupRendering() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false);
		batch = new SpriteBatch();
	}

	@Override
	public void render(float delta) {

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

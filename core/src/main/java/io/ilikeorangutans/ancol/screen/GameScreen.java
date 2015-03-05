package io.ilikeorangutans.ancol.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import io.ilikeorangutans.ancol.game.GameState;
import io.ilikeorangutans.ancol.game.GameStateBuilder;
import io.ilikeorangutans.ancol.game.activity.ActivitySystem;
import io.ilikeorangutans.ancol.game.cmd.CommandEventHandler;
import io.ilikeorangutans.ancol.game.colony.ColonyHandler;
import io.ilikeorangutans.ancol.game.event.GameStartedEvent;
import io.ilikeorangutans.ancol.game.player.NextUnitPicker;
import io.ilikeorangutans.ancol.game.player.Player;
import io.ilikeorangutans.ancol.game.player.PlayerTurnHandler;
import io.ilikeorangutans.ancol.game.player.SimplePlayerEntities;
import io.ilikeorangutans.ancol.game.mod.Mod;
import io.ilikeorangutans.ancol.graphics.AnColRenderer;
import io.ilikeorangutans.ancol.input.InputProcessorFactory;
import io.ilikeorangutans.ancol.input.action.AnColActions;
import io.ilikeorangutans.ancol.map.viewport.MapViewport;
import io.ilikeorangutans.ancol.path.AStarPathFinder;
import io.ilikeorangutans.ancol.path.PathFinder;
import io.ilikeorangutans.ancol.select.SelectionHandler;
import io.ilikeorangutans.bus.EventBus;
import io.ilikeorangutans.bus.SimpleEventBus;
import io.ilikeorangutans.ecs.EntitiesEntityFactory;
import io.ilikeorangutans.ecs.SimpleEntities;

/**
 *
 */
public class GameScreen implements Screen {

	private final EventBus bus;
	private final Skin skin;
	private final InputProcessorFactory inputProcessorFactory;
	private GameScreenUI ui;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private AnColRenderer renderer;
	private PathFinder pathFinder;
	private MapViewport viewport;

	public GameScreen(Skin skin, InputProcessorFactory inputProcessorFactory) {
		this.skin = skin;
		this.inputProcessorFactory = inputProcessorFactory;

		// Infrastructure:
		bus = new SimpleEventBus();
		EntitiesEntityFactory entities = new SimpleEntities(bus);

		// Game rules (tile types, professions, etc)
		Mod mod = new Mod();
		mod.loadFromJSON();

		// Game data (map, players, nations, players' entities)
		GameStateBuilder gameStateBuilder = new GameStateBuilder(bus, mod);
		gameStateBuilder.withEntities(entities);
		GameState gameState = gameStateBuilder.setupSampleGame();

		// Game mechanics
		setupGameMechanics(bus, entities, mod, gameState);

		// Setup Game UI for player:
		Player player = gameStateBuilder.getLocalPlayer();
		setupUIForPlayer(bus, mod, entities, player);

		// Start the game:
		bus.fire(new GameStartedEvent());

	}

	private void setupUIForPlayer(EventBus bus, Mod mod, EntitiesEntityFactory entities, Player player) {
		AnColActions actions = new AnColActions(bus, pathFinder);
		viewport = new MapViewport(bus, 30, 30, Gdx.graphics.getWidth() - 250, Gdx.graphics.getHeight(), 60, 60, player.getMap());
		bus.subscribe(viewport);

		ui = new GameScreenUI(bus, mod, actions, player, viewport);
		bus.subscribe(ui);
		ui.setupUI(skin);

		InputProcessor platformSpecific = inputProcessorFactory.create(bus, viewport, actions);
		bus.subscribe(platformSpecific);

		setupInputProcessing(ui.getInputProcessor(), platformSpecific);

		setupRendering();

		SimplePlayerEntities p1Entities = new SimplePlayerEntities(entities, player);

		NextUnitPicker nextUnitPicker = new NextUnitPicker(bus, player, p1Entities);
		bus.subscribe(nextUnitPicker);

		renderer = new AnColRenderer(batch, viewport, player.getMap(), entities, mod);
	}

	private void setupGameMechanics(EventBus bus, EntitiesEntityFactory entities, Mod mod, GameState gameState) {
		SelectionHandler selectionHandler = new SelectionHandler(entities, bus);
		bus.subscribe(selectionHandler);

		ColonyHandler colonyHandler = new ColonyHandler(bus, entities, mod);
		bus.subscribe(colonyHandler);

		ActivitySystem actionPointSystem = new ActivitySystem(bus, entities);
		bus.subscribe(actionPointSystem);

		CommandEventHandler commandManager = new CommandEventHandler(bus);
		bus.subscribe(commandManager);

		pathFinder = new AStarPathFinder(gameState.getMap().getHeight(), gameState.getMap().getWidth());

		PlayerTurnHandler playerTurnSystem = new PlayerTurnHandler(bus, gameState);
		bus.subscribe(playerTurnSystem);

	}

	public void setupInputProcessing(InputProcessor... inputProcessors) {

		InputMultiplexer inputMultiplexer = new InputMultiplexer();

		for (InputProcessor inputProcessor : inputProcessors) {
			inputMultiplexer.addProcessor(inputProcessor);
		}

		Gdx.input.setInputProcessor(inputMultiplexer);

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
		// TODO: need to find a better way to deal with the sidebar width here
		viewport.resize(width - 250, height);
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

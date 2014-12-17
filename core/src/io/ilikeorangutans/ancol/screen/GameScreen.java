package io.ilikeorangutans.ancol.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.ilikeorangutans.ancol.game.Player;
import io.ilikeorangutans.ancol.game.PlayerOwnedComponent;
import io.ilikeorangutans.ancol.game.activity.ActivityComponent;
import io.ilikeorangutans.ancol.game.activity.ActivitySystem;
import io.ilikeorangutans.ancol.game.cmd.BuildColonyCommand;
import io.ilikeorangutans.ancol.game.cmd.CommandEventHandler;
import io.ilikeorangutans.ancol.game.cmd.ControllableComponent;
import io.ilikeorangutans.ancol.game.cmd.ImproveTileCommand;
import io.ilikeorangutans.ancol.game.cmd.event.CommandEvent;
import io.ilikeorangutans.ancol.game.turn.BeginTurnEvent;
import io.ilikeorangutans.ancol.game.turn.PlayerTurnSystem;
import io.ilikeorangutans.ancol.game.turn.TurnConcludedEvent;
import io.ilikeorangutans.ancol.graphics.AnColRenderer;
import io.ilikeorangutans.ancol.graphics.RenderableComponent;
import io.ilikeorangutans.ancol.input.AnColInputProcessor;
import io.ilikeorangutans.ancol.map.Map;
import io.ilikeorangutans.ancol.map.MapViewport;
import io.ilikeorangutans.ancol.map.PositionComponent;
import io.ilikeorangutans.ancol.map.RandomMap;
import io.ilikeorangutans.ancol.move.MovableComponent;
import io.ilikeorangutans.ancol.move.MoveSystem;
import io.ilikeorangutans.ancol.path.DumbPathFinder;
import io.ilikeorangutans.ancol.select.SelectableComponent;
import io.ilikeorangutans.ancol.select.SelectedEvent;
import io.ilikeorangutans.ancol.select.SelectionHandler;
import io.ilikeorangutans.bus.EventBus;
import io.ilikeorangutans.bus.SimpleEventBus;
import io.ilikeorangutans.bus.Subscribe;
import io.ilikeorangutans.ecs.Engine;
import io.ilikeorangutans.ecs.Entity;
import io.ilikeorangutans.ecs.Facade;
import io.ilikeorangutans.ecs.NameComponent;
import io.ilikeorangutans.ecs.event.EntityUpdatedEvent;

/**
 *
 */
public class GameScreen implements Screen {

	private final Game game;
	/**
	 * We use this engine to perform turn based updates as opposed to continuous updates.
	 */
	private final Engine turnbasedEngine;
	private Stage stage;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Facade facade;
	private EventBus bus;
	private MapViewport viewport;
	private AnColRenderer renderer;


	public GameScreen(Game game, Skin skin) {
		this.game = game;

		bus = new SimpleEventBus();

		Map map = new RandomMap();
		viewport = new MapViewport(bus, 30, 30, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 60, 60, map);

		setupUI(skin);
		setupInputProcessing();
		setupRendering();

		bus.subscribe(viewport);

		facade = new Facade(bus);
		facade.init();

		SelectionHandler selectionHandler = new SelectionHandler(facade.getEntities(), bus);
		bus.subscribe(selectionHandler);

		CommandEventHandler commandManager = new CommandEventHandler(bus);
		bus.subscribe(commandManager);

		turnbasedEngine = new Engine();

		PlayerTurnSystem playerTurnSystem = new PlayerTurnSystem(bus);
		bus.subscribe(playerTurnSystem);

		Player p1 = new Player(1, "player 1");
		Player p2 = new Player(2, "player 2");

		playerTurnSystem.addPlayer(p1);
		playerTurnSystem.addPlayer(p2);


		ActivitySystem actionPointSystem = new ActivitySystem(bus, facade.getEntities());
		bus.subscribe(actionPointSystem);

		MoveSystem moveSystem = new MoveSystem(facade.getEntities(), bus);
		bus.subscribe(moveSystem);
		turnbasedEngine.add(moveSystem);

		renderer = new AnColRenderer(batch, viewport, map, facade.getEntities());

		setupSampleEntities(p1);

		playerTurnSystem.start();
	}

	private void setupSampleEntities(Player p1) {
		facade.getEntities().create(
				new PositionComponent(10, 10),
				new RenderableComponent(),
				new NameComponent("test entity 1"),
				new SelectableComponent(),
				new MovableComponent(),
				new PlayerOwnedComponent(p1),
				new ControllableComponent(),
				new ActivityComponent(2));
		facade.getEntities().create(
				new PositionComponent(4, 4),
				new RenderableComponent(),
				new NameComponent("test entity 2"),
				new SelectableComponent(),
				new MovableComponent(),
				new PlayerOwnedComponent(p1),
				new ControllableComponent(),
				new ActivityComponent(2));
		facade.getEntities().create(
				new PositionComponent(1, 3),
				new RenderableComponent(),
				new NameComponent("test entity 3"),
				new SelectableComponent(),
				new MovableComponent(),
				new PlayerOwnedComponent(p1),
				new ControllableComponent(),
				new ActivityComponent(2));
	}

	private void setupRendering() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false);
		batch = new SpriteBatch();
	}

	private void setupUI(Skin skin) {
		stage = new Stage(new ScreenViewport());
		Table table = new Table(skin);
		table.left().bottom();
		stage.addActor(table);

		TextButton tb;

		final TextButton tb2 = new TextButton("Current Player", skin, "default");
		tb2.setDisabled(true);

		bus.subscribe(new CurrentPlayerListener(tb2));
		table.add(tb2).pad(17);

		TextButton tb3 = new TextButton("Selected Unit (Points) (activity) (queue)", skin, "default");
		tb3.setDisabled(true);
		bus.subscribe(new SelectedUnitListener(tb3));
		table.add(tb3).pad(17);

		tb = new TextButton("Fortify", skin, "default");
		table.add(tb);

		tb = new TextButton("Colony", skin, "default");
		tb.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				bus.fire(new CommandEvent(new BuildColonyCommand()));
			}
		});
		table.add(tb);

		tb = new TextButton("Road", skin, "default");
		tb.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
			}
		});
		table.add(tb);

		tb = new TextButton("Improve", skin, "default");
		tb.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				bus.fire(new CommandEvent(new ImproveTileCommand()));
			}
		});
		table.add(tb);

		tb = new TextButton("End Turn", skin, "default");
		tb.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				bus.fire(new TurnConcludedEvent());
			}
		});
		table.add(tb).pad(34);

	}

	private void setupInputProcessing() {
		InputMultiplexer inputMultiplexer = new InputMultiplexer();
		AnColInputProcessor anColInputProcessor = new AnColInputProcessor(bus, viewport, new DumbPathFinder());
		bus.subscribe(anColInputProcessor);
		inputMultiplexer.addProcessor(stage);
		inputMultiplexer.addProcessor(anColInputProcessor);
		Gdx.input.setInputProcessor(inputMultiplexer);
	}

	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		batch.setProjectionMatrix(camera.combined);
		renderer.render();

		stage.act();
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
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
		stage.dispose();
		batch.dispose();
	}

	public static class CurrentPlayerListener {
		private final TextButton tb2;

		public CurrentPlayerListener(TextButton tb2) {
			this.tb2 = tb2;
		}

		@Subscribe
		public void onBeginTurn(BeginTurnEvent bte) {
			tb2.setText(bte.player.getName());
		}
	}

	public static class SelectedUnitListener {
		private final TextButton tb2;
		private Entity selected;

		public SelectedUnitListener(TextButton tb2) {
			this.tb2 = tb2;
		}

		@Subscribe
		public void onUpdated(EntityUpdatedEvent e) {
			if (selected == e.entity)
				updateLabel();
		}

		@Subscribe
		public void onSelected(SelectedEvent e) {
			selected = e.entity;
			updateLabel();
		}

		private void updateLabel() {
			if (selected == null) {
				tb2.setText("none (0) (nothing)");
			} else {
				NameComponent nc = selected.getComponent(NameComponent.class);
				ActivityComponent ac = selected.getComponent(ActivityComponent.class);
				ControllableComponent cc = selected.getComponent(ControllableComponent.class);

				tb2.setText(nc.getName() + " (" + ac.getPointsLeft() + ") (" + (ac.hasActivity() ? ac.getActivity().getName() : "idle") + ") (" + (cc.hasCommands() ? cc.getQueueLength() + " queued" : "-") + ")");
			}
		}
	}
}

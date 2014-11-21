package io.ilikeorangutans.ancol.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.ilikeorangutans.ancol.graphics.AnColRenderer;
import io.ilikeorangutans.ancol.graphics.RenderableComponent;
import io.ilikeorangutans.ancol.input.AnColInputProcessor;
import io.ilikeorangutans.ancol.map.Map;
import io.ilikeorangutans.ancol.map.MapViewport;
import io.ilikeorangutans.ancol.map.PositionComponent;
import io.ilikeorangutans.ancol.map.RandomMap;
import io.ilikeorangutans.ancol.move.MovableComponent;
import io.ilikeorangutans.ancol.move.MoveSystem;
import io.ilikeorangutans.ancol.select.SelectableComponent;
import io.ilikeorangutans.ancol.select.SelectionSystem;
import io.ilikeorangutans.bus.EventBus;
import io.ilikeorangutans.bus.SimpleEventBus;
import io.ilikeorangutans.ecs.Facade;
import io.ilikeorangutans.ecs.NameComponent;

import java.awt.event.InputEvent;

/**
 *
 */
public class GameScreen implements Screen {

    private final Stage stage;
    private final Game game;
    private final Skin skin;

    private SpriteBatch batch;

    private OrthographicCamera camera;

    private Facade facade;

    private EventBus bus;

    private MapViewport viewport;

    private AnColRenderer renderer;


    public GameScreen(Game game, Skin skin) {
        this.game = game;
        this.skin = skin;

        stage = new Stage();

        bus = new SimpleEventBus();

        camera = new OrthographicCamera();
        camera.setToOrtho(false);

        Map map = new RandomMap();
        viewport = new MapViewport(bus, 30, 30, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 60, 60, map);
        bus.subscribe(viewport);


        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        AnColInputProcessor anColInputProcessor = new AnColInputProcessor(bus, viewport);
        bus.subscribe(anColInputProcessor);
        inputMultiplexer.addProcessor(anColInputProcessor);
        inputMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(inputMultiplexer);

        TextButton tb = new TextButton("Step", skin, "default");
        tb.setPosition(Gdx.graphics.getWidth() - 50, 50);
        tb.addListener(new ClickListener() {

            public void clicked(InputEvent event, float x, float y) {
                facade.step();
            }
        });
        stage.addActor(tb);


        batch = new SpriteBatch();

        facade = new Facade(bus);
        facade.init();

        SelectionSystem selectionSystem = new SelectionSystem(facade.getEntities(), bus);
        bus.subscribe(selectionSystem);

        MoveSystem moveSystem = new MoveSystem(facade.getEntities(), bus);
        bus.subscribe(moveSystem);
        facade.addSystem(moveSystem);

        renderer = new AnColRenderer(batch, viewport, map, facade.getEntities());

        facade.getEntities().create(new PositionComponent(10, 10), new RenderableComponent(), new NameComponent("test entity 1"), new SelectableComponent(), new MovableComponent());
        facade.getEntities().create(new PositionComponent(4, 4), new RenderableComponent(), new NameComponent("test entity 2"), new SelectableComponent(), new MovableComponent());
        facade.getEntities().create(new PositionComponent(1, 1), new RenderableComponent(), new NameComponent("test entity 3"), new SelectableComponent(), new MovableComponent());

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        renderer.render();

        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
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

        stage.dispose();
        batch.dispose();
    }
}

package io.ilikeorangutans.ancol;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.ilikeorangutans.ancol.map.Map;
import io.ilikeorangutans.ancol.map.MapViewport;
import io.ilikeorangutans.ancol.map.RandomMap;
import io.ilikeorangutans.bus.EventBus;
import io.ilikeorangutans.bus.SimpleEventBus;
import io.ilikeorangutans.ecs.Facade;

public class AnCol extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture img;

    private OrthographicCamera camera;

    private Facade facade;

    private EventBus bus;

    private MapViewport viewport;

    private AnColRenderer renderer;

    private InputProcessor inputProcessor;

    public AnCol(EventBus bus, InputProcessor inputProcessor) {
        this.bus = bus;
        this.inputProcessor = inputProcessor;
    }

    @Override
    public void create() {

        Gdx.input.setInputProcessor(inputProcessor);

        camera = new OrthographicCamera();
        camera.setToOrtho(false);

        Map map = new RandomMap();
        viewport = new MapViewport(bus, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 60, 60, map);

        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");

        bus = new SimpleEventBus();
        facade = new Facade(bus);
        facade.init();

        renderer = new AnColRenderer(batch, viewport, map, facade.getEntities());

    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        renderer.render();
    }

    @Override
    public void resize(int width, int height) {
        viewport.resize(width, height);
    }
}

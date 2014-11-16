package io.ilikeorangutans.ancol;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.ilikeorangutans.ancol.graphics.AnColRenderer;
import io.ilikeorangutans.ancol.graphics.RenderableComponent;
import io.ilikeorangutans.ancol.map.Map;
import io.ilikeorangutans.ancol.map.MapViewport;
import io.ilikeorangutans.ancol.map.PositionComponent;
import io.ilikeorangutans.ancol.map.RandomMap;
import io.ilikeorangutans.bus.EventBus;
import io.ilikeorangutans.bus.SimpleEventBus;
import io.ilikeorangutans.ecs.Entity;
import io.ilikeorangutans.ecs.Facade;
import io.ilikeorangutans.ecs.NameComponent;

public class AnCol extends ApplicationAdapter {
    private SpriteBatch batch;

    private OrthographicCamera camera;

    private Facade facade;

    private EventBus bus;

    private MapViewport viewport;

    private AnColRenderer renderer;

    private InputProcessor[] inputProcessors;

    public AnCol(EventBus bus, InputProcessor... inputProcessors) {
        this.bus = bus;
        this.inputProcessors = inputProcessors;
    }

    @Override
    public void create() {

        for (InputProcessor ip : inputProcessors)
            Gdx.input.setInputProcessor(ip);

        camera = new OrthographicCamera();
        camera.setToOrtho(false);

        Map map = new RandomMap();
        viewport = new MapViewport(bus, 30, 30, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 60, 60, map);
        bus.subscribe(viewport);

        batch = new SpriteBatch();

        bus = new SimpleEventBus();
        facade = new Facade(bus);
        facade.init();

        renderer = new AnColRenderer(batch, viewport, map, facade.getEntities());

        facade.getEntities().create(new PositionComponent(10, 10), new RenderableComponent(), new NameComponent("test entity 1"));
        facade.getEntities().create(new PositionComponent(7, 14), new RenderableComponent(), new NameComponent("test entity 2"));
        facade.getEntities().create(new PositionComponent(1, 1), new RenderableComponent(), new NameComponent("test entity 3"));

    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
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

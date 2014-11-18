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
import io.ilikeorangutans.ancol.select.SelectableComponent;
import io.ilikeorangutans.ancol.select.SelectionSystem;
import io.ilikeorangutans.bus.EventBus;
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

        camera = new OrthographicCamera();
        camera.setToOrtho(false);

        Map map = new RandomMap();
        viewport = new MapViewport(bus, 30, 30, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 60, 60, map);
        bus.subscribe(viewport);

        for (InputProcessor ip : inputProcessors) {
            Gdx.input.setInputProcessor(ip);

            // Not happy with this but the input processors are dependant on the runtime
            if (ip instanceof RequiresScreenToMap) {
                ((RequiresScreenToMap) ip).setScreenToMap(viewport);
            }
        }

        batch = new SpriteBatch();

        facade = new Facade(bus);
        facade.init();

        SelectionSystem selectionSystem = new SelectionSystem(facade.getEntities(), bus);
        bus.subscribe(selectionSystem);

        renderer = new AnColRenderer(batch, viewport, map, facade.getEntities());

        facade.getEntities().create(new PositionComponent(10, 10), new RenderableComponent(), new NameComponent("test entity 1"), new SelectableComponent());
        facade.getEntities().create(new PositionComponent(4, 4), new RenderableComponent(), new NameComponent("test entity 2"), new SelectableComponent());
        facade.getEntities().create(new PositionComponent(1, 1), new RenderableComponent(), new NameComponent("test entity 3"), new SelectableComponent());
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

package io.ilikeorangutans.ancol.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.ilikeorangutans.ancol.Point;
import io.ilikeorangutans.ancol.map.Map;
import io.ilikeorangutans.ancol.map.MapViewport;
import io.ilikeorangutans.ancol.map.PositionComponent;
import io.ilikeorangutans.ecs.ComponentType;
import io.ilikeorangutans.ecs.Entities;
import io.ilikeorangutans.ecs.Entity;

import java.util.List;


/**
 *
 */
public class AnColRenderer {
    private SpriteBatch batch;
    private MapViewport viewport;
    private Map map;
    private Entities entities;

    private Texture terrainTexture;
    private Texture unitTexture;
    private Sprite grass;
    private Sprite water;
    private Sprite explorer;

    public AnColRenderer(SpriteBatch batch, MapViewport viewport, Map map, Entities entities) {
        this.batch = batch;
        this.viewport = viewport;
        this.map = map;
        this.entities = entities;

        terrainTexture = new Texture(Gdx.files.internal("tiles.png"));
        grass = new Sprite(terrainTexture, 0, 60, 60, 60);
        water = new Sprite(terrainTexture, 0, 540, 60, 60);

        unitTexture = new Texture(Gdx.files.internal("units.png"));
        explorer = new Sprite(unitTexture, 13 * 60, 60, 60, 60);

    }

    public void render() {

        final Point min = viewport.getFirstVisibleTile();

        batch.begin();

        for (int iy = 0; iy < viewport.getHeightInTiles(); iy++) {

            for (int ix = 0; ix < viewport.getWidthInTiles(); ix++) {

                Sprite draw = grass;
                if (ix + min.x < iy + min.y)
                    draw = water;

                Point p = viewport.mapToScreen((ix + min.x) * 60, (iy + min.y) * 60);
                draw.setPosition(p.x, p.y - 60);

                draw.draw(batch);
            }
        }

        List<Entity> renderables = entities.getEntityByType(ComponentType.fromClass(RenderableComponent.class), ComponentType.fromClass(PositionComponent.class));

        for (Entity e : renderables) {
            final PositionComponent pos = e.getComponent(PositionComponent.class);

            // Filter out elements not in the viewport... this should be done by some kind of query...
            final int x = pos.getX() * viewport.getTileWidth();
            final int y = pos.getY() * viewport.getTileHeight();

            if (!viewport.isVisible(x, y))
                continue;

            Point point = viewport.mapToScreen(x, y);

            explorer.setPosition(point.x, point.y - 60);
            explorer.draw(batch);
        }

        batch.end();
    }
}

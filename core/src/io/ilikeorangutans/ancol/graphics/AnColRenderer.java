package io.ilikeorangutans.ancol.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.ilikeorangutans.ancol.Point;
import io.ilikeorangutans.ancol.map.Map;
import io.ilikeorangutans.ancol.map.MapViewport;
import io.ilikeorangutans.ancol.map.PositionComponent;
import io.ilikeorangutans.ancol.map.Tile;
import io.ilikeorangutans.ancol.move.MovableComponent;
import io.ilikeorangutans.ancol.select.SelectableComponent;
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
    private Sprite select;
    private Sprite flag;

    public AnColRenderer(SpriteBatch batch, MapViewport viewport, Map map, Entities entities) {
        this.batch = batch;
        this.viewport = viewport;
        this.map = map;
        this.entities = entities;

        terrainTexture = new Texture(Gdx.files.internal("tiles.png"));
        grass = new Sprite(terrainTexture, 0, 60, 60, 60);
        water = new Sprite(terrainTexture, 0, 540, 60, 60);
        flag = new Sprite(terrainTexture, 16 * 60, 9 * 60, 60, 60);

        unitTexture = new Texture(Gdx.files.internal("units.png"));
        explorer = new Sprite(unitTexture, 13 * 60, 60, 60, 60);

        select = new Sprite(new Texture(Gdx.files.internal("select.png")), 0, 0, 60, 60);
    }

    public void render() {

        final Point min = viewport.getFirstVisibleTile();

        batch.begin();

        for (int iy = 0; iy < viewport.getHeightInTiles(); iy++) {

            for (int ix = 0; ix < viewport.getWidthInTiles(); ix++) {

                final Tile tile = map.getTileAt(ix + min.x, iy + min.y);

                Sprite draw = water;
                if (tile.getType().getId() == 2) {
                    draw = grass;
                }

                Point p = viewport.mapToScreen((ix + min.x) * viewport.getTileWidth(), (iy + min.y) * viewport.getTileHeight());
                draw.setPosition(p.x, p.y - viewport.getTileHeight());

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

            if (e.hasComponent(ComponentType.fromClass(SelectableComponent.class))) {
                SelectableComponent sc = e.getComponent(SelectableComponent.class);

                if (sc.isSelected()) {
                    select.setPosition(point.x, point.y - viewport.getTileHeight());
                    select.draw(batch);

                    if (e.hasComponent(ComponentType.fromClass(MovableComponent.class))) {
                        MovableComponent mc = e.getComponent(MovableComponent.class);

                        if (mc.hasDestination()) {
                            Point destination = mc.getDestination();
                            Point screen = viewport.mapToScreen(destination.x * viewport.getTileWidth(), destination.y * viewport.getTileHeight());
                            flag.setPosition(screen.x, screen.y - viewport.getTileHeight());
                            flag.draw(batch);
                        }
                    }
                }
            }


            explorer.setPosition(point.x, point.y - viewport.getTileHeight());
            explorer.draw(batch);
        }

        batch.end();
    }
}

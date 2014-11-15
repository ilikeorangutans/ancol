package io.ilikeorangutans.ancol;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.ilikeorangutans.ancol.map.Map;
import io.ilikeorangutans.ancol.map.MapViewport;
import io.ilikeorangutans.ecs.Entities;

import java.awt.*;

/**
 *
 */
public class AnColRenderer {
    private SpriteBatch batch;
    private MapViewport viewport;
    private Map map;
    private Entities entities;

    private Texture terrainTexture;
    private Sprite grass;
    private Sprite water;

    public AnColRenderer(SpriteBatch batch, MapViewport viewport, Map map, Entities entities) {
        this.batch = batch;
        this.viewport = viewport;
        this.map = map;
        this.entities = entities;

        terrainTexture = new Texture(Gdx.files.internal("tiles.png"));
        grass = new Sprite(terrainTexture, 0, 60, 60, 60);
        water = new Sprite(terrainTexture, 0, 540, 60, 60);
    }

    public void render() {

        batch.begin();

        for (int iy = 0; iy < viewport.getHeightInTiles(); iy++) {

            for (int ix = 0; ix < viewport.getWidthInTiles(); ix++) {

                Sprite draw = grass;
                if (iy % 2 == 0)
                    draw = water;

                Point p = viewport.mapToScreen(ix * 60, iy * 60);
                draw.setPosition(p.x, p.y);

                draw.draw(batch);

            }

        }

        batch.end();
    }
}

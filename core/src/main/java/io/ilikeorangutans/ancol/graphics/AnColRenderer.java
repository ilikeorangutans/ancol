package io.ilikeorangutans.ancol.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.ilikeorangutans.ancol.Point;
import io.ilikeorangutans.ancol.game.activity.ActivityComponent;
import io.ilikeorangutans.ancol.game.colony.ColonyComponent;
import io.ilikeorangutans.ancol.game.rule.Rules;
import io.ilikeorangutans.ancol.map.Map;
import io.ilikeorangutans.ancol.map.PositionComponent;
import io.ilikeorangutans.ancol.map.tile.Tile;
import io.ilikeorangutans.ancol.map.viewport.MapViewport;
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
	private final BitmapFont font;
	private SpriteBatch batch;
	private MapViewport viewport;
	private Map map;
	private Entities entities;
	private Rules rules;
	private Texture terrainTexture;
	private Texture unitTexture;
	private Sprite grass;
	private Sprite water;
	private Sprite unexplored;
	private Sprite explorer;
	private Sprite colony;
	private Sprite select;
	private Sprite flag;
	private Sprite ship;
	private Sprite[] terrain;

	public AnColRenderer(SpriteBatch batch, MapViewport viewport, Map map, Entities entities, Rules rules) {
		this.batch = batch;
		this.viewport = viewport;
		this.map = map;
		this.entities = entities;
		this.rules = rules;

		terrainTexture = new Texture(Gdx.files.internal("tiles.png"));
		terrain = new Sprite[12];

		terrain[0] = new Sprite(terrainTexture, 0, 13 * 60, 60, 60);
		terrain[1] = new Sprite(terrainTexture, 0, 3 * 60, 60, 60);
		terrain[2] = new Sprite(terrainTexture, 0, 10 * 60, 60, 60);
		terrain[3] = new Sprite(terrainTexture, 0, 10 * 60, 60, 60);
		terrain[4] = new Sprite(terrainTexture, 0, 10 * 60, 60, 60);
		terrain[5] = new Sprite(terrainTexture, 0, 2 * 60, 60, 60);
		terrain[6] = new Sprite(terrainTexture, 0, 1 * 60, 60, 60);
		terrain[7] = new Sprite(terrainTexture, 0, 10 * 60, 60, 60);
		terrain[8] = new Sprite(terrainTexture, 0, 6 * 60, 60, 60);
		terrain[9] = new Sprite(terrainTexture, 8 * 60, 10 * 60, 60, 60);
		terrain[10] = new Sprite(terrainTexture, 0, 10 * 60, 60, 60);
		terrain[11] = new Sprite(terrainTexture, 0, 540, 60, 60);


		grass = new Sprite(terrainTexture, 0, 60, 60, 60);
		water = new Sprite(terrainTexture, 0, 540, 60, 60);
		flag = new Sprite(terrainTexture, 16 * 60, 9 * 60, 60, 60);
		unexplored = new Sprite(terrainTexture, 0, 13 * 60, 60, 60);

		unitTexture = new Texture(Gdx.files.internal("units.png"));
		explorer = new Sprite(unitTexture, 13 * 60, 60, 60, 60);
		ship = new Sprite(unitTexture, 13 * 60, 0, 60, 60);
		colony = new Sprite(terrainTexture, 12 * 60, 12 * 60, 60, 60);

		select = new Sprite(new Texture(Gdx.files.internal("select.png")), 0, 0, 60, 60);

		font = new BitmapFont();
	}

	public void render() {

		final Point min = viewport.getFirstVisibleTile();

		batch.begin();

		for (int iy = 0; iy < viewport.getHeightInTiles(); iy++) {

			for (int ix = 0; ix < viewport.getWidthInTiles(); ix++) {

				final Tile tile = map.getTileAt(ix + min.x, iy + min.y);


				Sprite draw = terrain[tile.getType().getId()];// unexplored;
//                switch (tile.getType().getId()) {
//                    case 1:
//                        draw = water;
//                        break;
//                    case 2:
//                        draw = grass;
//                        break;
//                    default:
//                        draw = unexplored;
//                }


				Point p = viewport.mapToScreen((ix + min.x) * viewport.getTileWidth(), (iy + min.y) * viewport.getTileHeight());
				draw.setPosition(p.x, p.y - viewport.getTileHeight());

				draw.draw(batch);
			}
		}

		List<Entity> renderables = entities.getEntityByType(ComponentType.fromClasses(RenderableComponent.class, PositionComponent.class));

		for (Entity e : renderables) {
			final PositionComponent pos = e.getComponent(PositionComponent.class);

			// Filter out elements not in the viewport... this should be done by some kind of query...
			final int x = pos.getX() * viewport.getTileWidth();
			final int y = pos.getY() * viewport.getTileHeight();

			if (!viewport.isVisible(x, y)) {
				continue;
			}
			RenderableComponent renderableComponent = e.getComponent(RenderableComponent.class);
			if (!renderableComponent.isVisible())
				continue;

			Point point = viewport.mapToScreen(x, y);

			if (e.hasComponent(ComponentType.fromClasses(SelectableComponent.class))) {
				SelectableComponent sc = e.getComponent(SelectableComponent.class);

				if (sc.isSelected()) {
					select.setPosition(point.x, point.y - viewport.getTileHeight());
					select.draw(batch);

					if (e.hasComponent(ComponentType.fromClasses(MovableComponent.class))) {
						MovableComponent mc = e.getComponent(MovableComponent.class);

						if (mc.hasDestination()) {
							for (Point p : mc.getPath().segments) {
								Point screen = viewport.mapToScreen(p.x * viewport.getTileWidth(), p.y * viewport.getTileHeight());
								flag.setPosition(screen.x, screen.y - viewport.getTileHeight());
								flag.draw(batch);
							}
						}
					}
				}
			}

			Sprite draw;

			switch (renderableComponent.getSpriteId()) {
				case 0:
					draw = colony;
					break;

				case 1:
					draw = explorer;
					break;

				case 2:
				default:
					draw = ship;
					break;
			}


			draw.setPosition(point.x, point.y - viewport.getTileHeight());
			draw.draw(batch);

			if (e.hasComponent(ComponentType.fromClass(ActivityComponent.class))) {
				ActivityComponent ac = e.getComponent(ActivityComponent.class);
				String aps = ac.getPointsLeft() + "/" + ac.getPoints();
				font.draw(batch, aps, point.x, point.y);
			}

			if (e.hasComponent(ComponentType.fromClass(ColonyComponent.class))) {
				ColonyComponent colony = e.getComponent(ColonyComponent.class);

				font.draw(batch, colony.getName() + " [" + colony.getSize() + "]", point.x, point.y);
			}
		}

		batch.end();
	}
}

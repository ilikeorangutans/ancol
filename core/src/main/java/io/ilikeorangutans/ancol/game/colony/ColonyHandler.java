package io.ilikeorangutans.ancol.game.colony;

import io.ilikeorangutans.ancol.game.player.Player;
import io.ilikeorangutans.ancol.game.player.PlayerOwnedComponent;
import io.ilikeorangutans.ancol.game.player.event.BeginTurnEvent;
import io.ilikeorangutans.ancol.game.vision.VisionComponent;
import io.ilikeorangutans.ancol.graphics.RenderableComponent;
import io.ilikeorangutans.ancol.map.Map;
import io.ilikeorangutans.ancol.map.PositionComponent;
import io.ilikeorangutans.ancol.map.surrounding.PointSurroundings;
import io.ilikeorangutans.ancol.map.surrounding.Surroundings;
import io.ilikeorangutans.ancol.select.event.EntitySelectedEvent;
import io.ilikeorangutans.ancol.select.SelectableComponent;
import io.ilikeorangutans.bus.Emitter;
import io.ilikeorangutans.bus.Subscribe;
import io.ilikeorangutans.ecs.*;

import java.util.List;

/**
 *
 */
public class ColonyHandler {

	private final Emitter emitter;
	private final EntityFactory factory;
	private final Entities entities;
	private final Map map;

	private int counter = 1;

	public ColonyHandler(Emitter emitter, EntityFactory factory, Entities entities, Map map) {
		this.emitter = emitter;
		this.factory = factory;
		this.entities = entities;
		this.map = map;
	}

	@Subscribe
	public void onBuildColony(BuildColonyEvent event) {
		buildColony(event.builder);
	}

	@Subscribe
	public void onEntitySelected(EntitySelectedEvent event) {
		if (event.entity.hasComponent(ComponentType.fromClass(ColonyComponent.class))) {
			// TODO: might be better to queue that
			emitter.fire(new OpenColonyEvent(event.entity));
		}
	}

	@Subscribe
	public void onBeginTurn(BeginTurnEvent event) {
		List<Entity> colonies = entities.getEntityByType(ComponentType.fromClasses(ColonyComponent.class, PlayerOwnedComponent.class));

		for (Entity entity : colonies) {
			if (!entity.getComponent(PlayerOwnedComponent.class).getPlayer().equals(event.player))
				continue;

			ColonyComponent colony = entity.getComponent(ColonyComponent.class);

			colony.beginTurn();
		}

	}

	private void buildColony(Entity builder) {
		Player owner = builder.getComponent(PlayerOwnedComponent.class).getPlayer();
		PositionComponent position = builder.getComponent(PositionComponent.class);

		String name = "colony " + counter;
		Surroundings surroundings = new PointSurroundings(position.getPoint(), map, entities);

		Entity colony = factory.create(
				new PlayerOwnedComponent(owner),
				new RenderableComponent(0),
				new SelectableComponent(),
				new PositionComponent(position),
				new VisionComponent(1),
				new NameComponent(name),
				new ColonyComponent(name, surroundings)
		);

		colony.getComponent(ColonyComponent.class).addColonist(builder);

		counter++;
	}
}

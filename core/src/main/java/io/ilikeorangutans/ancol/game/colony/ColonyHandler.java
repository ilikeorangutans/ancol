package io.ilikeorangutans.ancol.game.colony;

import io.ilikeorangutans.ancol.game.colony.event.OpenColonyEvent;
import io.ilikeorangutans.ancol.game.player.Player;
import io.ilikeorangutans.ancol.game.player.PlayerOwnedComponent;
import io.ilikeorangutans.ancol.game.player.event.BeginTurnEvent;
import io.ilikeorangutans.ancol.game.mod.Mod;
import io.ilikeorangutans.ancol.game.vision.VisionComponent;
import io.ilikeorangutans.ancol.graphics.RenderableComponent;
import io.ilikeorangutans.ancol.map.GameMap;
import io.ilikeorangutans.ancol.map.PositionComponent;
import io.ilikeorangutans.ancol.map.surrounding.ImmutablePointSurroundings;
import io.ilikeorangutans.ancol.map.surrounding.Surroundings;
import io.ilikeorangutans.ancol.select.SelectableComponent;
import io.ilikeorangutans.ancol.select.event.EntitySelectedEvent;
import io.ilikeorangutans.ancol.select.event.SelectEntityEvent;
import io.ilikeorangutans.bus.Emitter;
import io.ilikeorangutans.bus.Subscribe;
import io.ilikeorangutans.ecs.ComponentType;
import io.ilikeorangutans.ecs.EntitiesEntityFactory;
import io.ilikeorangutans.ecs.Entity;
import io.ilikeorangutans.ecs.NameComponent;

import java.util.List;

/**
 * Event handler that deals with all Colony related events. Reacts to {@link io.ilikeorangutans.ancol.game.colony.BuildColonyEvent}
 * for which it creates the new colony (might move that somewhere else), {@link io.ilikeorangutans.ancol.select.event.EntitySelectedEvent}
 * on colonies which opens the colony screen, and simulates the player's colonies on {@link io.ilikeorangutans.ancol.game.player.event.BeginTurnEvent}.
 */
public class ColonyHandler {

	private final Emitter emitter;
	private final EntitiesEntityFactory entities;
	private final Mod mod;

	private GameMap map;

	private int counter = 1;

	public ColonyHandler(Emitter emitter, EntitiesEntityFactory entities, Mod mod) {
		this.emitter = emitter;
		this.entities = entities;
		this.mod = mod;

	}

	@Subscribe
	public void onBuildColony(BuildColonyEvent event) {
		foundColony(event.builder);
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
		Player player = event.player;
		map = player.getMap();
		simulateColonies(player);
	}

	private void simulateColonies(Player player) {
		List<Entity> colonies = entities.getEntityByType(ComponentType.fromClasses(ColonyComponent.class, PlayerOwnedComponent.class));

		for (Entity entity : colonies) {
			if (!entity.getComponent(PlayerOwnedComponent.class).getPlayer().equals(player))
				continue;

			ColonyComponent colony = entity.getComponent(ColonyComponent.class);

			colony.beginTurn();
		}
	}

	private void foundColony(Entity builder) {
		Player owner = builder.getComponent(PlayerOwnedComponent.class).getPlayer();
		PositionComponent position = builder.getComponent(PositionComponent.class);

		String name = "colony " + counter;
		counter++;
		Surroundings surroundings = new ImmutablePointSurroundings(position.getPoint(), map, entities);

		ColonyComponent colonyComponent = new ColonyComponent(name, surroundings, mod, entities);
		Entity colony = entities.create(
				new PlayerOwnedComponent(owner),
				new RenderableComponent(0),
				new SelectableComponent(),
				new PositionComponent(position),
				new VisionComponent(1),
				new NameComponent(name),
				colonyComponent
		);

		colonyComponent.found(builder.getComponent(PlayerOwnedComponent.class).getPlayer());

		colony.getComponent(ColonyComponent.class).addColonist(builder);

		emitter.fire(new SelectEntityEvent(colony));
	}
}

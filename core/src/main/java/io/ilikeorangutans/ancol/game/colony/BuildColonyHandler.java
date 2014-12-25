package io.ilikeorangutans.ancol.game.colony;

import io.ilikeorangutans.ancol.game.Player;
import io.ilikeorangutans.ancol.game.PlayerOwnedComponent;
import io.ilikeorangutans.ancol.game.vision.VisionComponent;
import io.ilikeorangutans.ancol.graphics.RenderableComponent;
import io.ilikeorangutans.ancol.map.PositionComponent;
import io.ilikeorangutans.ancol.select.SelectableComponent;
import io.ilikeorangutans.bus.Subscribe;
import io.ilikeorangutans.ecs.Entity;
import io.ilikeorangutans.ecs.EntityFactory;
import io.ilikeorangutans.ecs.NameComponent;

/**
 *
 */
public class BuildColonyHandler {

	private final EntityFactory factory;

	private int counter = 1;

	public BuildColonyHandler(EntityFactory factory) {
		this.factory = factory;
	}

	@Subscribe
	public void onBuildColony(BuildColonyEvent event) {
		buildColony(event.builder);
	}

	private void buildColony(Entity builder) {
		Player owner = builder.getComponent(PlayerOwnedComponent.class).getPlayer();
		PositionComponent position = builder.getComponent(PositionComponent.class);

		String name = "colony " + counter;
		Entity colony = factory.create(
				new PlayerOwnedComponent(owner),
				new RenderableComponent(0),
				new SelectableComponent(),
				new PositionComponent(position),
				new VisionComponent(1),
				new NameComponent(name),
				new ColonyComponent(name)
		);

		colony.getComponent(ColonyComponent.class).addColonist(builder);

		counter++;
	}
}

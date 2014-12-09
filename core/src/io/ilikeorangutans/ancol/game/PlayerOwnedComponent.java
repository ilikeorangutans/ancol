package io.ilikeorangutans.ancol.game;

import io.ilikeorangutans.ecs.Component;
import io.ilikeorangutans.ecs.ComponentType;

/**
 *
 */
public class PlayerOwnedComponent implements Component {

    public static final ComponentType COMPONENT_TYPE = ComponentType.fromClass(PlayerOwnedComponent.class)[0];

    private Player player;

    public PlayerOwnedComponent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public ComponentType getType() {
        return COMPONENT_TYPE;
    }
}

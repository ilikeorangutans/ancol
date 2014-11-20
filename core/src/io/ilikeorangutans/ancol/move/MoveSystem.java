package io.ilikeorangutans.ancol.move;

import io.ilikeorangutans.ancol.Point;
import io.ilikeorangutans.ancol.map.PositionComponent;
import io.ilikeorangutans.ancol.select.SelectableComponent;
import io.ilikeorangutans.bus.Emitter;
import io.ilikeorangutans.bus.Subscribe;
import io.ilikeorangutans.ecs.ComponentType;
import io.ilikeorangutans.ecs.Entities;
import io.ilikeorangutans.ecs.Entity;

import java.util.List;

/**
 *
 */
public class MoveSystem implements io.ilikeorangutans.ecs.System {

    private final Entities entities;

    private final Emitter emitter;

    public MoveSystem(Entities entities, Emitter emitter) {
        this.entities = entities;
        this.emitter = emitter;
    }

    @Override
    public void step(float deltaTime) {
        List<Entity> ents = entities.getEntityByType(ComponentType.fromClass(MovableComponent.class), ComponentType.fromClass(PositionComponent.class));

        for (Entity e : ents) {
            final MovableComponent mc = e.getComponent(MovableComponent.class);
            if (!mc.hasDestination())
                continue;

            final PositionComponent pc = e.getComponent(PositionComponent.class);

            final Point dst = mc.getDestination();
            boolean arrived = pc.getX() == dst.x && pc.getY() == dst.y;
            if (arrived) {
                mc.setDestination(null);
                continue;
            }

            int deltax = 0, deltay = 0;

            if (dst.x < pc.getX()) deltax = -1;
            if (dst.x > pc.getX()) deltax = 1;
            if (dst.y < pc.getY()) deltay = -1;
            if (dst.y > pc.getY()) deltay = 1;

            pc.set(pc.getX() + deltax, pc.getY() + deltay);

            emitter.fire(new MovedEvent(e, new Point(pc.getX(), pc.getY())));
        }
    }

    @Subscribe
    public void onMoveEvent(MoveEvent moveEvent) {
        List<Entity> ents = entities.getEntityByType(ComponentType.fromClass(SelectableComponent.class), ComponentType.fromClass(MovableComponent.class));

        for (Entity e : ents) {
            final SelectableComponent sc = e.getComponent(SelectableComponent.class);

            if (!sc.isSelected()) {
                continue;
            }

            MovableComponent mc = e.getComponent(MovableComponent.class);
            mc.setDestination(moveEvent.destination);
        }
    }

}

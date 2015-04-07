package io.ilikeorangutans.ancol.ui.colony;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;

/**
 *
 */
public class WarehouseSlotSource extends DragAndDrop.Source {

	private final WarehouseSlot slot;
	private final Actor dragActor;

	public WarehouseSlotSource(WarehouseSlot actor, Actor dragActor) {
		super(actor);
		slot = actor;
		this.dragActor = dragActor;
	}

	@Override
	public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
		if (slot.isEmpty())
			return null;

		final DragAndDrop.Payload p = new DragAndDrop.Payload();

		p.setDragActor(dragActor);

		return p;
	}
}

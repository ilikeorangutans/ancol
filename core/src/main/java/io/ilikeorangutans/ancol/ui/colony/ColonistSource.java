package io.ilikeorangutans.ancol.ui.colony;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import io.ilikeorangutans.ecs.Entity;

/**
 *
 */
public class ColonistSource extends DragAndDrop.Source {

	private final Actor actor;
	private final Image image;
	private final Entity colonist;
	private final boolean removeOnDrag;

	public ColonistSource(Actor actor, Image image, Entity colonist, boolean removeOnDrag) {
		super(actor);
		this.actor = actor;
		this.image = image;
		this.colonist = colonist;
		this.removeOnDrag = removeOnDrag;
	}

	@Override
	public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
		DragAndDrop.Payload payload = new DragAndDrop.Payload();
		payload.setDragActor(image);
		payload.setObject(colonist);
		return payload;
	}

	@Override
	public void dragStop(InputEvent event, float x, float y, int pointer, DragAndDrop.Payload payload, DragAndDrop.Target target) {
		if (target == null)
			return;


	}
}

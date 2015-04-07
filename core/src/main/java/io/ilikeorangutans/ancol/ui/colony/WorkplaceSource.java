package io.ilikeorangutans.ancol.ui.colony;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import io.ilikeorangutans.ancol.game.production.Workplace;

/**
 *
 */
public class WorkplaceSource extends DragAndDrop.Source {

	private final Workplace workplace;

	public WorkplaceSource(Actor actor, Workplace workplace) {
		super(actor);
		this.workplace = workplace;
	}

	@Override
	public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
		if (workplace.getNumberOfWorkers() < 1) {
			System.out.println("BuildingSource.dragStart no workers");
			return null;
		}
		DragAndDrop.Payload payload = new DragAndDrop.Payload();

//		payload.setDragActor();

		return payload;
	}

}

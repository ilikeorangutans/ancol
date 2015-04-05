package io.ilikeorangutans.ancol.ui;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import io.ilikeorangutans.ecs.Entity;

/**
 *
 */
public class ColonistUI extends Image {
	private final Entity colonist;
	private final TextureRegion texture;

	public ColonistUI(Entity colonist, TextureRegion texture) {
		super(texture);
		this.colonist = colonist;
		this.texture = texture;
	}

	public DragAndDrop.Source getSource() {
		return new ColonistSource(this, new Image(texture));
	}

	public class ColonistSource extends DragAndDrop.Source {

		private final Image image;

		public ColonistSource(Actor actor, Image image) {
			super(actor);
			this.image = image;
		}

		@Override
		public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
			DragAndDrop.Payload payload = new DragAndDrop.Payload();
			payload.setDragActor(image);
			payload.setObject(colonist);
			return payload;
		}
	}
}

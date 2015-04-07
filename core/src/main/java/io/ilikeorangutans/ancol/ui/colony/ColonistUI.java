package io.ilikeorangutans.ancol.ui.colony;

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

}

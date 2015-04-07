package io.ilikeorangutans.ancol.ui.colony;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import io.ilikeorangutans.ancol.game.colonist.Colonists;
import io.ilikeorangutans.ecs.Entity;

/**
 *
 */
public class ColonistsUI extends HorizontalGroup {
	private final Colonists colonists;

	public ColonistsUI(Colonists colonists, TextureAtlas atlas, DragAndDrop dragAndDrop) {
		this.colonists = colonists;

		for (Entity colonist : colonists) {
			TextureRegion textureRegion = atlas.findRegion("units").split(60, 60)[1][13];
			ColonistUI colonistUI = new ColonistUI(colonist, textureRegion);
			addActor(colonistUI);
			dragAndDrop.addSource(new ColonistSource(colonistUI, new Image(textureRegion), colonist, true));
		}
	}
}

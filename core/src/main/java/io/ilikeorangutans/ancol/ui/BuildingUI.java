package io.ilikeorangutans.ancol.ui;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import io.ilikeorangutans.ancol.game.colony.ColonyComponent;
import io.ilikeorangutans.ancol.game.colony.building.Building;

/**
 *
 *
 */
public class BuildingUI extends Group {

	private final ColonyComponent colony;
	private final Building building;

	public BuildingUI(TextureAtlas atlas, ColonyComponent colony, Building building) {
		TextureAtlas atlas1 = atlas;
		this.colony = colony;
		this.building = building;
		Image image = new Image(atlas.findRegion("tiles").split(60, 60)[12][14]);
		addActor(image);

		setBounds(getX(), getY(), image.getWidth(), image.getHeight());
	}

	public DragAndDrop.Source getSource() {
		return new WorkplaceSource(this, building);
	}

}

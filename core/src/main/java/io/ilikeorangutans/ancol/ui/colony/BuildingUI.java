package io.ilikeorangutans.ancol.ui.colony;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
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

	public BuildingUI(TextureAtlas atlas, ColonyComponent colony, Building building, DragAndDrop dragAndDrop, Skin skin) {
		this.colony = colony;
		this.building = building;
		Image image = new Image(atlas.findRegion("tiles").split(60, 60)[12][14]);
		image.setPosition(image.getWidth() / 2, image.getWidth() / 2);
		addActor(image);
		float height = image.getHeight() * 2;
		float width = image.getWidth() * 3;

		Label label = new Label(building.getName() + "(" + building.size() + ")", skin);
		label.setWrap(true);
		label.setPosition(0, height - label.getHeight());
		addActor(label);

		ColonistsUI colonistsUI = new ColonistsUI(building, atlas, dragAndDrop);
		colonistsUI.setPosition(0, 0);
		colonistsUI.setWidth(width);
		colonistsUI.setHeight(image.getHeight());
		addActor(colonistsUI);

		setBounds(getX(), getY(), width, height);
	}

	public DragAndDrop.Source getSource() {
		return new WorkplaceSource(this, building);
	}

}

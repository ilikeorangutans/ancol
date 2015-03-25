package io.ilikeorangutans.ancol.ui;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import io.ilikeorangutans.ancol.game.ware.RecordingWares;
import io.ilikeorangutans.ancol.game.ware.Stored;
import io.ilikeorangutans.ancol.game.ware.Ware;
import io.ilikeorangutans.ancol.game.ware.Wares;

/**
 *
 */
public class WarehouseAndProductionUI extends HorizontalGroup {
	private final Skin skin;

	public WarehouseAndProductionUI(Skin skin, TextureAtlas atlas, Wares warehouse, RecordingWares simulated) {
		DragAndDrop dragAndDrop = new DragAndDrop();
		this.skin = skin;

		for (Stored stored : warehouse.getWares()) {
			final Ware ware = stored.getWare();
			if (ware.isStorable()) {
				WarehouseSlot slot = new WarehouseSlot(skin, atlas, stored, simulated.getProduced(ware), simulated.getConsumed(ware));
				addActor(slot);
				dragAndDrop.addSource(slot.getSource());
			}

		}
	}

}

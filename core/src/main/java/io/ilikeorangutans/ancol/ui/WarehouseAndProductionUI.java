package io.ilikeorangutans.ancol.ui;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.*;
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
		this.skin = skin;

		for (Stored stored : warehouse.getWares()) {
			final Ware ware = stored.getWare();
			if (ware.isStorable())
				addActor(new WarehouseAndProductionUI.WarehouseSlot(skin, atlas, stored, simulated.getProduced(ware), simulated.getConsumed(ware)));
		}
	}

	public static class WarehouseSlot extends VerticalGroup {
		public static final int HEIGHT = 48;
		public static final int WIDTH = 32;
		public static final int MARGIN = 3;
		private final Label amountLabel;
		private final Label simulatedLabel;
		
		public WarehouseSlot(Skin skin, TextureAtlas atlas, Stored stored, int produced, int consumed) {
			final String regionName = "goods/" + stored.getWare().getName().toLowerCase().replaceAll(" ", "_");
			final Image background = new Image(atlas.findRegion(regionName));
			addActor(background);

			amountLabel = new Label(Integer.toString(stored.getAmount()), skin);
			addActor(amountLabel);

			simulatedLabel = new Label("", skin);
			simulatedLabel.setFontScale(.7F);
			addActor(simulatedLabel);

			setBounds(getX(), getY(), WIDTH + 2 * MARGIN, HEIGHT);
			refresh(stored.getAmount(), produced, consumed);
		}


		public void refresh(int amount, int produced, int consumed) {
			amountLabel.setText(Integer.toString(amount));
			simulatedLabel.setText(createSimulatedLabel(produced, consumed));
		}

		private String createSimulatedLabel(int amountProduced, int amountConsumed) {
			StringBuilder sb = new StringBuilder();

			boolean produced = amountProduced > 0;
			boolean consumed = amountConsumed > 0;

			if (produced) {
				sb.append("+");
				sb.append(amountProduced);
			}
			if (consumed) {
				sb.append("-");
				sb.append(amountConsumed);
			}

			if (produced && consumed) {
				sb.append(" = ");
				sb.append(amountProduced - amountConsumed);
			}

			if (!consumed && !produced)
				sb.append("-");

			return sb.toString();
		}
	}
}

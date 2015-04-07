package io.ilikeorangutans.ancol.ui.colony;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import io.ilikeorangutans.ancol.game.ware.Stored;
import io.ilikeorangutans.ancol.game.ware.Ware;

/**
 *
 */
public class WarehouseSlot extends VerticalGroup {
	public static final int HEIGHT = 48;
	public static final int WIDTH = 32;
	public static final int MARGIN = 3;
	private final Label amountLabel;
	private final Label simulatedLabel;
	private final Stored stored;
	private final DragAndDrop.Source source;

	public WarehouseSlot(Skin skin, TextureAtlas atlas, Stored stored, int produced, int consumed) {
		this.stored = stored;
		final String regionName = "goods/" + stored.getWare().getName().toLowerCase().replaceAll(" ", "_");
		TextureAtlas.AtlasRegion region = atlas.findRegion(regionName);
		final Image icon = new Image(region);
		addActor(icon);

		source = new WarehouseSlotSource(this, new Image(region));

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
		if (produced > consumed) {
			simulatedLabel.setColor(Color.GREEN);
		} else if (produced < consumed) {
			simulatedLabel.setColor(Color.RED);
		} else {
			simulatedLabel.setColor(Color.WHITE);
		}
	}

	public Ware getWare() {
		return stored.getWare();
	}

	public int getAmount() {
		return stored.getAmount();
	}

	public boolean isEmpty() {
		return getAmount() < 1;
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

	/**
	 * Returns drag and drop source for this.
	 *
	 * @return
	 */
	public DragAndDrop.Source getSource() {
		return source;
	}

}

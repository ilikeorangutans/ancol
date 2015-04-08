package io.ilikeorangutans.ancol.ui.colony;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import io.ilikeorangutans.ancol.game.colony.ColonyProduction;
import io.ilikeorangutans.ancol.game.colony.event.JobAssignedEvent;
import io.ilikeorangutans.ancol.game.colony.population.ColonistJoinedEvent;
import io.ilikeorangutans.ancol.game.colony.population.ColonistLeftEvent;
import io.ilikeorangutans.ancol.game.ware.RecordingWares;
import io.ilikeorangutans.ancol.game.ware.Stored;
import io.ilikeorangutans.ancol.game.ware.Ware;
import io.ilikeorangutans.ancol.game.ware.Wares;
import io.ilikeorangutans.bus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class WarehouseAndProductionUI extends HorizontalGroup {
	private final Wares warehouse;
	private final ColonyProduction production;
	private final List<WarehouseSlot> slots = new ArrayList<WarehouseSlot>();

	public WarehouseAndProductionUI(Skin skin, DragAndDrop dragAndDrop, TextureAtlas atlas, Wares warehouse, ColonyProduction production) {
		this.warehouse = warehouse;
		this.production = production;

		for (Stored stored : warehouse.getWares()) {
			final Ware ware = stored.getWare();
			if (ware.isStorable()) {
				WarehouseSlot slot = new WarehouseSlot(skin, atlas, ware, stored.getAmount());
				addActor(slot);
				slot.setUserObject(ware);
				dragAndDrop.addSource(slot.getSource());
				slots.add(slot);
			}
		}

		refresh();
	}

	public void refresh() {
		RecordingWares simulated = production.simulate(warehouse);
		for (WarehouseSlot slot : slots) {
			Ware ware = slot.getWare();
			int amount = warehouse.getAmount(ware);
			slot.refresh(amount, simulated.getProduced(ware), simulated.getConsumed(ware));
		}
	}

	@Subscribe
	public void onColonistJoined(ColonistJoinedEvent event) {
		refresh();
	}

	@Subscribe
	public void onColonistLeft(ColonistLeftEvent event) {
		refresh();
	}

	@Subscribe
	public void onJobAssigned(JobAssignedEvent event) {
		refresh();
	}
}

package io.ilikeorangutans.ancol.game.ware;

import io.ilikeorangutans.ancol.game.ware.Stored;
import io.ilikeorangutans.ancol.game.ware.WareType;
import io.ilikeorangutans.ancol.game.ware.Wares;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Wares that will record all store and retrieve calls separately.
 */
public class RecordingWares implements Wares {

	private Map<WareType, Line> lines = new LinkedHashMap<WareType, Line>();

	public RecordingWares(Wares start) {
		for (WareType wareType : WareType.values()) {
			lines.put(wareType, new Line(start.getAmount(wareType)));
		}
	}

	@Override
	public void store(WareType ware, int amount) {
		lines.get(ware).effective += amount;
		lines.get(ware).produced += amount;
	}

	@Override
	public Collection<Stored> getWares() {
		throw new RuntimeException("Implement me");
	}

	@Override
	public int retrieve(WareType ware, int amount) {
		int effective = Math.min(amount, lines.get(ware).effective);
		lines.get(ware).effective -= effective;
		lines.get(ware).consumed += effective;
		return effective;
	}

	@Override
	public int getAmount(WareType type) {
		return lines.get(type).effective;
	}

	public int getConsumed(WareType type) {
		return lines.get(type).consumed;
	}

	public int getProduced(WareType type) {
		return lines.get(type).produced;
	}

	private class Line {

		int consumed, produced, effective;

		public Line(int start) {
			this.effective = start;
		}
	}
}

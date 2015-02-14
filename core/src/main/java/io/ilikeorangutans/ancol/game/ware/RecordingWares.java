package io.ilikeorangutans.ancol.game.ware;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Wares that will record all store and retrieve calls separately.
 */
public class RecordingWares implements Wares {

	private Map<Ware, Line> lines = new LinkedHashMap<Ware, Line>();

	public RecordingWares(Wares start) {
		for (Stored stored : start.getWares()) {
			lines.put(stored.getWare(), new Line(stored.getAmount()));
		}
	}

	@Override
	public void store(Ware ware, int amount) {
		lines.get(ware).effective += amount;
		lines.get(ware).produced += amount;
	}

	@Override
	public Collection<Stored> getWares() {
		throw new RuntimeException("Implement me");
	}

	@Override
	public int retrieve(Ware ware, int amount) {
		int effective = Math.min(amount, lines.get(ware).effective);
		lines.get(ware).effective -= effective;
		lines.get(ware).consumed += effective;
		return effective;
	}

	@Override
	public int getAmount(Ware type) {
		return lines.get(type).effective;
	}

	public int getConsumed(Ware type) {
		return lines.get(type).consumed;
	}

	public int getProduced(Ware type) {
		return lines.get(type).produced;
	}

	private class Line {

		int consumed, produced, effective;

		public Line(int start) {
			this.effective = start;
		}
	}
}

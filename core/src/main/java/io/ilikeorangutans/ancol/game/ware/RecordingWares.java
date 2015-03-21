package io.ilikeorangutans.ancol.game.ware;

import java.util.Collection;

/**
 * Wares that will record all store and retrieve calls to its delegate.
 */
public class RecordingWares implements Wares {

	private final Wares delegate;
	private int[] deposits;
	private int[] withdrawals;

	/**
	 * Record all calls for the given delegate.
	 *
	 * @param delegate
	 */
	public RecordingWares(Wares delegate) {
		this.delegate = delegate;
		int numberOfWares = delegate.getWares().size();
		deposits = new int[numberOfWares];
		withdrawals = new int[numberOfWares];
	}

	@Override
	public void store(Ware ware, int amount) {
		deposits[ware.getId()] += amount;
		delegate.store(ware, amount);
	}

	@Override
	public Collection<Stored> getWares() {
		return delegate.getWares();
	}

	@Override
	public int retrieve(Ware ware, int amount) {
		withdrawals[ware.getId()] += amount;
		return delegate.retrieve(ware, amount);
	}

	@Override
	public int getAmount(Ware type) {
		return delegate.getAmount(type);
	}

	/**
	 * @param type
	 * @return how much of the given type was consumed.
	 */
	public int getConsumed(Ware type) {
		return withdrawals[type.getId()];
	}

	/**
	 * @param type
	 * @return how much of the given ware was produced.
	 */
	public int getProduced(Ware type) {
		return deposits[type.getId()];
	}

}

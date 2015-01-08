package io.ilikeorangutans.ancol.game.ware;

import io.ilikeorangutans.ancol.game.cargo.Transportable;

import java.util.Arrays;
import java.util.Comparator;

/**
 *
 */
public enum WareType implements Transportable {

	Food(true, 0),
	Sugar(true, 10),
	Tobacco(true, 20),
	Cotton(true, 30),
	Furs(true, 40),
	Lumber(true, 50),
	Ore(true, 60),
	Silver(true, 70),
	Horses(true, 80),
	Rum(true, 90),
	Cigars(true, 100),
	Cloth(true, 110),
	Coats(true, 120),
	Goods(true, 130),
	Tools(true, 140),
	Muskets(true, 150),
	Hammers(true, 200),
	Crosses(false, 300),
	LibertyBells(false, 400);

	private final boolean canBeStored;
	private final int sort;

	WareType(boolean canBeStored, int sort) {
		this.canBeStored = canBeStored;
		this.sort = sort;
	}

	public static WareType[] getStorableTypes() {
		WareType[] values = values();
		Arrays.sort(values, new Comparator<WareType>() {
			@Override
			public int compare(WareType o1, WareType o2) {
				return o1.sort - o2.sort;
			}
		});

		return Arrays.copyOf(values, values.length - 2);
	}

	public boolean isTransient() {
		return !canBeStored;
	}

	@Override
	public int getRequiredSpace() {
		return 1;
	}

	@Override
	public String getDescription() {
		return this.name();
	}

}

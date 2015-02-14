package io.ilikeorangutans.ancol.game.ware;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class AvailableWares {

	private List<Ware> all;
	private List<Ware> storableWares;

	public List<Ware> getAll() {
		return all;
	}

	public List<Ware> getStorableWares() {
		if (storableWares == null) {
			storableWares = new ArrayList<Ware>();

			for (Ware ware : all) {
				if (ware.isStorable())
					storableWares.add(ware);
			}

		}
		return storableWares;
	}

	public List<Ware> findByType(WareType type) {
		List<Ware> result = Lists.newArrayList();
		for (Ware ware : all) {
			if (ware.getWareType().equals(type))
				result.add(ware);
		}

		return result;
	}


	public Ware findByName(String name) {

		for (Ware ware : all) {
			if (name.equals(ware.getName()))
				return ware;
		}

		throw new IllegalArgumentException("No ware with name " + name + "found");
	}
}

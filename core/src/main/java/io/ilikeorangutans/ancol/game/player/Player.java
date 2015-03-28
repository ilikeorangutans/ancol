package io.ilikeorangutans.ancol.game.player;

import io.ilikeorangutans.ancol.game.nation.Nation;
import io.ilikeorangutans.ancol.map.GameMap;

/**
 *
 */
public class Player {

	private final String name;
	private final int id;
	private Nation nation;
	private GameMap map;

	public Player(int id, String name, Nation nation) {
		this.id = id;
		this.name = name;
		this.nation = nation;
	}

	public Nation getNation() {
		return nation;
	}

	public GameMap getMap() {
		return map;
	}

	public void setMap(GameMap map) {
		this.map = map;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Player player = (Player) o;

		if (id == player.id)
			return true;

		return false;
	}

	@Override
	public int hashCode() {
		return id;
	}

	@Override
	public String toString() {
		return "Player{" +
				"name='" + name + '\'' +
				", id=" + id +
				'}';
	}

}

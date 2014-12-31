package io.ilikeorangutans.ancol.game.player;

/**
 *
 */
public class Player {

	private final String name;

	private final int id;

	public Player(int id, String name) {
		this.id = id;
		this.name = name;
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

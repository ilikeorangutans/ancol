package io.ilikeorangutans.ancol.game.nation;

import com.badlogic.gdx.graphics.Color;

/**
 *
 */
public class Nation {

	private String name;

	public Color getColor() {
		return color;
	}

	public String getName() {
		return name;
	}

	private Color color;

	public Nation(String name, Color color) {
		this.name = name;
		this.color = color;

	}
}

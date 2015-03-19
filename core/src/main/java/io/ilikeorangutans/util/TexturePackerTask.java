package io.ilikeorangutans.util;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

/**
 * Simple wrapper around TexturePacker
 */
public class TexturePackerTask {

	public static void main(String[] args) {
		TexturePacker.Settings settings = new TexturePacker.Settings();
		settings.maxWidth = 2048;
		settings.maxHeight = 2048;

//		TexturePacker.process(settings, "../../assets", "packed", "ancol");
		TexturePacker.process(settings, args[0], args[1], args[2]);
	}

}
